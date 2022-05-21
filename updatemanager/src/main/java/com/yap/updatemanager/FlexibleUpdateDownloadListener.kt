package com.yap.updatemanager

interface FlexibleUpdateDownloadListener {
    fun onDownloadProgress(bytesDownloaded: Long, totalBytes: Long){}
    fun onDownloadComplete(){}
}