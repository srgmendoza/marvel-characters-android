package com.samr.marvelcharacterswiki.ui.presenters

import com.samr.core.utils.AspectRatio
import com.samr.core.utils.LayerResult
import com.samr.core.utils.UIError
import com.samr.domain.entities.CharacterEntity
import com.samr.domain.entities.Thumbnail
import com.samr.domain.usecases.CharactersUseCase
import com.samr.domain.usecases.ImagesUseCase
import com.samr.marvelcharacterswiki.ui.fragments.CharactersListFragment
import com.samr.marvelcharacterswiki.ui.views.CharactersListView
import com.samr.marvelcharacterswiki.ui.models.CharacterModel


class CharactersListPresenter(private val view: CharactersListView) {

    private var characterUseCase: CharactersUseCase = CharactersUseCase()

    fun fetchDataForMainScreen() {

        characterUseCase.execute{uiResult ->

            when (uiResult){
                is LayerResult.Success -> {
                    view.onCharacterslistReceived(mapDataToUi(uiResult.value))
                }
                is LayerResult.Error -> {
                    val error = uiResult.errorInfo as UIError
                    view.onError(error.userMessage)
                }
            }

        }
    }

    private fun mapDataToUi(value: List<CharacterEntity>) =
        value.map{
            CharacterModel(
                id = it.id,
                name = it.name,
                    thumbnail = com.samr.marvelcharacterswiki.ui.models.Thumbnail(it.thumbnail.path,
                    it.thumbnail.extension)
            )
        }


}