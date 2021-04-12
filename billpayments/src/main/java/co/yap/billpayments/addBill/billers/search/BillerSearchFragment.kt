package co.yap.billpayments.addBill.billers.search

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.billpayments.BR
import co.yap.billpayments.R
import co.yap.billpayments.addBill.base.AddBillBaseFragment
import co.yap.billpayments.databinding.FragmentBillerSearchBinding
import co.yap.networking.customers.responsedtos.billpayment.BillerCatalogModel
import co.yap.widgets.MultiStateView
import co.yap.widgets.State
import co.yap.widgets.Status
import co.yap.widgets.searchwidget.SearchingListener
import co.yap.yapcore.helpers.extentions.hideKeyboard
import co.yap.yapcore.interfaces.OnItemClickListener

class BillerSearchFragment : AddBillBaseFragment<IBillerSearch.ViewModel>(),
    IBillerSearch.View, SearchingListener {
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_biller_search

    override val viewModel: BillerSearchViewModel
        get() = ViewModelProviders.of(this).get(BillerSearchViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        setObservers()
    }

    override fun onResume() {
        super.onResume()
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
    }

    override fun setObservers() {
        getBindings().svBiller.initializeSearch(this)
        viewModel.state.stateLiveData?.observe(this, Observer { handleState(it) })
        viewModel.adapter.filterCount.observe(this, Observer {
            viewModel.state.stateLiveData?.value =
                if (it == 0) State.empty("") else State.success("")
        })
        viewModel.adapter.setItemListener(onItemClickListener)
    }

    val onItemClickListener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            viewModel.parentViewModel?.selectedBillerCatalog = (data as BillerCatalogModel)
            navigate(R.id.action_billerSearchFragment_to_billerDetailFragment)
        }
    }

    private fun handleState(state: State?) {
        when (state?.status) {
            Status.EMPTY -> {
                getBindings().multiStateView.viewState = MultiStateView.ViewState.EMPTY
            }
            Status.ERROR -> {
                getBindings().multiStateView.viewState = MultiStateView.ViewState.ERROR
            }
            Status.SUCCESS -> {
                getBindings().multiStateView.viewState = MultiStateView.ViewState.CONTENT
            }
            else -> throw IllegalStateException("Provided multi state is not handled $state")
        }
    }

    override fun onTypingSearch(search: String?) {
        viewModel.adapter.filter.filter(search)
    }

    override fun onCancel() {
        getBindings().svBiller.hideKeyboard()
        navigateBack()
    }

    override fun removeObservers() {
        viewModel.adapter.filterCount.removeObservers(this)
        viewModel.state.stateLiveData?.removeObservers(this)
    }

    override fun onStop() {
        super.onStop()
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
    }

    override fun onDestroy() {
        super.onDestroy()
        removeObservers()
    }

    override fun onBackPressed(): Boolean {
        return true
    }

    private fun getBindings(): FragmentBillerSearchBinding =
        viewDataBinding as FragmentBillerSearchBinding
}
