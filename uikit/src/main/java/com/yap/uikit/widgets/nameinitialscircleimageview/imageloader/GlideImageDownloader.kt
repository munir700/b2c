package com.yap.uikit.widgets.nameinitialscircleimageview.imageloader

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

class GlideImageDownloader : ImageDownloader {
    override fun downloadImage(
        context: Context,
        url: String,
        imageView: ImageView,
        placeHolder: Drawable,
        signature: String?
    ) {
        signature?.let {

        }
        val requestOptions = RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.AUTOMATIC)
        Glide.with(imageView).load(url).apply(requestOptions)
            .error(placeHolder)
            .placeholder(placeHolder)
            .into(imageView)
    }

    override fun invalidateImage(context: Context, url: String) {

    }
}