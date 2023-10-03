package com.sm.feature_search.presentation.ui.components.result_list

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core_utils.ListDelegate
import com.example.core_utils.ListScrollListener
import com.sm.feature_search.databinding.ViewResultListBinding
import com.sm.feature_search.presentation.models.SearchCharacter

internal class ResultListView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    listDelegate: ListDelegate
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding = ViewResultListBinding.inflate(
        LayoutInflater.from(context), this, false)

    private val adapter: ResultListAdapter = ResultListAdapter {
        listDelegate.onItemSelected(it)
    }

    init {
        with(binding.rvList) {
            layoutManager = LinearLayoutManager(context)
            addOnScrollListener(object :
                ListScrollListener(this.layoutManager as LinearLayoutManager) {
                override fun loadMoreItems() {
                    listDelegate.onMoreItemsRequest()
                }

                override fun isLastPage() = false

                override fun isLoading() = listDelegate.getLoadingState()
            })

            adapter = this@ResultListView.adapter
        }
    }

    fun updateListContent(resultList: List<SearchCharacter>) {
        adapter.addCharacters(resultList)
    }
}