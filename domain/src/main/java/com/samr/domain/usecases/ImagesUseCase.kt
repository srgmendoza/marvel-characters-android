package com.samr.domain.usecases

import android.graphics.Bitmap
import com.samr.data.utils.AspectRatio
import com.samr.data.utils.CustomError
import com.samr.data.utils.LayerResult
import com.samr.data.utils.Utils.getImageUrl
import com.samr.domain.models.Thumbnail
import com.samr.domain.repositories.ImageRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ImagesUseCase(private val imageRepo: ImageRepo) {

    fun execute(
        imageInfo: Thumbnail,
        origin: com.samr.data.utils.AspectRatio.Origin,
        callback: (com.samr.data.utils.LayerResult<Bitmap>) -> Unit
    ) {

        val url = getImageUrl(
            path = imageInfo.path,
            extension = imageInfo.extension,
            size = com.samr.data.utils.AspectRatio.ImageSize.MEDIUM,
            origin = origin
        )

        GlobalScope.launch(Dispatchers.Main) {

            imageRepo.fetchImage(
                url = url
            ) { result ->

                try {
                    when (result) {
                        is com.samr.data.utils.LayerResult.Success -> {
                            callback(result)
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
                                originLayer = com.samr.data.utils.CustomError.OriginLayer.DOMAIN_LAYER,
                                underLyingError = e
                            )
                        )
                    )
                } catch (ce: com.samr.data.utils.CustomError) {

                    callback(com.samr.data.utils.LayerResult.Error(ce))
                }
            }
        }
    }
}
