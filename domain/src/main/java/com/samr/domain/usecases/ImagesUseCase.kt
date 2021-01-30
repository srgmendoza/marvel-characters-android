package com.samr.domain.usecases

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.samr.core.utils.AspectRatio
import com.samr.core.utils.CustomError
import com.samr.core.utils.LayerResult
import com.samr.core.utils.Utils.getImageUrl
import com.samr.domain.R
import com.samr.domain.entities.Thumbnail
import com.samr.domain.repositories.ImageRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ImagesUseCase(private val imageRepo: ImageRepo) {

    fun execute(imageInfo: Thumbnail,
                origin: AspectRatio.Origin,
                callback: (LayerResult<Bitmap>) -> Unit){


        val url = getImageUrl(path = imageInfo.path,
                extension = imageInfo.extension,
                size = AspectRatio.ImageSize.MEDIUM,
                origin = origin)

        GlobalScope.launch(Dispatchers.Main) {

            imageRepo.fetchImage(
                url = url
            ) { result ->

                try {
                    when (result) {
                        is LayerResult.Success -> {
                            callback(result)
                        }
                        is LayerResult.Error -> {
                            throw CustomError(
                                originLayer = (result.error as CustomError).getErrorOriginLayer(),
                                underLyingError = (result.error as CustomError).getUnderlyingError()
                            )
                        }
                    }
                } catch (e: Throwable) {

                    callback(
                        LayerResult.Error(
                            CustomError(
                                originLayer = CustomError.OriginLayer.DOMAIN_LAYER,
                                underLyingError = e
                            )
                        )
                    )

                } catch (ce: CustomError) {

                    callback(LayerResult.Error(ce))
                }

            }
        }
    }
}