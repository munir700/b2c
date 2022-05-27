/*
package co.yap.yapcore.dagger.base

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.Toolbar
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import co.yap.yapcore.BaseActivity
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.IBase
import co.yap.yapcore.dagger.base.interfaces.CanHandleOnClick
import co.yap.yapcore.dagger.base.interfaces.OnClickHandler
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import co.yap.yapcore.dagger.di.ViewModelInjectionField
import co.yap.yapcore.dagger.di.components.Injectable
import co.yap.yapcore.dagger.di.qualifiers.ViewModelInjection
import co.yap.yapcore.helpers.cancelAllSnackBar
import co.yap.yapcore.helpers.validation.IValidator
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject
import kotlin.properties.Delegates

*/
/**
 * Created by Muhammad Irfan Arshad
 *
 *//*

abstract class BaseViewModelFragment<VB : ViewDataBinding, S : IBase.State, VM : DaggerBaseViewModel<S>> :
    BaseBindingFragment<VM>(),
    HasSupportFragmentInjector,
    Injectable, CanHandleOnClick {
    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var mActivity: BaseActivity<*>

    @Inject
    @ViewModelInjection
    lateinit var mViewModel: ViewModelInjectionField<VM>

    @Inject
    lateinit var state: S
    override lateinit var viewModel: VM
    lateinit var mViewDataBinding: VB
        private set
    override var shouldRegisterViewModelLifeCycle: Boolean = false

    */
/**
     * Hint provided by the app that this fragment is currently visible to the user, as well as "active".
     * (This is usually set manually (e.g. when using the [androidx.viewpager.widget.ViewPager]) to indicate that the "Page" is active
     * and ready to load data or do something useful)
     *//*

    var isActive by Delegates.observable(true) { _, oldValue, newValue ->
        onActiveStateChange(
            oldValue,
            newValue
        )
    }

    override fun performDataBinding(savedInstanceState: Bundle?) {
        mViewDataBinding = viewDataBinding as VB
        viewModel = mViewModel.get()
        registerStateListeners()
        viewModel.fetchExtras(arguments)
        viewModel.c = requireContext()
        viewDataBinding.setVariable(getBindingVariable(), viewModel)
        viewDataBinding.lifecycleOwner = this
        viewDataBinding.executePendingBindings()
        if (viewModel is IValidator) {
            (viewModel as IValidator).validator?.targetViewBinding = mViewDataBinding
        }
        if (viewModel is OnClickHandler) {
            viewModel.clickEvent?.observe(this, Observer { onClick(it) })
        }
        postExecutePendingBindings(savedInstanceState)
        viewModel.onCreate(arguments)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as BaseActivity<*>

    }

    override fun injectDependencies() {
        super.injectDependencies()
        AndroidSupportInjection.inject(this)
    }

    override fun onDestroyView() {
        if (viewModel is OnClickHandler)
            viewModel.clickEvent?.removeObservers(this)
        unregisterStateListeners()
//        view?.hideKeyboard()
//        hideKeyboard()
        super.onDestroyView()
        cancelAllSnackBar()
    }

    protected fun getmViewModel() = viewModel

    override fun supportFragmentInjector() = fragmentInjector

    fun getBaseActivity() = mActivity

    */
/**
     * Adding BackButtonDispatcher callback to activity
     *//*

    fun setBackButtonDispatcher() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onBackPressed()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    fun setupToolbar(
        toolbar: Toolbar?,
        toolbarMenu: Int? = null, setActionBar: Boolean = true,
        navigationOnClickListener: ((View) -> Unit?)? = null
    ) {
        toolbar?.apply {
            title = ""
            setHomeAsUpIndicator()?.let { setNavigationIcon(it) }
            getBaseActivity().setSupportActionBar(this)
            navigationOnClickListener?.let { l -> this.setNavigationOnClickListener { l.invoke(it) } }
            if (setActionBar) {
                getBaseActivity().supportActionBar?.apply {
                    setDisplayHomeAsUpEnabled(setDisplayHomeAsUpEnabled() ?: true)
                    setHomeButtonEnabled(setDisplayHomeAsUpEnabled() ?: true)
                    setDisplayShowCustomEnabled(setDisplayHomeAsUpEnabled() ?: true)
                    setHomeAsUpIndicator()?.let { setHomeAsUpIndicator(it) }
                }
            }
            toolbarMenu?.let { this.inflateMenu(it) }
        }
    }

    private fun onActiveStateChange(oldState: Boolean, newState: Boolean) {
        if (oldState == newState) {
            return
        }
        if (newState) {
            onBecameActive()
        } else {
            onBecameInactive()
        }
    }

    */
/**
     * Gets called whenever the [BaseViewModelFragment] becomes "active".
     * (see: [BaseViewModelFragment.isActive])
     *//*

    protected open fun onBecameActive() {
        //
    }


    */
/**
     * Gets called whenever the [BaseViewModelFragment] becomes "inactive".
     * (see: [BaseViewModelFragment.isActive])
     *//*

    protected open fun onBecameInactive() {
        //
    }

    */
/**
     * Finishes the host [android.app.Activity] (see: [android.app.Activity.finish]).
     *//*

    protected fun finishActivity() {
        activity?.finish()
    }

    */
/**
     * Finishes the host [android.app.Activity] affinity (see: [android.app.Activity.finishAffinity]).
     *//*

    protected fun finishActivityAffinity() {
        activity?.finishAffinity()
    }

    abstract fun getToolBarTitle(): String?
    abstract fun toolBarVisibility(): Boolean?
    abstract fun setDisplayHomeAsUpEnabled(): Boolean?
    abstract fun setHomeAsUpIndicator(): Int?
}
*/
