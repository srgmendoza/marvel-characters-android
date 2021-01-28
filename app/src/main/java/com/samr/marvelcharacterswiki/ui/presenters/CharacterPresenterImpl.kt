package com.samr.marvelcharacterswiki.ui.presenters

import android.graphics.Bitmap
import android.util.Log
import com.samr.core.utils.AspectRatio
import com.samr.core.utils.LayerResult
import com.samr.core.utils.UIError
import com.samr.domain.entities.CharacterEntity
import com.samr.domain.usecases.CharacterDetailUseCase
import com.samr.domain.usecases.CharactersUseCase
import com.samr.domain.usecases.ImagesUseCase
import com.samr.marvelcharacterswiki.models.CharacterDetailModel
import com.samr.marvelcharacterswiki.models.CharacterModel
import com.samr.marvelcharacterswiki.models.DetailThumbnail
import com.samr.marvelcharacterswiki.models.Thumbnail
import org.koin.java.KoinJavaComponent.inject


class CharacterPresenterImpl: CharacterPresenter {

    private val characterUseCase: CharactersUseCase by inject(CharactersUseCase::class.java)
    private val characterDetailUseCase: CharacterDetailUseCase by inject(CharacterDetailUseCase::class.java)
    private val imagesUseCase: ImagesUseCase by inject(ImagesUseCase::class.java)

    override fun fetchCharacterList(callback: (LayerResult<List<CharacterModel>>) -> Unit) {

        characterUseCase.execute{uiResult ->

            when (uiResult){
                is LayerResult.Success -> {
                    callback(LayerResult.Success(mapDataToUi(uiResult.value)))
                }
                is LayerResult.Error -> {
                    val error = uiResult.errorInfo as UIError
                    callback(LayerResult.Error(error))
                }
            }
        }
    }

    override fun fetchCharacterDetail(
        characterId: String,
        callback: (LayerResult<CharacterDetailModel>?) -> Unit
    ) {

        characterDetailUseCase.execute(characterId){uiResult ->

            when (uiResult){
                is LayerResult.Success -> {
                    callback(LayerResult.Success(mapDataToUi(uiResult.value)))
                }
                is LayerResult.Error -> {
                    val error = uiResult.errorInfo as UIError
                    callback(LayerResult.Error(error))
                }
            }

        }

    }


    override fun fetchImage(imageInfo: Thumbnail, origin: AspectRatio.Origin,callback: (Bitmap) -> Unit) {

        imagesUseCase.execute(com.samr.domain.entities.Thumbnail(imageInfo.path,imageInfo.extension),origin){ result ->

            when(result){
                is LayerResult.Success -> {
                    callback(result.value)
                }
                is LayerResult.Error -> {
                    Log.e("Error", result.errorInfo.message.toString())
                }
            }
        }
    }


    //Private Methods

    private fun mapDataToUi(value: List<CharacterEntity>) =
        value.map{
            CharacterModel(
                id = it.id,
                name = it.name,
                    thumbnail = com.samr.marvelcharacterswiki.models.Thumbnail(it.thumbnail.path,
                    it.thumbnail.extension)
            )
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
            eventsCount = characterEntity.events.available,
            detailUrl = characterEntity.urls.find { it.type == "detail" }?.url ?: ""
        )




}