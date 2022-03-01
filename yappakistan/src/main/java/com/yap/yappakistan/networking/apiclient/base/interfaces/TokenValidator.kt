package com.yap.yappakistan.networking.apiclient.base.interfaces

internal interface TokenValidator {
    var tokenRefreshInProgress: Boolean
    fun invalidate()
}