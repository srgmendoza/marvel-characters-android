package com.sm.listing.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.sm.listing.databinding.FragmentCharactersListBinding
import com.sm.listing.ui.utils.CharactersListScrollListener
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
            //renderError(it)
        }
/*        viewmodel.onCharactersListReady().observe(viewLifecycleOwner) {
            renderView(it)
        }*/
    }

/*    private fun renderError(error: CustomError) {

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
    }*/

    private fun renderView(characters: List<Character>) {
        binding.progressBar.visibility = View.GONE
        //adapter?.addCharacters(characters)
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

                override fun isLoading() = binding.progressBar.visibility == View.VISIBLE
            })
        }

        binding.charactersRecyclerview.adapter = adapter
    }

    private fun askForData() {
        binding.progressBar.visibility = View.VISIBLE
        viewmodel.getCharacters()
    }

    private fun navigateToDetails(detailId: String) {
/*        val action = CharactersListFragmentDirections.actionInitFragmentToSecondFragment(detailId)
        findNavController().navigate(action)*/
    }

}