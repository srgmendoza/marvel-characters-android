package com.samr.data.repositories

import android.graphics.Bitmap
import com.samr.core.utils.AspectRatio
import com.samr.core.utils.LayerResult


interface ImagesRepository {

    fun fetchImage(path: String,
                   extension: String,
                   size: AspectRatio.ImageSize,
                   origin: AspectRatio.Origin,
                   callback: (LayerResult<Bitmap>?) -> Unit)
}