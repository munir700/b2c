package co.yap.yapcore.hilt.base.viewmodel

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import co.yap.yapcore.IBase
import co.yap.yapcore.dagger.base.interfaces.CanFetchExtras
import co.yap.yapcore.dagger.base.interfaces.CanHandleOnClick
import co.yap.yapcore.dagger.base.interfaces.OnClickHandler
import co.yap.yapcore.hilt.base.activity.BaseBindingActivityV2

/**
 * Created by Safi ur Rehman
 */
abstract class BaseViewModelActivityV2<VB : ViewDataBinding, S : IBase.State, VM : HiltBaseViewModel<S>> :
    BaseBindingActivityV2<VM>(), CanFetchExtras, CanHandleOnClick {

    override var shouldRegisterViewModelLifeCycle: Boolean = false

    lateinit var mViewDataBinding: VB
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBindingView(this, getLayoutId()) {
            mViewDataBinding = it
            performDataBinding(savedInstanceState)
        }
    }

    private fun setupBindingView(
        fragmentActivity: FragmentActivity,
        layoutResId: Int,
        set: (VB) -> Unit
    ): VB {
        return DataBindingUtil.setContentView<VB>(fragmentActivity, layoutResId).also {
            set(it)
        }
    }

    override fun performDataBinding(savedInstanceState: Bundle?) {
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

    override fun onDestroy() {
        unregisterStateListeners()
        super.onDestroy()
    }
}
