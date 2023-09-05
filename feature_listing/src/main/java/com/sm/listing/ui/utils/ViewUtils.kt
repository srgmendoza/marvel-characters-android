package com.sm.listing.ui.utils

import android.app.AlertDialog
import android.content.Context
import android.webkit.URLUtil
import android.widget.ImageView
import com.squareup.picasso.Picasso

object ViewUtils {

    fun onDialog(msg: String, context: Context, onRetry: () -> Unit) {
        val builder = AlertDialog.Builder(context)
        builder.setMessage(msg)
            .setPositiveButton("Yes") { _, _ ->
                onRetry()
            }
            .setNegativeButton("No", null)
            .setCancelable(true)
        val dialog = builder.create()
        dialog.show()
    }

    fun paintImage(url: String, imageView: ImageView) {
        if (URLUtil.isValidUrl(url)) {
            Picasso.get().load(url)
                .fit()
                .into(imageView)
        }
    }
}
