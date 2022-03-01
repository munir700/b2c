package com.yap.uikit.widgets.nameinitialscircleimageview.imageloader

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView

interface ImageDownloader {
    fun downloadImage(
        context: Context,
        url: String,
        imageView: ImageView,
        placeHolder: Drawable,
        signature: String?
    )

    fun invalidateImage(context: Context, url: String)
}