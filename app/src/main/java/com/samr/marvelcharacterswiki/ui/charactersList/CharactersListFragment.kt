
package com.samr.marvelcharacterswiki.ui.characterWebview

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.samr.domain.models.Character
import com.samr.domain.models.CustomError
import com.samr.marvelcharacterswiki.databinding.FragmentCharactersListBinding
import com.samr.marvelcharacterswiki.ui.charactersList.CharacterListAdapter
import com.samr.marvelcharacterswiki.ui.charactersList.CharacterListViewModel
import com.samr.marvelcharacterswiki.ui.utils.ViewUtils
import kotlinx.android.synthetic.main.fragment_characters_list.*
import org.koin.java.KoinJavaComponent.inject

class CharactersListFragment : Fragment() {

    private val viewmodel: CharacterListViewModel by inject(CharacterListViewModel::class.java)
    private var adapter: CharacterListAdapter? = null

    private lateinit var binding: FragmentCharactersListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharactersListBinding.inflate(inflater)
        binding.lifecycleOwner = this

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
        viewmodel.onError.observe(viewLifecycleOwner) {
            renderError(it)
        }
        viewmodel.onCharactersListReady().observe(viewLifecycleOwner) {
            renderView(it)
        }
    }

    private fun renderError(error: CustomError) {

        val errorOriginLayer = error.getErrorOriginLayerMsg()
        val errorDescription = error.getErrorDetailedMsg()
        activity?.let {
            ViewUtils.onDialog(
                "Error: <$errorDescription> \nThrown in $errorOriginLayer \nShould retry?",
                it
            ) {
                askForData()
            }
        }
        Log.d("Fragment List", "Error: ${error.localizedMessage}")
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

            this.addOnScrollListener(object : CharactersListScrollListener(this.layoutManager as LinearLayoutManager) {
                override fun loadMoreItems() {
                    askForData()
                }

                override fun isLastPage() = false

                override fun isLoading() = progressBar.visibility == View.VISIBLE
            })
        }

        characters_recyclerview.adapter = adapter
    }

    private fun askForData() {
        binding.progressBar.visibility = View.VISIBLE
        viewmodel.getCharacters()
    }

    private fun navigateToDetails(detailId: String) {
        val action = CharactersListFragmentDirections.actionInitFragmentToSecondFragment(detailId)
        findNavController().navigate(action)
    }

}

private abstract class CharactersListScrollListener(val layoutManager: LinearLayoutManager) : RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val PAGE_SIZE = 4

        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

        if (!isLoading() && !isLastPage()) {
            if (visibleItemCount + firstVisibleItemPosition >= totalItemCount &&
                firstVisibleItemPosition >= 0 &&
                totalItemCount >= PAGE_SIZE
            ) {
                loadMoreItems()
            }
        }
    }

    abstract fun loadMoreItems()

    abstract fun isLastPage(): Boolean

    abstract fun isLoading(): Boolean
}
