package co.yap.yapcore.dagger.base

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import co.yap.yapcore.BaseActivity
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.IBase
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import co.yap.yapcore.dagger.di.ViewModelInjectionField
import co.yap.yapcore.dagger.di.components.Injectable
import co.yap.yapcore.dagger.di.qualifiers.ViewModelInjection
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

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
    var mViewDataBinding = viewDataBinding as VB
        private set
    override var shouldRegisterViewModelLifeCycle: Boolean = false


    override fun performDataBinding(savedInstanceState: Bundle?) {
        viewModel = mViewModel.get()
        registerStateListeners()
        viewModel.onCreate(arguments)
        viewDataBinding.setVariable(getBindingVariable(), viewModel)
        mViewDataBinding.lifecycleOwner = this
        mViewDataBinding.executePendingBindings()
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
}