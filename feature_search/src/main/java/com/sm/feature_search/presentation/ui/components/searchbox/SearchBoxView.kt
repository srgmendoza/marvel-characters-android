package com.sm.feature_search.presentation.ui.components.searchbox

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.SearchView.OnQueryTextListener
import androidx.appcompat.widget.SearchView
import androidx.constraintlayout.widget.ConstraintLayout
import com.sm.feature_search.databinding.ViewSearchBoxBinding

class SearchBoxView @JvmOverloads constructor(context: Context,
                     attrs: AttributeSet? = null,
                     defStyleAttr: Int = 0):
    ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding = ViewSearchBoxBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        binding.svBox.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d("SearchBoxView", "Text submitted $query")
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d("SearchBoxView", "Text input $newText")
                return false
            }

        })
    }
}