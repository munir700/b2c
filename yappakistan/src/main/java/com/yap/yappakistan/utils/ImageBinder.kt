package com.yap.yappakistan.utils

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter

object ImageBinder {
    @JvmStatic
    @BindingAdapter(value = ["loadImg", "errorImg", "placeHolder"], requireAll = false)
    fun loadImageSrc(
        imageView: ImageView,
        resource: String?,
        errorRecourse: Drawable? = null,
        placeRecourse: Drawable? = null
    ) {
//        errorRecourse?.let { err ->
//            Glide.with(imageView).load(resource).placeholder(placeRecourse).error(err)
//                .into(imageView)
//        } ?: Glide.with(imageView).load(resource).into(imageView)
    }

    fun ImageView.loadImage(
        resource: String?,
        errorRecourse: Drawable? = null,
        placeRecourse: Drawable? = null
    ) {
//        errorRecourse?.let { err ->
//            Glide.with(this).load(resource).placeholder(placeRecourse).error(err)
//                .into(this)
//        } ?: Glide.with(this).load(resource).into(this)

    }
}