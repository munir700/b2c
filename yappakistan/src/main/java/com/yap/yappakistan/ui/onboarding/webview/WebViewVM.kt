package com.yap.yappakistan.ui.onboarding.webview

import com.yap.core.base.BaseViewModel
import com.yap.core.base.SingleClickEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WebViewVM @Inject constructor() : BaseViewModel<IWebView.State>(), IWebView.ViewModel {

    override val viewState: IWebView.State = WebViewState()
    override val clickEvent: SingleClickEvent = SingleClickEvent()
}