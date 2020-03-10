package co.yap.yapcore.dagger.base

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import co.yap.yapcore.BaseActivity
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.IBase
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
abstract class BaseViewModelFragment<VB : ViewDataBinding, S : IBase.State, VM : BaseViewModel<S>> :
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
    override lateinit var viewModel: VM


    override fun performDataBinding() {
        viewModel = mViewModel.get()
//        mViewModel.onCreate(arguments, navigatorHelper)
        viewDataBinding.setVariable(getBindingVariable(), viewModel)
        getMViewDataBinding().lifecycleOwner = this
        getMViewDataBinding().executePendingBindings()
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as BaseActivity<*>
        AndroidSupportInjection.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        performDataBinding()
    }

    fun getMViewDataBinding() = viewDataBinding as VB
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        check(mActivity is BaseViewModelActivity<*, *, *>) {
            throw  IllegalStateException("All fragment's container must extend BaseViewModelActivity<*,*,*>")
        }
    }

    protected fun getmViewModel() = mViewModel

    override fun supportFragmentInjector() = fragmentInjector

    fun getBaseActivity() = mActivity
}