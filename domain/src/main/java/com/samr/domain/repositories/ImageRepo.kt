package com.samr.domain.repositories

import android.graphics.Bitmap
import com.samr.core.utils.AspectRatio
import com.samr.core.utils.LayerResult


interface ImageRepo {

    suspend fun fetchImage(url: String,
                   callback: (LayerResult<Bitmap>?) -> Unit)
}