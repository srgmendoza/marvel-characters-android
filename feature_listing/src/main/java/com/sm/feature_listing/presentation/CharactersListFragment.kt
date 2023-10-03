package com.sm.feature_listing.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.sm.feature_listing.databinding.FragmentCharactersListBinding
import com.sm.feature_listing.presentation.models.Character
import com.example.core_utils.ListScrollListener
import com.example.core_utils.ViewUtils
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent

class CharactersListFragment : Fragment() {

    private val viewmodel: CharacterListViewModel by KoinJavaComponent.inject(CharacterListViewModel::class.java)
    private var adapter: CharacterListAdapter? = null

    private lateinit var binding: FragmentCharactersListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharactersListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = CharacterListAdapter {
            navigateToDetails(it)
        }
        setupObservers()
        setupRecyclerView()
        askForData()
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            viewmodel.uiState.collect {
                when (it.state) {
                    is CharacterListContract.CharacterListState.Idle -> {
                        binding.progressBar.visibility = View.GONE
                    }

                    is CharacterListContract.CharacterListState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    is CharacterListContract.CharacterListState.Success -> {
                        renderView(it.state.characters)
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewmodel.effect.collect {
                when (it) {
                    CharacterListContract.Effect.Error -> {
                        renderError()
                    }
                }
            }
        }
    }

    private fun renderError() {
        activity?.let {
            ViewUtils.onDialog(
                "Oops, an error happened!! \nShould retry?",
                it
            ) {
                askForData()
            }
        }
    }

    private fun renderView(characters: List<Character>) {
        binding.progressBar.visibility = View.GONE
        adapter?.addCharacters(characters)
    }

    private fun setupRecyclerView() {

        binding.charactersRecyclerview.apply {

            this.layoutManager = activity?.let { fa ->
                LinearLayoutManager(
                    fa
                )
            }

            this.addOnScrollListener(object :
                ListScrollListener(this.layoutManager as LinearLayoutManager) {
                override fun loadMoreItems() {
                    askForData()
                }

                override fun isLastPage() = false

                override fun isLoading() = binding.progressBar.visibility == View.VISIBLE
            })
        }

        binding.charactersRecyclerview.adapter = adapter
    }

    private fun askForData() {
        viewmodel.setEvent(CharacterListContract.Event.OnLoadRequested)
    }

    private fun navigateToDetails(detailId: String) {
        /*        val action = CharactersListFragmentDirections.actionInitFragmentToSecondFragment(detailId)
                findNavController().navigate(action)*/
    }

}