package com.sm.feature_search.presentation.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.core_utils.ListDelegate
import com.sm.base_core.GenericLoader
import com.sm.feature_search.databinding.FragmentSearchBinding
import com.sm.feature_search.presentation.models.SearchCharacter
import com.sm.feature_search.presentation.ui.components.result_list.ResultListView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent

class SearchFragment : Fragment() {

    //override val classToken = SearchViewModel::class.java
    private val viewModel: SearchViewModel by KoinJavaComponent.inject(SearchViewModel::class.java)
    private lateinit var binding: FragmentSearchBinding
    private lateinit var loaderView: GenericLoader

    private var resultListView: ResultListView? = null

    private val listDelegate: ListDelegate = object : ListDelegate {
        override fun onMoreItemsRequest() {
            viewModel.handleEvent(SearchContract.Event.OnLoadRequested)
        }

        override fun getLoadingState() = viewModel.isInLoadingState()

        override fun <T> onItemSelected(item: T) {
            (item as? SearchCharacter)?.let {
                viewModel.handleEvent(SearchContract.Event.OnItemSelected(it.id.toString()))
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater)
        loaderView = binding.loaderView
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        resultListView = context?.let {
            ResultListView(
                context = it,
                listDelegate = listDelegate
            )
        }

        resultListView?.let {
            binding.containerView.addView(it)
        }


        setupViewObservers()
        setupViewModelObservers()
    }

    @OptIn(FlowPreview::class)
    private fun setupViewObservers() {
        lifecycleScope.launch {
            binding.searchView.getTextChangeQueryAsStateFlow()
                .debounce(300)
                .filter { query ->
                    return@filter (query.isNotEmpty() && query.length >= 4)
                }
                .distinctUntilChanged()
                .flowOn(Dispatchers.Default)
                .collect { result ->
                    viewModel.setEvent(SearchContract.Event.OnSearchByName(result))
                }
        }
        lifecycleScope.launch {
            binding.searchView.listenForCloseEvent()
                .collect {
                    if(it)
                        Log.d("SearchFragment", "search closed")
                }
        }
    }

    private fun setupViewModelObservers() {
        lifecycleScope.launch {
            viewModel.uiState.collect {
                when (it.state) {
                    is SearchContract.SearchState.Idle -> {
                        //TODO: Render view
                    }

                    is SearchContract.SearchState.Loading -> {
                        loaderView.show(
                            withBackground = it.state == SearchContract.SearchState.Loading.Opaque
                        )
                    }

                    is SearchContract.SearchState.Empty -> {
                        //TODO: Render view
                    }

                    is SearchContract.SearchState.Success -> {
                        loaderView.hide()
                        when (it.state) {
                            is SearchContract.SearchState.Success.RandomResult -> {
                                //TODO: Render random characters
                            }

                            is SearchContract.SearchState.Success.SearchResult -> {
                                resultListView?.updateListContent(it.state.results)
                            }
                        }
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewModel.effect.collect {
                when (it) {
                    SearchContract.Effect.Error -> {
                        //TODO: Render error
                    }
                }
            }
        }

    }
}