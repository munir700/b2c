package co.yap.yapcore

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.databinding.Observable
import co.yap.translation.Translator
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.extentions.hideKeyboard
import co.yap.yapcore.interfaces.OnBackPressedListener


abstract class BaseFragment<V : IBase.ViewModel<*>> : BaseNavFragment(), IBase.View<V>,
    OnBackPressedListener {
    private var progress: Dialog? = null
    override var shouldRegisterViewModelLifeCycle: Boolean = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        progress = Utils.createProgressDialog(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (shouldRegisterViewModelLifeCycle)
            registerStateListeners()
    }

    override fun onDestroyView() {
        if (shouldRegisterViewModelLifeCycle)
            unregisterStateListeners()
        progress?.dismiss()
        hideKeyboard()
        super.onDestroyView()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is IFragmentHolder) {
            context.onFragmentAttached()
        }
//        else {
//            throw IllegalStateException("Could not find reference to IFragmentHolder. Make sure parent activity implements IFragmentHolder interface")
//        }

        if (context !is IBase.View<*>) {
            throw IllegalStateException("Could not find reference to IBase.View. Make sure parent activity implements IBase.View interface")
        }
    }

    override fun onDetach() {
        super.onDetach()
        if (getFragmentHolder() != null) {
            getFragmentHolder()?.onFragmentDetached("")
        }
    }


    override fun showLoader(isVisible: Boolean) {
        if (isVisible) {
            if (isResumed && userVisibleHint) progress?.show()
        } else progress?.dismiss()
        view.hideKeyboard()
    }

    override fun showToast(msg: String) {
        getBaseView()?.showToast(msg)
    }

    private fun getFragmentHolder(): IFragmentHolder? {
        if (context is IFragmentHolder) {
            return context as IFragmentHolder
        }

        return null
    }

    private fun getBaseView(): IBase.View<*>? {
        if (context is IBase.View<*>) {
            return context as IBase.View<*>
        }

        return null
    }

    override fun showInternetSnack(isVisible: Boolean) {
        getBaseView()?.showInternetSnack(isVisible)
    }

    override fun onNetworkStateChanged(isConnected: Boolean) {
        getBaseView()?.onNetworkStateChanged(isConnected)
    }

    override fun isPermissionGranted(permission: String): Boolean {
        return getBaseView()?.isPermissionGranted(permission)!!
    }

    override fun requestPermissions() {
        getBaseView()?.requestPermissions()
    }

    // TODO: Support PermissionsManager.OnPermissionGrantedListener functions in BaseFragment

    override fun getString(resourceKey: String): String =
        Translator.getString(requireContext(), resourceKey)

    fun getString(resourceKey: String, vararg arg: String): String =
        Translator.getString(requireContext(), resourceKey, *arg)

    override fun onBackPressed(): Boolean = false

    private val stateObserver = object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
            if (propertyId == BR.toast && viewModel.state.toast.isNotBlank()) {
                showToast(viewModel.state.toast)
            }
            if (propertyId == BR.loading) {
                showLoader(viewModel.state.loading)
            }
        }
    }

    open fun registerStateListeners() {
        if (viewModel is BaseViewModel<*>) {
            viewModel.registerLifecycleOwner(this)
        }
        if (viewModel.state is BaseState) {
            (viewModel.state as BaseState).addOnPropertyChangedCallback(stateObserver)
        }
    }

    open fun unregisterStateListeners() {
        if (viewModel is BaseViewModel<*>) {
            viewModel.unregisterLifecycleOwner(this)
        }
        if (viewModel.state is BaseState) {
            (viewModel.state as BaseState).removeOnPropertyChangedCallback(stateObserver)
        }
    }
}
