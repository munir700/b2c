package com.yap.yappakistan.networking.apiclient.models

import com.google.gson.annotations.SerializedName
import com.yap.yappakistan.networking.apiclient.base.BaseApiResponse


class BaseListResponse<T : Any> : BaseApiResponse() {
    @SerializedName("data")
    var data: MutableList<T>? = mutableListOf()
}