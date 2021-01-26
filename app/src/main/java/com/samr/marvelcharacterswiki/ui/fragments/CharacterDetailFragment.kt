package com.samr.marvelcharacterswiki.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.samr.core.utils.AspectRatio
import com.samr.marvelcharacterswiki.R
import com.samr.marvelcharacterswiki.models.CharacterDetailModel
import com.samr.marvelcharacterswiki.models.Thumbnail
import com.samr.marvelcharacterswiki.ui.presenters.CharacterDetailPresenter
import com.samr.marvelcharacterswiki.ui.presenters.ImageFetchPresenter
import com.samr.marvelcharacterswiki.ui.views.CharacterDetailView
import com.samr.marvelcharacterswiki.ui.views.CharacterImageView
import kotlinx.android.synthetic.main.fragment_character_detail.*

class CharacterDetailFragment : Fragment(), CharacterDetailView {

    private lateinit var presenter: CharacterDetailPresenter
    private var imagePresenter: CharacterImageView = ImageFetchPresenter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_character_detail, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = CharacterDetailPresenter(this)

        val characterId = arguments?.let { CharacterDetailFragmentArgs.fromBundle(it).characterId}

        if (characterId != null) {
            presenter.fetchDataForMainScreen(characterId)
        }

    }

    override fun onCharacterDetailReceived(character: CharacterDetailModel) {

        character_description.post{
            character_description.text = character.description
        }


        imagePresenter.fetchImage(
            imageInfo = Thumbnail(character.thumbnail.path,character.thumbnail.extension),
            origin = AspectRatio.Origin.DETAIL){

            character_image.post{
                character_image.setImageBitmap(it)
            }

        }
    }

    override fun onError(errorMessage: String) {
        Log.e("DetailFragment",errorMessage)
    }
}