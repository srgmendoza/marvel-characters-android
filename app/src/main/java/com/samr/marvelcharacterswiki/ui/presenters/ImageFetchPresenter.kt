package com.samr.marvelcharacterswiki.ui.presenters

import android.graphics.Bitmap
import android.util.Log
import com.samr.core.utils.AspectRatio
import com.samr.core.utils.LayerResult
import com.samr.domain.usecases.ImagesUseCase
import com.samr.marvelcharacterswiki.models.Thumbnail
import com.samr.marvelcharacterswiki.ui.views.CharacterImageView
import org.koin.java.KoinJavaComponent.inject


class ImageFetchPresenter:CharacterImageView {

    private val imagesUseCase: ImagesUseCase by inject(ImagesUseCase::class.java)

    override fun fetchImage(imageInfo: Thumbnail, origin: AspectRatio.Origin, callback: (Bitmap) -> Unit){

        imagesUseCase.execute(com.samr.domain.entities.Thumbnail(imageInfo.path,imageInfo.extension),origin){ result ->
            when(result){
                is LayerResult.Success -> {
                    callback(result.value)
                }
                is LayerResult.Error -> {
                    Log.e("Error", result.errorInfo.message.toString())
                }
            }
        }
    }
}