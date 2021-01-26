package com.samr.data.repositories

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.samr.core.utils.*
import com.samr.data.services.ImageService
import com.samr.domain.repositories.ImagesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DefaultImageRepository: ImagesRepository {

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

                            throw DomainError(result.errorInfo, DomainError.Type.DATA_LAYER_ERROR)
                        }
                    }
                }catch (de: DomainError){

                    callback(LayerResult.Error(de))
                }
            }

        }


    }

    private fun mapToBmp(value: ByteArray) = BitmapFactory.decodeByteArray(value,0,value.size)

}