package com.yap.yappakistan.utils

import androidx.viewbinding.ViewBinding

interface BaseViewHolder {
    fun onBind(viewBinding: ViewBinding?)
    fun notifyDatasetRefresh(data: Any)
}