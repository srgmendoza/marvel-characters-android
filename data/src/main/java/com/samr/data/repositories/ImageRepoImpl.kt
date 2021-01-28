package com.samr.data.repositories

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.samr.core.utils.*
import com.samr.data.services.ImageService
import com.samr.domain.repositories.ImageRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ImageRepoImpl: ImageRepo {

    private var service = ImageService()

    override fun fetchImage(path: String,
                            extension: String,
                            size: AspectRatio.ImageSize,
                            origin: AspectRatio.Origin,
                            callback: (LayerResult<Bitmap>?) -> Unit) {


        val url = if(origin == AspectRatio.Origin.LIST) "$path/${StandardAspectRatio.getSize(size)}.$extension" else "$path.$extension"

        GlobalScope.launch(Dispatchers.IO) {

            service.fetchImage(url){result ->

                try{
                    when (result) {
                        is LayerResult.Success -> {
                            val image = mapToBmp(result.value)
                            callback(LayerResult.Success(image))
                        }
                        is LayerResult.Error -> {

                            throw CustomError(originLayer = CustomError.OriginLayer.DATA_LAYER,
                                underLyingError = result.error)
                        }
                    }
                }catch (e: Exception){

                    callback(LayerResult.Error(e))
                }
            }

        }


    }

    private fun mapToBmp(value: ByteArray) = BitmapFactory.decodeByteArray(value,0,value.size)

}