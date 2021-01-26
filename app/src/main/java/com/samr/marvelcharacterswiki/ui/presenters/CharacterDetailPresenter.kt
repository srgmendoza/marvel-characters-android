package com.samr.marvelcharacterswiki.ui.presenters

import com.samr.core.utils.LayerResult
import com.samr.core.utils.UIError
import com.samr.domain.entities.CharacterEntity
import com.samr.domain.usecases.CharacterDetailUseCase
import com.samr.marvelcharacterswiki.models.CharacterDetailModel
import com.samr.marvelcharacterswiki.models.DetailThumbnail
import com.samr.marvelcharacterswiki.ui.views.CharacterDetailView
import org.koin.java.KoinJavaComponent.inject

class CharacterDetailPresenter(private val view: CharacterDetailView) {

    private val characterDetailUseCase: CharacterDetailUseCase by inject(CharacterDetailUseCase::class.java)

    fun fetchDataForMainScreen(characterId: String) {

        characterDetailUseCase.execute(characterId){uiResult ->

            when (uiResult){
                is LayerResult.Success -> {
                    view.onCharacterDetailReceived(mapDataToUi(uiResult.value))
                }
                is LayerResult.Error -> {
                    val error = uiResult.errorInfo as UIError
                    view.onError(error.userMessage)
                }
            }

        }
    }

    private fun mapDataToUi(characterEntity: CharacterEntity) =
        CharacterDetailModel(
            id = characterEntity.id,
            name = characterEntity.name,
            description = characterEntity.description,
            thumbnail = DetailThumbnail(
                path = characterEntity.thumbnail.path,
                extension = characterEntity.thumbnail.extension),
            storiesCount = characterEntity.stories.available,
            seriesCount = characterEntity.series.available,
            comicsCount = characterEntity.comics.available,
            eventsCount = characterEntity.events.available
        )
}