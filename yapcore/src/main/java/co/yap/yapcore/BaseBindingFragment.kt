package co.yap.yapcore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.lifecycleScope
import co.yap.yapcore.helpers.extentions.getCountUnreadMessage
import co.yap.yapcore.managers.isUserLogin
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.fragment.app.Fragment
import co.yap.yapcore.dagger.base.interfaces.CanFetchExtras

abstract class BaseBindingFragment<V : IBase.ViewModel<*>> : BaseFragment<V>(),CanFetchExtras {

    lateinit var viewDataBinding: ViewDataBinding
    /**
     * Indicates whether the current [BaseBindingFragment]'s content view is initialized or not.
     */
    var isViewCreated = false
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        // dependencies will be injected only once (based on the state of the content view)
        if(!isViewCreated) {
            injectDependencies()
        }
        super.onCreate(savedInstanceState)
        // the overall initialization, extras fetching and post initialization will be performed only once, too
        if(!isViewCreated) {
            arguments?.let(::fetchExtras)
            preInit()
        }
        setHasOptionsMenu(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val wasViewCreated = isViewCreated
        isViewCreated = true

        // performing the initialization only in cases when the view was created for the first time
        if(!wasViewCreated) {
            init(savedInstanceState)
            postInit()
        }
        performDataBinding(savedInstanceState)

    }

    override fun performDataBinding(savedInstanceState: Bundle?) {
        viewDataBinding.setVariable(getBindingVariable(), viewModel)
        //viewDataBinding.lifecycleOwner = this
        viewDataBinding.executePendingBindings()
        postExecutePendingBindings(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        if (shouldShowChatChatOverLay() == true)
            requireActivity().getCountUnreadMessage()
    }

    fun launch(dispatcher: Dispatcher = Dispatcher.Main, block: suspend () -> Unit) {
        lifecycleScope.launch(
            when (dispatcher) {
                Dispatcher.Main -> Dispatchers.Main
                Dispatcher.Background -> Dispatchers.IO
                Dispatcher.LongOperation -> Dispatchers.Default
            }
        ) { block() }
    }

    open fun shouldShowChatChatOverLay() = requireContext().isUserLogin()

    fun <VB : ViewDataBinding> getDataBindingView() = viewDataBinding as VB

    /**MV
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

    /**
     * Get's called when it's the right time for you to initialize the UI elements.
     *
     * @param savedInstanceState the state bundle brought from the [Fragment.onViewCreated]
     */
    protected open fun init(savedInstanceState : Bundle?) {
        //
    }


    /**
     * Gets called right after the UI initialization.
     */
    protected open fun postInit() {
        //
    }
    /**
     * Gets called right after the UI executePendingBindings.
     */
    protected open fun postExecutePendingBindings(savedInstanceState: Bundle?) {
        //
    }
    /**
     * Gets called when it's the right time for you to inject the dependencies.
     */
    open fun injectDependencies() {
        //
    }
    /**
     * Gets called right before the UI initialization.
     */
    protected open fun preInit() {
        //
    }

    override fun fetchExtras(extras: Bundle?) {

    }
}