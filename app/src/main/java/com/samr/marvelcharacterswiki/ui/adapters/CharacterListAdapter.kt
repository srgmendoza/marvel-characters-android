package com.samr.marvelcharacterswiki.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.samr.core.utils.AspectRatio
import com.samr.marvelcharacterswiki.R
import com.samr.marvelcharacterswiki.ui.fragments.CharactersListFragmentDirections
import com.samr.marvelcharacterswiki.ui.presenters.ImageFetchPresenter
import com.samr.marvelcharacterswiki.models.CharacterModel
import com.samr.marvelcharacterswiki.ui.views.CharacterImageView
import kotlinx.android.synthetic.main.character_item.view.*



class CharacterListAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var characters: MutableList<CharacterModel> = mutableListOf()
    private var presenter: CharacterImageView = ImageFetchPresenter()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CharactersListsViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.character_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.character_id.text = characters[position].id.toString()
        holder.itemView.character_name.text = characters[position].name

        presenter.fetchImage(characters[position].thumbnail,AspectRatio.Origin.LIST){ result ->

            holder.itemView.character_img.setImageBitmap(result)

        }

        holder.itemView.setOnClickListener {
            val action = CharactersListFragmentDirections.actionInitFragmentToSecondFragment(characters[position].id.toString())
            holder.itemView.findNavController().navigate(action)
        }

    }

    override fun getItemCount(): Int {
        return characters.count()
    }

    internal inner class CharactersListsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}