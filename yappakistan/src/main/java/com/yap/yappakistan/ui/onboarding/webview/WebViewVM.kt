package com.yap.yappakistan.ui.onboarding.webview

import androidx.hilt.lifecycle.ViewModelInject
import com.yap.core.base.BaseViewModel
import com.yap.core.base.SingleClickEvent

class WebViewVM @ViewModelInject constructor() : BaseViewModel<IWebView.State>(),
    IWebView.ViewModel {

    override val viewState: IWebView.State = WebViewState()
    override val clickEvent: SingleClickEvent = SingleClickEvent()
}