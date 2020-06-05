package co.yap.yapcore

import android.content.Context
import android.os.Bundle
import co.yap.yapcore.helpers.NetworkConnectionManager

interface IBase {
    interface View<V : ViewModel<*>> : NetworkConnectionManager.OnNetworkStateChangeListener {
        val viewModel: V
        fun showLoader(isVisible: Boolean)
        fun showToast(msg: String)
        fun showInternetSnack(isVisible: Boolean)
        fun isPermissionGranted(permission: String): Boolean
        fun requestPermissions()
        fun getString(resourceKey: String): String
        fun performDataBinding(savedInstanceState : Bundle?)
        var shouldRegisterViewModelLifeCycle:Boolean
    }

    interface ViewModel<S : State> : ILifecycle {
        val state: S
        val context: Context
        fun getString(resourceId: Int): String
        fun getString(resourceId: String): String
    }

    interface State {
        var toast: String
        var loading: Boolean
        var toolbarTitle: String
        var toolsBarVisibility: Boolean
        var error: String
        fun reset()
        // fun getString(key: String): String
        fun destroy()

        fun init()
        fun resume()
        fun pause()
    }
}
