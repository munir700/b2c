package com.yap.yappakistan.ui.onboarding.webview

import com.yap.core.base.interfaces.IBase

interface IWebView {
    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State>

    interface State : IBase.State {
        var pageUrl: String?
    }
}