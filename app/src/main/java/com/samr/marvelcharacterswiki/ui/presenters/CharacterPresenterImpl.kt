package com.samr.marvelcharacterswiki.ui.presenters

import android.graphics.Bitmap
import com.samr.core.utils.AspectRatio
import com.samr.core.utils.CustomError
import com.samr.core.utils.LayerResult
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

            try{
                when (uiResult){
                    is LayerResult.Success -> {
                        callback(LayerResult.Success(uiResult.value?.let { mapDataToUi(it) }))
                    }
                    is LayerResult.Error -> {

                        throw CustomError(originLayer = (uiResult.error as CustomError).getErrorOriginLayer(),
                            underLyingError = (uiResult.error as CustomError).getUnderlyingError())
                    }
                }
            }catch (ce: CustomError){

                callback(LayerResult.Error(ce))
            }
            catch (e: Throwable){

                callback(LayerResult.Error(
                    CustomError(originLayer = CustomError.OriginLayer.PRESENTATION_LAYER,
                        underLyingError = e)
                ))

            }

        }
    }

    override fun fetchCharacterDetail(
        characterId: String,
        callback: (LayerResult<CharacterDetailModel>) -> Unit
    ) {

        characterDetailUseCase.execute(characterId){uiResult ->

            try{

                when (uiResult){
                    is LayerResult.Success -> {

                        callback(LayerResult.Success(uiResult.value?.let { mapDataToUi(it) }))
                    }
                    is LayerResult.Error -> {

                        throw CustomError(originLayer = (uiResult.error as CustomError).getErrorOriginLayer(),
                            underLyingError = (uiResult.error as CustomError).getUnderlyingError())
                    }
                }
            }catch (e: Throwable){

                callback(LayerResult.Error(
                    CustomError(originLayer = CustomError.OriginLayer.PRESENTATION_LAYER,
                        underLyingError = e)
                ))

            }catch (ce: CustomError){

                callback(LayerResult.Error(ce))
            }


        }

    }


    override fun fetchImage(imageInfo: Thumbnail, origin: AspectRatio.Origin,callback: (LayerResult<Bitmap>) -> Unit) {

        imagesUseCase.execute(com.samr.domain.entities.Thumbnail(imageInfo.path,imageInfo.extension),origin){ result ->

            try {

                when (result) {
                    is LayerResult.Success -> {

                        callback(LayerResult.Success(result.value))
                    }
                    is LayerResult.Error -> {
                        throw CustomError(originLayer = (result.error as CustomError).getErrorOriginLayer(),
                            underLyingError = (result.error as CustomError).getUnderlyingError())
                    }
                }
            }catch (e: Throwable){

                callback(LayerResult.Error(
                    CustomError(originLayer = CustomError.OriginLayer.PRESENTATION_LAYER,
                        underLyingError = e)
                ))

            }catch (ce: CustomError){

                callback(LayerResult.Error(ce))
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