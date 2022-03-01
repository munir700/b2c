package com.yap.uikit.widgets.nameinitialscircleimageview.imageloader

import android.content.Context

object ImageDownloaderSingleton {
    //this should be injected with Dagger2 but may
    //be for a later day

    //Do you see the benefit of ImageDownloader interface???
    //We can just plugin a different image downloader and other
    //code in app will not need a single line of code change. Cool na?
    private var imageDownloader: ImageDownloader = GlideImageDownloader()

    fun getImageDownloader(context: Context): ImageDownloader {
        return imageDownloader
    }

    fun setImageDownloader(imageDownloader: ImageDownloader) {
        ImageDownloaderSingleton.imageDownloader = imageDownloader;
    }
}
