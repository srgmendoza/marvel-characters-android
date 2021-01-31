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


class CharacterPresenterImpl(private val characterUseCase: CharactersUseCase,
                             private val characterDetailUseCase: CharacterDetailUseCase,
                             private val imagesUseCase: ImagesUseCase): CharacterPresenter {

//    private val characterUseCase: CharactersUseCase by inject(CharactersUseCase::class.java)
//    private val characterDetailUseCase: CharacterDetailUseCase by inject(CharacterDetailUseCase::class.java)
//    private val imagesUseCase: ImagesUseCase by inject(ImagesUseCase::class.java)


    override fun fetchCharacterList(callback: (LayerResult<List<CharacterModel>>) -> Unit) {

        characterUseCase.execute{uiResult: LayerResult<List<CharacterEntity>>? ->

            try{

                when (uiResult){

                    is LayerResult.Success -> {

                        callback(LayerResult.Success(uiResult.value?.map { mapDataListToUi(it) }))
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

    private fun mapDataListToUi(value: CharacterEntity?) =

        CharacterModel(
                id = value?.id ?: 0,
                name = value?.name ?: "",
                thumbnail = Thumbnail(value?.thumbnail?.path ?: "",
                        value?.thumbnail?.extension ?: "")
        )

    private fun mapDataToUi(characterEntity: CharacterEntity?) =

        CharacterDetailModel(
            id = characterEntity?.id ?: 0,
            name = characterEntity?.name ?: "",
            description = characterEntity?.description ?: "",
            thumbnail = DetailThumbnail(
                path = characterEntity?.thumbnail?.path ?: "",
                extension = characterEntity?.thumbnail?.extension ?: ""),
            storiesCount = characterEntity?.stories?.available ?: 0,
            seriesCount = characterEntity?.series?.available ?: 0,
            comicsCount = characterEntity?.comics?.available ?: 0,
            eventsCount = characterEntity?.events?.available ?: 0,
            detailUrl = characterEntity?.urls?.find { it.type == "detail" }?.url ?: ""
        )




}