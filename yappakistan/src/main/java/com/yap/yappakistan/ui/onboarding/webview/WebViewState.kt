package com.yap.yappakistan.ui.onboarding.webview

import com.yap.core.base.BaseState
import javax.inject.Inject

class WebViewState @Inject constructor() : BaseState(), IWebView.State {
    override var pageUrl: String? = ""
}