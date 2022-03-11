package com.samr.marvelcharacterswiki.ui.charactersList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.samr.domain.models.Character
import com.samr.marvelcharacterswiki.databinding.CharacterItemBinding
import com.samr.marvelcharacterswiki.ui.utils.ViewUtils

class CharacterListAdapter(private val onClickListener: (String) -> Unit):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var characters: MutableList<Character> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = CharacterItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CharactersListsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val character = characters[position]

        (holder as CharactersListsViewHolder).bind(character)

        holder.itemView.setOnClickListener {
            onClickListener(character.id.toString())
        }
    }

    override fun getItemCount(): Int {
        return characters.count()
    }

    internal inner class CharactersListsViewHolder(private val binding: CharacterItemBinding):
        RecyclerView.ViewHolder(binding.root) {

        fun bind(character: Character) {
            binding.characterId.text = character.id.toString()
            binding.characterName.text = character.name
            ViewUtils.paintImage(character.thumbnail.thumbnail, binding.characterImg)
        }
    }

}
