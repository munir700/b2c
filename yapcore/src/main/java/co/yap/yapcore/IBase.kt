package co.yap.yapcore

import android.content.Context
import co.yap.yapcore.helpers.NetworkConnectionManager


interface IBase {
    interface View<V: ViewModel<*>> : NetworkConnectionManager.OnNetworkStateChangeListener {
        val viewModel: V
        fun showLoader(isVisible: Boolean)
        fun showToast(msg: String)
        fun onBackPressed()
        fun showInternetSnack(isVisible: Boolean)
        fun isPermissionGranted(permission: String): Boolean
        fun requestPermissions()
    }

    interface ViewModel<S : State> : ILifecycle {
        val state: S
        val context: Context
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
