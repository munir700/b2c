package co.yap.modules.transaction_filters.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.databinding.Observable
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.app.YAPApplication
import co.yap.modules.transaction_filters.interfaces.ITransactionFilters
import co.yap.modules.transaction_filters.viewmodels.TransactionFiltersViewModel
import co.yap.networking.transactions.requestdtos.HomeTransactionsRequest
import co.yap.networking.transactions.responsedtos.TransactionFilters
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.BaseState
import com.jaygoo.widget.OnRangeChangedListener
import com.jaygoo.widget.RangeSeekBar
import kotlinx.android.synthetic.main.activity_transaction_filters.*

class TransactionFiltersActivity : BaseBindingActivity<ITransactionFilters.ViewModel>(),
    ITransactionFilters.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_transaction_filters

    override val viewModel: ITransactionFilters.ViewModel
        get() = ViewModelProviders.of(this).get(TransactionFiltersViewModel::class.java)


    companion object {
        const val INTENT_FILTER_REQUEST = 1111
        const val KEY_FILTER_IN_TRANSACTION = "incomingTransaction"
        const val KEY_FILTER_OUT_TRANSACTION = "outgoingTransaction"
        const val KEY_FILTER_START_AMOUNT = "startRange"
        const val KEY_FILTER_END_AMOUNT = "endRange"
        fun newIntent(context: Context): Intent {
            return Intent(context, TransactionFiltersActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setObservers()
    }

    private fun initViews() {
        YAPApplication.homeTransactionsRequest.creditSearch?.let {
            cbInTransFilter.isChecked = it
        }
        YAPApplication.homeTransactionsRequest.debitSearch?.let {
            cbOutTransFilter.isChecked = it
        }
    }

    private fun setObservers() {
        viewModel.clickEvent.observe(this, clickEventObserver)
        viewModel.transactionFilters.observe(this, searchFilterAmountObserver)
        if (viewModel.state is BaseState) {
            (viewModel.state as BaseState).addOnPropertyChangedCallback(stateObserver)
        }
    }

    private fun setRangeSeekBar(transactionFilters: TransactionFilters) {
        rsbAmount?.setRange(
            transactionFilters.minAmount.toFloat(),
            transactionFilters.maxAmount.toFloat()
        )

        rsbAmount?.setProgress(
            transactionFilters.maxAmount.toFloat(),
            transactionFilters.maxAmount.toFloat()
        )
        if (YAPApplication.homeTransactionsRequest.minAmount != null && YAPApplication.homeTransactionsRequest.minAmount != 0.00) {
            rsbAmount?.setProgress(
                YAPApplication.homeTransactionsRequest.minAmount!!.toFloat(),
                transactionFilters.maxAmount.toFloat()
            )
        }

        viewModel.updateRangeValue(rsbAmount)
        rsbAmount.setOnRangeChangedListener(object : OnRangeChangedListener {
            override fun onStartTrackingTouch(view: RangeSeekBar?, isLeft: Boolean) {}

            override fun onRangeChanged(
                rangeSeekbar: RangeSeekBar?,
                leftValue: Float,
                rightValue: Float,
                isFromUser: Boolean
            ) {
                viewModel.updateRangeValue(rangeSeekbar!!)
            }

            override fun onStopTrackingTouch(view: RangeSeekBar?, isLeft: Boolean) {}

        })
    }

    private val clickEventObserver = Observer<Int> {
        when (it) {
            R.id.tvClearFilters -> {
                resetAllFilters()
            }
            R.id.btnApplyFilters -> setFilterValues()
            R.id.IvClose -> {
                YAPApplication.homeTransactionsRequest.hasFilterStateChanged = false
                finish()
            }
        }
    }
    private val searchFilterAmountObserver = Observer<TransactionFilters> {
        if (it != null) {
            setRangeSeekBar(it)
            initViews()
        }
    }

    private val stateObserver = object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
            if (propertyId == BR.error && viewModel.state.error.isNotBlank()) {
                finish()
            }
        }

    }

    private fun resetAllFilters() {
        YAPApplication.clearFilters()
        finish()
    }

    private fun setFilterValues() {
        var count = 0
        if (cbOutTransFilter.isChecked) count++
        if (cbInTransFilter.isChecked) count++
        if (rsbAmount.leftSeekBar.progress != viewModel.transactionFilters.value?.minAmount?.toFloat()!!) count++
        YAPApplication.homeTransactionsRequest = HomeTransactionsRequest(
            0, YAPApplication.pageSize,
            rsbAmount.minProgress.toDouble(), rsbAmount.leftSeekBar.progress.toDouble(),
            cbInTransFilter.isChecked, cbOutTransFilter.isChecked,
            count,
            true,
            hasFiltersStateChanged()
        )
        setResult(INTENT_FILTER_REQUEST)
        finish()
    }

    private fun hasFiltersStateChanged(): Boolean {
        return when {
            YAPApplication.homeTransactionsRequest.creditSearch!! != cbInTransFilter.isChecked -> true
            YAPApplication.homeTransactionsRequest.debitSearch!! != cbOutTransFilter.isChecked -> true
            YAPApplication.homeTransactionsRequest.maxAmount!! != rsbAmount.leftSeekBar.progress.toDouble() -> true
            else -> false
        }
    }

    override fun onBackPressed() {
        YAPApplication.homeTransactionsRequest.hasFilterStateChanged = false
        super.onBackPressed()
    }
}