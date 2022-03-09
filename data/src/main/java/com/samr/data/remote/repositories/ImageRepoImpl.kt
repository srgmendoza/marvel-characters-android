package com.samr.data.remote.repositories

import android.graphics.Bitmap
import com.samr.data.entities.CustomError
import com.samr.data.entities.LayerResult
import com.samr.domain.repositories.ImageRepo

class ImageRepoImpl(private val service: ImageService) : ImageRepo {

    override suspend fun fetchImage(
        url: String,
        callback: (LayerResult<Bitmap>?) -> Unit
    ) {

        service.fetchImage(url) { result ->

            try {
                when (result) {
                    is LayerResult.Success -> {
                        callback(LayerResult.Success(result.value))
                    }
                    is LayerResult.Error -> {

                        throw CustomError(
                            originLayer = CustomError.OriginLayer.DATA_LAYER,
                            underLyingError = (result.error as CustomError).getUnderlyingError()
                        )
                    }
                }
            } catch (e: Throwable) {

                callback(LayerResult.Error(e))
            }
        }
    }
}
