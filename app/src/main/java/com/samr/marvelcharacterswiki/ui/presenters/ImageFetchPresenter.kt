package com.samr.marvelcharacterswiki.ui.presenters

import android.graphics.Bitmap
import com.samr.core.utils.AspectRatio
import com.samr.core.utils.LayerResult
import com.samr.domain.usecases.ImagesUseCase
import com.samr.marvelcharacterswiki.ui.models.Thumbnail
import com.samr.marvelcharacterswiki.ui.views.CharacterImageView


class ImageFetchPresenter:CharacterImageView {

    private var imagesUseCase: ImagesUseCase = ImagesUseCase()

    override fun fetchImage(imageInfo: Thumbnail, origin: AspectRatio.Origin, callback: (Bitmap) -> Unit){

        imagesUseCase.execute(com.samr.domain.entities.Thumbnail(imageInfo.path,imageInfo.extension),origin){ result ->
            when(result){
                is LayerResult.Success -> {
                    callback(result.value)
                }
            }
        }
    }
}