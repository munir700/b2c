package co.yap.yapcore

import android.content.Context
import co.yap.yapcore.helpers.NetworkConnectionManager


interface IBase {
    interface View : NetworkConnectionManager.OnNetworkStateChangeListener {
        fun getContext(): Context
        fun showLoader(isVisible: Boolean)
        fun showToast(msg: String)
        // fun <T : ViewModel> getViewModel(): T
        fun onBackPressed()
        fun showInternetSnack(isVisible: Boolean)
        fun isPermissionGranted(permission: String): Boolean
        fun requestPermissions()
    }

    interface ViewModel<S : State> : ILifecycle {
        val state: S
        fun getContext(): Context
        fun getString(resourceId: Int): String
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
