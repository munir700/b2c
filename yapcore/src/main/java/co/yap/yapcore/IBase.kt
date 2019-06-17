package co.yap.yapcore

import android.content.Context
import co.yap.yapcore.helpers.NetworkManager


interface IBase {
    interface View : NetworkManager.OnNetworkStateChangeListener {
        fun getContext(): Context
        fun showLoader(isVisible: Boolean)
        fun showToast(msg: String)
        fun <T : IBase.ViewModel> getViewModel(): T
        fun onBackPressed()
        fun showInternetSnack(isVisible: Boolean)
    }

    interface ViewModel : ILifecycle {
        fun getState(): IBase.State
        fun getContext(): Context
        fun getString(): String

    }

    interface State {
        fun isLoading(): Boolean
        fun getToolbarTitle(): String
        fun getError(): String
        fun reset()
        fun getString(key: String): String
        fun destroy()
        fun init()
        fun resume()
        fun pause()
    }
}
