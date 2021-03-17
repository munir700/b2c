package co.yap.sendmoney.currencyPicker.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.sendmoney.BR
import co.yap.sendmoney.R
import co.yap.sendmoney.currencyPicker.interfaces.ICurrencyPicker
import co.yap.sendmoney.currencyPicker.model.MultiCurrencyWallet
import co.yap.sendmoney.currencyPicker.viewmodel.CurrencyPickerViewModel
import co.yap.sendmoney.databinding.FragmentCurrencyPickerBinding
import co.yap.widgets.MultiStateView
import co.yap.widgets.State
import co.yap.widgets.Status
import co.yap.widgets.searchwidget.SearchingListener
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.interfaces.OnItemClickListener

class CurrencyPickerFragment : BaseBindingFragment<ICurrencyPicker.ViewModel>(),
    ICurrencyPicker.View, SearchingListener {

    companion object {
        const val IS_DIALOG_POP_UP = "IS_DIALOG_POP_UP"
        const val LIST_OF_CURRENCIES = "LIST_OF_CURRENCIES"
    }

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_currency_picker

    override val viewModel: CurrencyPickerViewModel
        get() = ViewModelProviders.of(this).get(CurrencyPickerViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { bundle ->
            bundle.getBoolean(IS_DIALOG_POP_UP).let {
                viewModel.state.currencyDialogChecker.set(it)

            }
            bundle.getParcelableArrayList<MultiCurrencyWallet>(LIST_OF_CURRENCIES)?.let {
                viewModel.availableCurrenciesList = it
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        getBindings().svBeneficiary.initializeSearch(this)
    }

    private fun setListeners() {
        viewModel.currencyAdapter.setItemListener(currencySelectedItemClickListener)
        viewModel.currencyAdapter.allowFullItemClickListener = true
        viewModel.state.stateLiveData?.observe(this, Observer { handleState(it) })
        viewModel.currencyAdapter.filterCount.observe(this, Observer {
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

    private val currencySelectedItemClickListener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            setResultData(data as MultiCurrencyWallet)
        }
    }

    fun setResultData(multiCurrencyWallet: MultiCurrencyWallet) {
        val intent = Intent()
        intent.putExtra(Constants.CURRENCYWALLET, multiCurrencyWallet)
        activity?.setResult(Activity.RESULT_OK, intent)
        activity?.finish()
    }

    override fun onCancel() {
        activity?.finish()
    }

    override fun onSearchKeyPressed(search: String?) {
    }

    override fun onTypingSearch(search: String?) {
        viewModel.currencyAdapter.filter.filter(search)
    }

    private fun getBindings(): FragmentCurrencyPickerBinding =
        viewDataBinding as FragmentCurrencyPickerBinding
}