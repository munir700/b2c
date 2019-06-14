package co.yap.yapcore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseBindingFragment : BaseFragment() {

    protected lateinit var viewDataBinding: ViewDataBinding
    protected lateinit var baseViewModel: BaseViewModel
    private var mRootView: View? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        baseViewModel = getViewModel()
        setHasOptionsMenu(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = DataBindingUtil.inflate<ViewDataBinding>(inflater, getLayoutId(), container, false)
        mRootView = viewDataBinding.getRoot()
        return mRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.baseViewModel = if (baseViewModel == null) getViewModel() else baseViewModel
        viewDataBinding.setVariable(getBindingVariable(), baseViewModel)
        viewDataBinding.executePendingBindings()
    }

    /**
     * Override for set binding variable
     *
     * @return variable id
     */

    abstract fun getBindingVariable(): Int

    /**
     * @return layout resource id
     */

    @LayoutRes
    abstract fun getLayoutId(): Int

}