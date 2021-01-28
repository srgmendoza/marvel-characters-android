package com.samr.marvelcharacterswiki.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.samr.core.utils.AspectRatio
import com.samr.core.utils.LayerResult
import com.samr.marvelcharacterswiki.R
import com.samr.marvelcharacterswiki.models.CharacterDetailModel
import com.samr.marvelcharacterswiki.models.Thumbnail
import com.samr.marvelcharacterswiki.ui.presenters.CharacterPresenter
import com.samr.marvelcharacterswiki.ui.presenters.CharacterPresenterImpl
import com.samr.marvelcharacterswiki.ui.utils.ViewUtils
import kotlinx.android.synthetic.main.fragment_character_detail.*

class CharacterDetailFragment : Fragment() {

    private var presenter: CharacterPresenter = CharacterPresenterImpl()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_character_detail, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val characterId = arguments?.let { CharacterDetailFragmentArgs.fromBundle(it).characterId}

        if (characterId != null) {
            presenter.fetchCharacterDetail(characterId){ result ->

                activity?.runOnUiThread{

                    when(result) {
                        is LayerResult.Success -> {
                            renderView(result.value)
                        }
                        is LayerResult.Error -> {
                            renderError(result.errorInfo)
                        }
                    }
                }
            }
        }
    }


    private fun renderView(character: CharacterDetailModel) {

        character_name.text = character.name
        character_description.text = if(character.description.isNotEmpty()) character.description else "Description not available"


        comics_count.text = "${character.comicsCount} Comics"
        series_count.text = "${character.seriesCount} Series"
        stories_count.text = "${character.storiesCount} Stories"

        more_info_btn.setOnClickListener {
            val action = CharacterDetailFragmentDirections.actionSecondFragmentToWebview(character.detailUrl)
            more_info_btn.findNavController().navigate(action)
        }

        presenter.fetchImage(
            imageInfo = Thumbnail(character.thumbnail.path,character.thumbnail.extension),
            origin = AspectRatio.Origin.DETAIL){

            character_image.setImageBitmap(it)

        }

    }

    private fun renderError(errorInfo: Throwable) {

        activity?.let {

            ViewUtils.onDialog("Error getting Marvel Character",
                it){}
        }
    }

}