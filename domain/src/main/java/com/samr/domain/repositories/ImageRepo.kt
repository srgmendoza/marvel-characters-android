package com.samr.domain.repositories

import android.graphics.Bitmap
import com.samr.data.utils.LayerResult

interface ImageRepo {

    suspend fun fetchImage(
        url: String,
        callback: (com.samr.data.utils.LayerResult<Bitmap>?) -> Unit
    )
}
