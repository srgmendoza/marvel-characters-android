package com.samr.marvelcharacterswiki.ui.presenters

import com.samr.core.utils.LayerResult
import com.samr.core.utils.UIError
import com.samr.domain.entities.CharacterEntity
import com.samr.domain.usecases.CharactersUseCase
import com.samr.marvelcharacterswiki.ui.views.CharactersListView
import com.samr.marvelcharacterswiki.models.CharacterModel
import org.koin.java.KoinJavaComponent.inject


class CharactersListPresenter(private val view: CharactersListView) {

    private val characterUseCase: CharactersUseCase by inject(CharactersUseCase::class.java)

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
                    thumbnail = com.samr.marvelcharacterswiki.models.Thumbnail(it.thumbnail.path,
                    it.thumbnail.extension)
            )
        }


}