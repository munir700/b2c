package co.yap.yapcore.dagger.base;

import android.app.Fragment
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.IBase
import co.yap.yapcore.dagger.di.ViewModelInjectionField
import co.yap.yapcore.dagger.di.components.Injectable
import co.yap.yapcore.dagger.di.qualifiers.ViewModelInjection
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasFragmentInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

/**
 * Created by Muhammad Irfan Arshad
 *
 */

abstract class BaseViewModelActivity<VB : ViewDataBinding, S : IBase.State, VM : BaseViewModel<S>> :
    BaseBindingActivity<VM>(), HasFragmentInjector, HasSupportFragmentInjector,
    Injectable {
    private lateinit var mViewDataBinding: VB

    @Inject
    @ViewModelInjection
    lateinit var mViewModel: ViewModelInjectionField<VM>
    @Inject
    lateinit var supportFragment: DispatchingAndroidInjector<androidx.fragment.app.Fragment>
    @Inject
    lateinit var frameworkFragment: DispatchingAndroidInjector<Fragment>

    override lateinit var viewModel: VM
    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun performDataBinding() {
        mViewDataBinding = DataBindingUtil
            .setContentView(this, getLayoutId())
        viewModel = mViewModel.get()
        mViewDataBinding.setVariable(getBindingVariable(), viewModel)
        mViewDataBinding.lifecycleOwner = this
        mViewDataBinding.executePendingBindings()
        //viewModel?.onCreate(intent.extras, navigatorHelper)
    }

    fun getMViewDataBinding(): VB = mViewDataBinding

    override fun supportFragmentInjector() = supportFragment


    override fun fragmentInjector() = frameworkFragment

//    fun setLoading(loading: Boolean) {
////        (activity as BaseActivity).setLoading(loading)
//    }
}