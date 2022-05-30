/*
package co.yap.yapcore.dagger.base

import android.app.Fragment
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.IBase
import co.yap.yapcore.dagger.base.interfaces.CanFetchExtras
import co.yap.yapcore.dagger.base.interfaces.CanHandleOnClick
import co.yap.yapcore.dagger.base.interfaces.OnClickHandler
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import co.yap.yapcore.dagger.di.ViewModelInjectionField
import co.yap.yapcore.dagger.di.components.Injectable
import co.yap.yapcore.dagger.di.qualifiers.ViewModelInjection
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasFragmentInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

*/
/**
 * Created by Muhammad Irfan Arshad
 *
 *//*


abstract class BaseViewModelActivity<VB : ViewDataBinding, S : IBase.State, VM : DaggerBaseViewModel<S>> :
    BaseBindingActivity<VB,VM>(), HasFragmentInjector, HasSupportFragmentInjector,
    Injectable, CanFetchExtras, CanHandleOnClick {

    lateinit var mViewDataBinding: VB
        private set

    @Inject
    @ViewModelInjection
    lateinit var mViewModel: ViewModelInjectionField<VM>

    @Inject
    lateinit var supportFragment: DispatchingAndroidInjector<androidx.fragment.app.Fragment>

    @Inject
    lateinit var frameworkFragment: DispatchingAndroidInjector<Fragment>
    override var shouldRegisterViewModelLifeCycle: Boolean = false

    override lateinit var viewModel: VM

    @Inject
    lateinit var state: S

    */
/**
     * Gets called when it's the right time for you to inject the dependencies.
     *//*

    override fun injectDependencies() {
        AndroidInjection.inject(this)
    }

    override fun performDataBinding(savedInstanceState: Bundle?) {

        mViewDataBinding = DataBindingUtil
            .setContentView(this, getLayoutId())
        viewDataBinding = mViewDataBinding
        viewModel = mViewModel.get()
        registerStateListeners()
        mViewDataBinding.setVariable(getBindingVariable(), viewModel)
        mViewDataBinding.lifecycleOwner = this
        init(savedInstanceState)
        postInit()
        mViewDataBinding.executePendingBindings()
        if (viewModel is OnClickHandler) {
            viewModel.clickEvent?.observe(this, Observer { onClick(it) })
        }
        postExecutePendingBindings(savedInstanceState)
        viewModel.onCreate(intent.extras)
    }

    override fun supportFragmentInjector() = supportFragment
    override fun fragmentInjector() = frameworkFragment
    override fun onDestroy() {
        unregisterStateListeners()
        super.onDestroy()
    }
}
*/
