package com.sm.feature_search.presentation.ui.components.result_list

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.core_utils.ViewUtils
import com.sm.feature_search.databinding.ItemSearchBinding
import com.sm.feature_search.presentation.models.SearchCharacter

class ResultListAdapter (private val onClickListener: (SearchCharacter) -> Unit):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var characters: MutableList<SearchCharacter> = mutableListOf()

    fun addCharacters(freshData: List<SearchCharacter>) {
        val lastPosition = if (characters.isEmpty()) {
            0
        } else {
            characters.size
        }

        if(!characters.containsAll(freshData)) {
            Log.d("Fragment List", "Characterslist Will update")
            characters.addAll(freshData.takeLast(40).reversed())
            notifyItemRangeInserted(lastPosition, freshData.size)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemSearchBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SearchResultListsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val character = characters[position]

        (holder as SearchResultListsViewHolder).bind(character)

        holder.itemView.setOnClickListener {
            onClickListener(character)
        }
    }

    override fun getItemCount(): Int {
        return characters.count()
    }

    internal inner class SearchResultListsViewHolder(private val binding: ItemSearchBinding):
        RecyclerView.ViewHolder(binding.root) {

        fun bind(character: SearchCharacter) {
            binding.characterId.text = character.id.toString()
            binding.characterName.text = character.name
            ViewUtils.paintImage(character.thumbnail, binding.characterImg)
        }
    }

}