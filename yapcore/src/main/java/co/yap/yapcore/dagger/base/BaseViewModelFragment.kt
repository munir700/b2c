package co.yap.yapcore.dagger.base

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.Toolbar
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import co.yap.yapcore.BaseActivity
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.IBase
import co.yap.yapcore.dagger.base.interfaces.ManageToolBarListener
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import co.yap.yapcore.dagger.di.ViewModelInjectionField
import co.yap.yapcore.dagger.di.components.Injectable
import co.yap.yapcore.dagger.di.qualifiers.ViewModelInjection
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject
import kotlin.properties.Delegates

/**
 * Created by Muhammad Irfan Arshad
 *
 */
abstract class BaseViewModelFragment<VB : ViewDataBinding, S : IBase.State, VM : DaggerBaseViewModel<S>> :
    BaseBindingFragment<VM>(),
    HasSupportFragmentInjector,
    Injectable {
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

    /**
     * Hint provided by the app that this fragment is currently visible to the user, as well as "active".
     * (This is usually set manually (e.g. when using the [androidx.viewpager.widget.ViewPager]) to indicate that the "Page" is active
     * and ready to load data or do something useful)
     */
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
        viewModel.c = requireContext().applicationContext
        viewDataBinding.setVariable(getBindingVariable(), viewModel)
        viewDataBinding.lifecycleOwner = this
        viewDataBinding.executePendingBindings()
        postExecutePendingBindings()
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setBackButtonDispatcher()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //performDataBinding(savedInstanceState)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        check(mActivity is BaseViewModelActivity<*, *, *>) {
//            throw  IllegalStateException("All fragment's container must extend BaseViewModelActivity<*,*,*>")
//        }
    }

    override fun onDestroyView() {
        unregisterStateListeners()
        super.onDestroyView()
    }

    protected fun getmViewModel() = viewModel

    override fun supportFragmentInjector() = fragmentInjector

    fun getBaseActivity() = mActivity

    /**
     * Adding BackButtonDispatcher callback to activity
     */
    private fun setBackButtonDispatcher() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onBackPresse()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    /**
     * Override this method into your fragment to handleBackButton
     */
    fun onBackPresse() {}

    private fun setupToolbar(toolbar: Toolbar?) {
        toolbar?.let {
            it.title = ""
            getBaseActivity().setSupportActionBar(it)
            getBaseActivity().supportActionBar?.apply {
                setDisplayHomeAsUpEnabled(true)
                setHomeButtonEnabled(true)
            }
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


    /**
     * Gets called whenever the [BaseViewModelFragment] becomes "active".
     * (see: [BaseViewModelFragment.isActive])
     */
    protected open fun onBecameActive() {
        //
    }


    /**
     * Gets called whenever the [BaseViewModelFragment] becomes "inactive".
     * (see: [BaseViewModelFragment.isActive])
     */
    protected open fun onBecameInactive() {
        //
    }

    /**
     * Finishes the host [android.app.Activity] (see: [android.app.Activity.finish]).
     */
    protected fun finishActivity() {
        activity?.finish()

    }


    /**
     * Finishes the host [android.app.Activity] affinity (see: [android.app.Activity.finishAffinity]).
     */
    protected fun finishActivityAffinity() {
        activity?.finishAffinity()
    }

    abstract fun getToolBarTitle(): String?
    abstract fun toolBarVisibility(): Boolean?
}