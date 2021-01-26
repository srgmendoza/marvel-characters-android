package com.samr.marvelcharacterswiki.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.samr.marvelcharacterswiki.R
import com.samr.marvelcharacterswiki.ui.adapters.CharacterListAdapter
import com.samr.marvelcharacterswiki.models.CharacterModel
import com.samr.marvelcharacterswiki.ui.presenters.CharactersListPresenter
import com.samr.marvelcharacterswiki.ui.views.CharactersListView
import kotlinx.android.synthetic.main.fragment_characters_list.*

class CharactersListFragment : Fragment(), CharactersListView {


    private lateinit var presenter: CharactersListPresenter
    private val adapter = CharacterListAdapter()


        override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_characters_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = CharactersListPresenter(this)


        setupRecyclerView()
        askForData()
    }



    override fun onCharacterslistReceived(characters: List<CharacterModel>) {

        adapter.characters.addAll(characters)


        Log.d("Fragment List", "CharacterslistReceived")
        progressBar.visibility = View.GONE

        characters_recyclerview.post{
            adapter.notifyDataSetChanged()
        }

    }

    override fun onError(errorMessage: String) {
        progressBar.visibility = View.GONE
        Log.d("Fragment List", "Error: $errorMessage")
    }

    private fun setupRecyclerView() {
        characters_recyclerview.apply {

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


    private fun askForData(){
        progressBar.visibility = View.VISIBLE
        presenter.fetchDataForMainScreen()
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
            if (visibleItemCount + firstVisibleItemPosition >= totalItemCount
                && firstVisibleItemPosition >= 0
                && totalItemCount >= PAGE_SIZE) {
                loadMoreItems()
            }
        }
    }

    abstract fun loadMoreItems()

    abstract fun isLastPage(): Boolean

    abstract fun isLoading(): Boolean

}