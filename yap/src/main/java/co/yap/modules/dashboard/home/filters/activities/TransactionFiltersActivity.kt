package co.yap.modules.dashboard.home.filters.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.databinding.Observable
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.app.YAPApplication
import co.yap.modules.dashboard.home.filters.interfaces.ITransactionFilters
import co.yap.modules.dashboard.home.filters.viewmodels.TransactionFiltersViewModel
import co.yap.networking.transactions.requestdtos.HomeTransactionsRequest
import co.yap.networking.transactions.responsedtos.TransactionFilters
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.BaseState
import co.yap.yapcore.helpers.Utils
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
        // init view with old states
        if (YAPApplication.homeTransactionsRequest.txnType == null) {
            when (YAPApplication.isAllChecked) {
                true -> {
                    cbInTransFilter.isChecked = true
                    cbOutTransFilter.isChecked = true
                }
                false -> {
                    cbInTransFilter.isChecked = false
                    cbOutTransFilter.isChecked = false
                }
            }
        }
        YAPApplication.homeTransactionsRequest.txnType?.let {
            when (it) {
                "CREDIT" -> cbInTransFilter.isChecked = true
                "DEBIT" -> cbOutTransFilter.isChecked = true
            }
        }
    }

    override fun onDestroy() {
        viewModel.clickEvent.removeObservers(this)
        super.onDestroy()
    }

    private fun setObservers() {
        viewModel.clickEvent.observe(this, clickEventObserver)
        viewModel.transactionFilters.observe(this, searchFilterAmountObserver)
        if (viewModel.state is BaseState) {
            (viewModel.state as BaseState).addOnPropertyChangedCallback(stateObserver)
        }
    }

    private fun setRangeSeekBar(transactionFilters: TransactionFilters) {
        try {
            rsbAmount?.setRange(
                transactionFilters.minAmount.toFloat(),
                transactionFilters.maxAmount.toFloat()
            )

        if (YAPApplication.homeTransactionsRequest.amountEndRange != null && YAPApplication.homeTransactionsRequest.amountEndRange != transactionFilters.maxAmount) {
            rsbAmount?.setProgress(
                YAPApplication.homeTransactionsRequest.amountEndRange!!.toFloat(),
                YAPApplication.homeTransactionsRequest.amountEndRange!!.toFloat())
        } else {
            rsbAmount?.setProgress(
                transactionFilters.maxAmount.toFloat(),
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
        })} catch (ex: Exception) {
            showToast("Max and Min range error")
            ex.printStackTrace()
        }
    }

    private val clickEventObserver = Observer<Int> {
        when (it) {
            R.id.tvClearFilters -> {
                resetAllFilters()
            }
            R.id.btnApplyFilters -> setFilterValues()
            R.id.IvClose -> {
                YAPApplication.hasFilterStateChanged = false
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

    //Observer used to check if something went wrong with api then close the activity
    private val stateObserver = object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
            if (propertyId == BR.error && viewModel.state.error.isNotBlank()) {
                showToast("Internal Server Error")
                finish()
            }
        }
    }

    private fun resetAllFilters() {
        if (YAPApplication.isAllChecked) {
            YAPApplication.hasFilterStateChanged =
                Utils.getTwoDecimalPlaces(rsbAmount.leftSeekBar.progress.toDouble()) != Utils.getTwoDecimalPlaces(
                    viewModel.transactionFilters.value?.maxAmount!!
                )
        } else {
            YAPApplication.hasFilterStateChanged =
                YAPApplication.homeTransactionsRequest.totalAppliedFilter != 0
        }

        YAPApplication.isAllChecked = false
        YAPApplication.clearFilters()
        finish()
    }

    private fun setFilterValues() {
        if (!cbOutTransFilter.isChecked && !cbInTransFilter.isChecked && rsbAmount.leftSeekBar.progress == viewModel.transactionFilters.value?.maxAmount?.toFloat()!!) {
            YAPApplication.hasFilterStateChanged =
                Utils.getTwoDecimalPlaces(rsbAmount.leftSeekBar.progress.toDouble()) != Utils.getTwoDecimalPlaces(
                    viewModel.transactionFilters.value?.maxAmount!!
                )
            YAPApplication.isAllChecked = false
            YAPApplication.clearFilters()
        } else {
            var count = 0
            if (cbOutTransFilter.isChecked) count++
            if (cbInTransFilter.isChecked) count++
            if (rsbAmount.leftSeekBar.progress != viewModel.transactionFilters.value?.maxAmount?.toFloat()!!) count++
            YAPApplication.hasFilterStateChanged = hasFiltersStateChanged()

            YAPApplication.homeTransactionsRequest = HomeTransactionsRequest(
                0, YAPApplication.pageSize,
                Utils.getTwoDecimalPlaces(rsbAmount.minProgress.toDouble()),
                Utils.getTwoDecimalPlaces(rsbAmount.leftSeekBar.progress.toDouble()),
                getCurrentTxnType(),
                null,
                count
            )
            setResult(INTENT_FILTER_REQUEST)
        }
        finish()
    }

    private fun hasFiltersStateChanged(): Boolean {
        var isStateChanged: Boolean

        // check if old txnType state is null and new txnType state is null then there is a change
        if (YAPApplication.homeTransactionsRequest.txnType == null && getCurrentTxnType() != null)
            return true

        // check if old endRange state is null and new selected endRange state is not equal to maxRange then there is a change
        if (YAPApplication.homeTransactionsRequest.amountEndRange == null && Utils.getTwoDecimalPlaces(
                rsbAmount.leftSeekBar.progress.toDouble()
            ) != Utils.getTwoDecimalPlaces(viewModel.transactionFilters.value?.maxAmount!!)
        ) return true

        // when not null compare old states with new states
        whenNotNull(YAPApplication.homeTransactionsRequest.txnType) {
            isStateChanged = it != getCurrentTxnType()
            if (isStateChanged) return true
        }
        whenNotNull(YAPApplication.homeTransactionsRequest.amountEndRange) {
            isStateChanged =
                it != Utils.getTwoDecimalPlaces(rsbAmount.leftSeekBar.progress.toDouble())
            if (isStateChanged) return true
        }

        return false
    }

    override fun onBackPressed() {
        YAPApplication.hasFilterStateChanged = false
        super.onBackPressed()
    }

    private inline fun <T : Any, R> whenNotNull(input: T?, callback: (T) -> R): R? {
        return input?.let(callback)
    }

    private fun getCurrentTxnType(): String? {
        // case null is used for all transaction
        if (!cbOutTransFilter.isChecked && !cbInTransFilter.isChecked ){
            return null
        }else{
            return when {
                cbInTransFilter.isChecked && cbOutTransFilter.isChecked -> {
                    YAPApplication.isAllChecked = true
                    null
                }
                !cbInTransFilter.isChecked && !cbOutTransFilter.isChecked -> {
                    YAPApplication.isAllChecked = true
                    null
                }
                cbInTransFilter.isChecked -> {
                    YAPApplication.isAllChecked = false
                    "CREDIT"
                }
                cbOutTransFilter.isChecked -> {
                    YAPApplication.isAllChecked = false
                    "DEBIT"
                }
                else -> null
            }
        }

    }
}