package com.sm.feature_listing.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sm.feature_listing.databinding.CharacterItemBinding
import com.sm.feature_listing.presentation.models.ListedCharacter
import com.sm.feature_listing.presentation.utils.ViewUtils

class CharacterListAdapter(private val onClickListener: (String) -> Unit):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var listedCharacters: MutableList<ListedCharacter> = mutableListOf()

    fun addCharacters(freshData: List<ListedCharacter>) {
        val lastPosition = if (listedCharacters.isEmpty()) {
            0
        } else {
            listedCharacters.size
        }

        if(!listedCharacters.containsAll(freshData)) {
            Log.d("Fragment List", "Characterslist Will update")
            listedCharacters.addAll(freshData.takeLast(40).reversed())
            notifyItemRangeInserted(lastPosition, freshData.size)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = CharacterItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CharactersListsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val character = listedCharacters[position]

        (holder as CharactersListsViewHolder).bind(character)

        holder.itemView.setOnClickListener {
            onClickListener(character.id.toString())
        }
    }

    override fun getItemCount(): Int {
        return listedCharacters.count()
    }

    internal inner class CharactersListsViewHolder(private val binding: CharacterItemBinding):
        RecyclerView.ViewHolder(binding.root) {

        fun bind(listedCharacter: ListedCharacter) {
            binding.characterId.text = listedCharacter.id.toString()
            binding.characterName.text = listedCharacter.name
            ViewUtils.paintImage(listedCharacter.imageUrl, binding.characterImg)
        }
    }

}