package com.samr.marvelcharacterswiki.ui.presenters

import android.graphics.Bitmap
import com.samr.data.utils.AspectRatio
import com.samr.data.utils.CustomError
import com.samr.data.utils.LayerResult
import com.samr.domain.models.Character
import com.samr.domain.usecases.CharacterDetailUseCase
import com.samr.domain.usecases.CharactersUseCase
import com.samr.domain.usecases.ImagesUseCase
import com.samr.marvelcharacterswiki.models.CharacterDetailModel
import com.samr.marvelcharacterswiki.models.CharacterModel
import com.samr.marvelcharacterswiki.models.DetailThumbnail
import com.samr.marvelcharacterswiki.models.Thumbnail

class CharacterPresenterImpl(
    private val characterUseCase: CharactersUseCase,
    private val characterDetailUseCase: CharacterDetailUseCase,
    private val imagesUseCase: ImagesUseCase
) : CharacterPresenter {

    override fun fetchCharacterList(callback: (com.samr.data.utils.LayerResult<List<CharacterModel>>) -> Unit) {

        characterUseCase.execute { uiResult: com.samr.data.utils.LayerResult<List<Character>>? ->

            try {

                when (uiResult) {

                    is com.samr.data.utils.LayerResult.Success -> {

                        callback(com.samr.data.utils.LayerResult.Success(uiResult.value?.map { mapDataListToUi(it) }))
                    }
                    is com.samr.data.utils.LayerResult.Error -> {

                        throw com.samr.data.utils.CustomError(
                            originLayer = (uiResult.error as com.samr.data.utils.CustomError).getErrorOriginLayer(),
                            underLyingError = (uiResult.error as com.samr.data.utils.CustomError).getUnderlyingError()
                        )
                    }
                }
            } catch (ce: com.samr.data.utils.CustomError) {

                callback(com.samr.data.utils.LayerResult.Error(ce))
            } catch (e: Throwable) {

                callback(
                    com.samr.data.utils.LayerResult.Error(
                        com.samr.data.utils.CustomError(
                            originLayer = com.samr.data.utils.CustomError.OriginLayer.PRESENTATION_LAYER,
                            underLyingError = e
                        )
                    )
                )
            }
        }
    }

    override fun fetchCharacterDetail(
        characterId: String,
        callback: (com.samr.data.utils.LayerResult<CharacterDetailModel>) -> Unit
    ) {

        characterDetailUseCase.execute(characterId) { uiResult ->

            try {

                when (uiResult) {
                    is com.samr.data.utils.LayerResult.Success -> {

                        callback(com.samr.data.utils.LayerResult.Success(uiResult.value?.let { mapDataToUi(it) }))
                    }
                    is com.samr.data.utils.LayerResult.Error -> {

                        throw com.samr.data.utils.CustomError(
                            originLayer = (uiResult.error as com.samr.data.utils.CustomError).getErrorOriginLayer(),
                            underLyingError = (uiResult.error as com.samr.data.utils.CustomError).getUnderlyingError()
                        )
                    }
                }
            } catch (e: Throwable) {

                callback(
                    com.samr.data.utils.LayerResult.Error(
                        com.samr.data.utils.CustomError(
                            originLayer = com.samr.data.utils.CustomError.OriginLayer.PRESENTATION_LAYER,
                            underLyingError = e
                        )
                    )
                )
            } catch (ce: com.samr.data.utils.CustomError) {

                callback(com.samr.data.utils.LayerResult.Error(ce))
            }
        }
    }

    override fun fetchImage(imageInfo: Thumbnail, origin: com.samr.data.utils.AspectRatio.Origin, callback: (com.samr.data.utils.LayerResult<Bitmap>) -> Unit) {

        imagesUseCase.execute(com.samr.domain.models.Thumbnail(imageInfo.path, imageInfo.extension), origin) { result ->

            try {

                when (result) {
                    is com.samr.data.utils.LayerResult.Success -> {

                        callback(com.samr.data.utils.LayerResult.Success(result.value))
                    }
                    is com.samr.data.utils.LayerResult.Error -> {
                        throw com.samr.data.utils.CustomError(
                            originLayer = (result.error as com.samr.data.utils.CustomError).getErrorOriginLayer(),
                            underLyingError = (result.error as com.samr.data.utils.CustomError).getUnderlyingError()
                        )
                    }
                }
            } catch (e: Throwable) {

                callback(
                    com.samr.data.utils.LayerResult.Error(
                        com.samr.data.utils.CustomError(
                            originLayer = com.samr.data.utils.CustomError.OriginLayer.PRESENTATION_LAYER,
                            underLyingError = e
                        )
                    )
                )
            } catch (ce: com.samr.data.utils.CustomError) {

                callback(com.samr.data.utils.LayerResult.Error(ce))
            }
        }
    }

    // Private Methods

    private fun mapDataListToUi(value: Character?) =

        CharacterModel(
            id = value?.id ?: 0,
            name = value?.name ?: "",
            thumbnail = Thumbnail(
                value?.thumbnail?.path ?: "",
                value?.thumbnail?.extension ?: ""
            )
        )

    private fun mapDataToUi(characterEntity: Character?) =

        CharacterDetailModel(
            id = characterEntity?.id ?: 0,
            name = characterEntity?.name ?: "",
            description = characterEntity?.description ?: "",
            thumbnail = DetailThumbnail(
                path = characterEntity?.thumbnail?.path ?: "",
                extension = characterEntity?.thumbnail?.extension ?: ""
            ),
            storiesCount = characterEntity?.stories?.available ?: 0,
            seriesCount = characterEntity?.series?.available ?: 0,
            comicsCount = characterEntity?.comics?.available ?: 0,
            eventsCount = characterEntity?.events?.available ?: 0,
            detailUrl = characterEntity?.urls?.find { it.type == "detail" }?.url ?: ""
        )
}
