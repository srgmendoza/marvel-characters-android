package com.samr.domain.usecases

import android.graphics.Bitmap
import com.samr.core.utils.AspectRatio
import com.samr.core.utils.CustomError
import com.samr.core.utils.LayerResult
import com.samr.domain.entities.Thumbnail
import com.samr.domain.repositories.ImageRepo

class ImagesUseCase(private val imageRepo: ImageRepo) {

    fun execute(imageInfo: Thumbnail,
                origin: AspectRatio.Origin,
                callback: (LayerResult<Bitmap>) -> Unit){



        imageRepo.fetchImage(path = imageInfo.path,
                extension = imageInfo.extension,
                size = AspectRatio.ImageSize.MEDIUM,
                origin = origin){ result ->

            try {
                when (result) {
                    is LayerResult.Success -> {
                        callback(result)
                    }
                    is LayerResult.Error -> {
                        throw CustomError(originLayer = (result.error as CustomError).getErrorOriginLayer(),
                            underLyingError = result.error)
                    }
                }
            }catch (e: Exception){

                callback(LayerResult.Error(
                    CustomError(originLayer = CustomError.OriginLayer.DOMAIN_LAYER,
                    underLyingError = e)
                ))

            }catch (ce: CustomError){

                callback(LayerResult.Error(ce))
            }


        }
    }
}