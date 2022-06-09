package co.yap.yapcore

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.databinding.Observable
import androidx.lifecycle.Observer
import co.yap.translation.Translator
import co.yap.yapcore.firebase.trackScreenViewEvent
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.extentions.hideKeyboard
import co.yap.yapcore.interfaces.OnBackPressedListener


abstract class BaseFragment<V : IBase.ViewModel<*>> : BaseNavFragment(), IBase.View<V>,
    OnBackPressedListener {
    //TODO remove fragmentContext and use fragment context
    lateinit var fragmentContext: Context
    private var progress: Dialog? = null
    override var shouldRegisterViewModelLifeCycle: Boolean = true
    open fun onToolBarClick(id: Int) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        context?.let { progress = Utils.createProgressDialog(it) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        trackScreenViewEvent()
        if (shouldRegisterViewModelLifeCycle)
            registerStateListeners()

    }

    override fun onDestroyView() {
        if (shouldRegisterViewModelLifeCycle)
            unregisterStateListeners()
        progress?.dismiss()
        viewModel.toolBarClickEvent.removeObservers(this)
        viewModel.state.viewState.removeObservers(this)
        hideKeyboard()
        super.onDestroyView()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentContext = context
        if (context is IFragmentHolder) {
            context.onFragmentAttached()
        }

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
            if (isResumed && userVisibleHint) {
                if (progress == null) {
                    context?.let { progress = Utils.createProgressDialog(it) }
                    progress?.show()
                } else {
                    progress?.show()
                }
            }
        } else {
            progress?.dismiss()
        }
        view.hideKeyboard()
    }

    override fun showToast(msg: String) {
        getBaseView()?.showToast(msg)
    }

    private fun getFragmentHolder(): IFragmentHolder? {
        if (requireContext() is IFragmentHolder) {
            return fragmentContext as IFragmentHolder
        }

        return null
    }

    private fun getBaseView(): IBase.View<*>? {
        if (fragmentContext is IBase.View<*>) {
            return fragmentContext as IBase.View<*>
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

    override fun getString(resourceKey: String): String =
        Translator.getString(requireContext(), resourceKey)

    fun getString(resourceKey: String, vararg arg: String): String =
        Translator.getString(requireContext(), resourceKey, *arg)

    override fun onBackPressed(): Boolean = false

    private val stateObserver = object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
            /**
             * please pay special attention to my this check as it is in base class,It is the solution of this crash
             * java.lang.IllegalStateException: Fragment YapCardsFragment{85bc643} (e6f42334-e97f-4b43-8958-11de71fe2c5f)} not attached to an activity.
             */
            if (activity != null && isAdded) {
                if (propertyId == BR.toast && viewModel.state.toast.isNotBlank()) {
                    showToast(viewModel.state.toast)
                }
                if (propertyId == BR.loading) {
                    showLoader(viewModel.state.loading)
                }
            }
        }
    }

    open fun registerStateListeners() {
        viewModel.state.viewState.observe(viewLifecycleOwner, Observer {
            it?.let {
                when (it) {
                    is String -> {
                        viewModel.state.toast = it
                    }
                    is Boolean -> {
                        viewModel.state.loading = it
                    }
                    else -> {

                    }
                }

            }
        })
        viewModel.toolBarClickEvent.observe(this, Observer {
            onToolBarClick(it)
        })
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

    override fun getScreenName(): String? = this::class.java.simpleName
}
