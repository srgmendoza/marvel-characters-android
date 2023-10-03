package com.sm.feature_search.presentation.ui.components.searchbox

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.SearchView.OnCloseListener
import androidx.appcompat.widget.SearchView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.sm.feature_search.databinding.ViewSearchBoxBinding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

internal class SearchBoxView @JvmOverloads constructor(context: Context,
                     attrs: AttributeSet? = null,
                     defStyleAttr: Int = 0):
    ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding = ViewSearchBoxBinding.inflate(LayoutInflater.from(context), this, true)

    private val textToSearch = MutableStateFlow("")
    private val closed = MutableStateFlow(false)

    init {
        binding.svBox.setIconifiedByDefault(false)
        binding.svBox.setOnClickListener {
            //binding.svBox.isIconified = !binding.svBox.isIconified
            //binding.svBox.onActionViewExpanded()
        }
        binding.svBox.setOnQueryTextFocusChangeListener { _, gainFocus ->
            if(!binding.svBox.isIconified) {
                binding.cancelButton.isVisible = gainFocus
                closed.value = !gainFocus
            }
        }
        binding.cancelButton.setOnClickListener {
            binding.svBox.onActionViewCollapsed()
            binding.svBox.isIconified = true
            closed.value = true
        }
    }

    fun getTextChangeQueryAsStateFlow(): StateFlow<String> {

        binding.svBox.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d("SearchBoxView", "Text submitted $query")
                textToSearch.value = query ?: ""
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                Log.d("SearchBoxView", "Text input $newText")
                return false
            }

        })
        return textToSearch
    }

    fun listenForCloseEvent(): StateFlow<Boolean> {
        binding.svBox.setOnCloseListener {
            closed.value = true
            false
        }

        return closed
    }
}
