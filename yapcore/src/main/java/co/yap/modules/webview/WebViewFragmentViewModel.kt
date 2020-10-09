package co.yap.modules.webview

import android.app.Application
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent

class WebViewFragmentViewModel (application: Application) :
    BaseViewModel<IWebViewFragment.State>(application = application), IWebViewFragment.ViewModel {
    override val state: WebViewFragmentState = WebViewFragmentState(application = application)
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

}