package co.yap.billpayments.billers.search

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.billpayments.BR
import co.yap.billpayments.R
import co.yap.billpayments.base.PayBillBaseFragment
import co.yap.billpayments.databinding.FragmentBillerSearchBinding
import co.yap.widgets.MultiStateView
import co.yap.widgets.State
import co.yap.widgets.Status
import co.yap.widgets.searchwidget.SearchingListener
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.extentions.hideKeyboard

class BillerSearchFragment : PayBillBaseFragment<IBillerSearch.ViewModel>(),
    IBillerSearch.View, SearchingListener {
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_biller_search

    override val viewModel: IBillerSearch.ViewModel
        get() = ViewModelProviders.of(this).get(BillerSearchViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
    }

    override fun setObservers() {
        getBindings().svBiller.initializeSearch(this)
        viewModel.clickEvent.observe(this, clickEventObserver)
        viewModel.state.stateLiveData?.observe(this, Observer { handleState(it) })
        viewModel.adapter.filterCount.observe(this, Observer {
            viewModel.state.stateLiveData?.value =
                if (it == 0) State.empty("") else State.success("")
        })
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

    private val clickEventObserver = Observer<Int> {
        when (it) {
            R.id.btnAction -> {
            }
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
        viewModel.clickEvent.removeObservers(this)
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
