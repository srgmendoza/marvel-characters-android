package com.sm.base_core

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ProgressBar
import androidx.constraintlayout.widget.ConstraintLayout
import com.sm.base_core.databinding.ViewLoaderBinding

class GenericLoader @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    val binding = ViewLoaderBinding.inflate(
        LayoutInflater.from(context), this, false)

    init {
        visibility = View.GONE
    }

    fun show(withBackground: Boolean) {
        if(withBackground) setBackgroundColor(Color.BLACK)
        visibility = View.VISIBLE
    }

    fun hide() {
        setBackgroundColor(Color.TRANSPARENT)
        visibility = View.GONE
    }
}