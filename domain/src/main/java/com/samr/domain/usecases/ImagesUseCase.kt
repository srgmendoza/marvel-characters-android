package com.samr.domain.usecases

import android.graphics.Bitmap
import com.samr.core.utils.AspectRatio
import com.samr.core.utils.LayerResult
import com.samr.domain.entities.Thumbnail
import com.samr.domain.repositories.ImagesRepository

class ImagesUseCase(private val imageRepo: ImagesRepository) {

    fun execute(imageInfo: Thumbnail,
                origin: AspectRatio.Origin,
                callback: (LayerResult<Bitmap>) -> Unit){



        imageRepo.fetchImage(path = imageInfo.path,
                extension = imageInfo.extension,
                size = AspectRatio.ImageSize.MEDIUM,
                origin = origin){ result ->

            when(result){
                is LayerResult.Success -> {
                    callback(result)
                }
                is LayerResult.Error -> {

                }
            }

        }
    }
}