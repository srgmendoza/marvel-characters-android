package com.samr.marvelcharacterswiki.ui.views

import android.graphics.Bitmap
import com.samr.core.utils.AspectRatio
import com.samr.marvelcharacterswiki.models.Thumbnail

interface CharacterImageView {

    fun fetchImage(imageInfo: Thumbnail, origin: AspectRatio.Origin, callback: (Bitmap) -> Unit)

}