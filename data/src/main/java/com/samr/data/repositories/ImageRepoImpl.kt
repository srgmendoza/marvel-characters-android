package com.samr.data.repositories

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.samr.core.utils.*
import com.samr.data.services.ImageService
import com.samr.domain.repositories.ImageRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ImageRepoImpl(private val service: ImageService): ImageRepo {

    override suspend fun fetchImage(url: String,
                            callback: (LayerResult<Bitmap>?) -> Unit) {

        service.fetchImage(url){result ->

            try{
                when (result) {
                    is LayerResult.Success -> {
                        callback(LayerResult.Success(result.value))
                    }
                    is LayerResult.Error -> {

                        throw CustomError(originLayer = CustomError.OriginLayer.DATA_LAYER,
                            underLyingError = (result.error as CustomError).getUnderlyingError())
                    }
                }
            }catch (e: Throwable){

                callback(LayerResult.Error(e))
            }
        }

    }
}