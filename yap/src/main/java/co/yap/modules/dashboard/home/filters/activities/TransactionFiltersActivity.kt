package co.yap.modules.dashboard.home.filters.activities

import android.app.Activity
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
import co.yap.modules.dashboard.home.filters.models.TransactionRequest
import co.yap.modules.dashboard.home.filters.viewmodels.TransactionFiltersViewModel
import co.yap.networking.transactions.requestdtos.HomeTransactionsRequest
import co.yap.networking.transactions.responsedtos.TransactionFilters
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.BaseState
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.constants.RequestCodes
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
        const val KEY_FILTER_TXN_TYPE = "txnType"
        const val KEY_FILTER_START_AMOUNT = "startRange"
        const val KEY_FILTER_END_AMOUNT = "endRange"
        fun newIntent(
            context: Context,
            txnType: String?,
            startRange: Double?,
            endRange: Double?
        ): Intent {
            val intent = Intent(context, TransactionFiltersActivity::class.java)
            intent.putExtra(KEY_FILTER_TXN_TYPE, txnType)
            intent.putExtra(KEY_FILTER_START_AMOUNT, startRange)
            intent.putExtra(KEY_FILTER_END_AMOUNT, endRange)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setObservers()
    }

    private fun initViews() {
        if (intent != null) {
            if (intent.hasExtra(KEY_FILTER_TXN_TYPE)) {
                val type: String? = intent.getStringExtra(KEY_FILTER_TXN_TYPE)
                viewModel.state.selectedTxnType.set(type)
                setupCheckboxes(txnType = type)
            }
            if (intent.hasExtra(KEY_FILTER_START_AMOUNT)) {
                val startRange: Double? = intent.getDoubleExtra(KEY_FILTER_START_AMOUNT, -1.0)
                viewModel.state.selectedStartRange.set(startRange)
            }
            if (intent.hasExtra(KEY_FILTER_END_AMOUNT)) {
                val endRange: Double? = intent.getDoubleExtra(KEY_FILTER_END_AMOUNT, -1.0)
                viewModel.state.selectedEndRange.set(endRange)
            }
        }
        // init view with old states
//        if (YAPApplication.homeTransactionsRequest.txnType == null) {
//            when (YAPApplication.isAllChecked) {
//                true -> {
//                    cbInTransFilter.isChecked = true
//                    cbOutTransFilter.isChecked = true
//                }
//                false -> {
//                    cbInTransFilter.isChecked = false
//                    cbOutTransFilter.isChecked = false
//                }
//            }
//        }
//        YAPApplication.homeTransactionsRequest.txnType?.let {
//            when (it) {
//                Constants.MANUAL_CREDIT -> cbInTransFilter.isChecked = true
//                Constants.MANUAL_DEBIT -> cbOutTransFilter.isChecked = true
//            }
//        }
    }

    private fun setupCheckboxes(txnType: String?) {
        if (txnType != null) {
            when (txnType) {
                Constants.MANUAL_CREDIT -> cbInTransFilter.isChecked = true
                Constants.MANUAL_DEBIT -> cbOutTransFilter.isChecked = true
            }
        } else {
            // handle case for null
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

    private fun setRangeSeekBar(transactionFilters: TransactionFilters?) {
        try {
            transactionFilters?.let {
                rsbAmount?.setRange(
                    it.minAmount?.toFloat() ?: 0f,
                    it.maxAmount?.toFloat() ?: 1f
                )
            }
            if (viewModel.state.selectedEndRange.get() != -1.0 && viewModel.state.selectedEndRange.get() != transactionFilters?.maxAmount) {
                rsbAmount?.setProgress(
                    viewModel.state.selectedEndRange.get()?.toFloat() ?: 1f,
                    viewModel.state.selectedEndRange.get()?.toFloat() ?: 1f
                )
            } else {
                transactionFilters?.let {
                    rsbAmount?.setProgress(
                        it.maxAmount?.toFloat() ?: 1f,
                        it.maxAmount?.toFloat() ?: 1f
                    )
                }
            }
//
//            if (YAPApplication.homeTransactionsRequest.amountEndRange != null && YAPApplication.homeTransactionsRequest.amountEndRange != transactionFilters?.maxAmount) {
//                rsbAmount?.setProgress(
//                    YAPApplication.homeTransactionsRequest.amountEndRange!!.toFloat(),
//                    YAPApplication.homeTransactionsRequest.amountEndRange!!.toFloat()
//                )
//            } else {
//                transactionFilters?.let {
//                    rsbAmount?.setProgress(
//                        it.maxAmount?.toFloat() ?: 1f,
//                        it.maxAmount?.toFloat() ?: 1f
//                    )
//                }
                /*rsbAmount?.setProgress(
                    transactionFilters?.maxAmount.toFloat(),
                    transactionFilters?.maxAmount.toFloat()
                )*/
//            }

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
        } catch (ex: Exception) {
            showToast("Max and Min range error")
            ex.printStackTrace()
        }
    }

    private val clickEventObserver = Observer<Int> {
        when (it) {
            R.id.tvClearFilters -> {
                resetAllFilters()
            }
            R.id.btnApplyFilters -> setFilterValues2()
            R.id.IvClose -> {
                YAPApplication.hasFilterStateChanged = false
                finish()
            }
        }
    }
    private val searchFilterAmountObserver = Observer<TransactionFilters> {
        if (it != null) {
            initViews()
            setRangeSeekBar(it)
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
        viewModel.transactionFilters.value?.maxAmount?.toFloat()?.let {
            if (!cbOutTransFilter.isChecked && !cbInTransFilter.isChecked && it == rsbAmount.leftSeekBar.progress) {
                YAPApplication.hasFilterStateChanged =
                    Utils.getTwoDecimalPlaces(rsbAmount.leftSeekBar.progress.toDouble()) != Utils.getTwoDecimalPlaces(
                        it.toDouble()
                    )
                YAPApplication.isAllChecked = false
                YAPApplication.clearFilters()
            } else {
                var count = 0
                if (cbOutTransFilter.isChecked) count++
                if (cbInTransFilter.isChecked) count++
                if (it != rsbAmount.leftSeekBar.progress) count++
                YAPApplication.hasFilterStateChanged = hasFiltersStateChanged()

                YAPApplication.homeTransactionsRequest = HomeTransactionsRequest(
                    0, YAPApplication.pageSize,
                    Utils.getTwoDecimalPlaces(rsbAmount.minProgress.toDouble()),
                    Utils.getTwoDecimalPlaces(rsbAmount.leftSeekBar.progress.toDouble()),
                    getCurrentTxnType(),
                    null,
                    count
                )
                setResult(RequestCodes.REQUEST_TXN_FILTER)
            }
            finish()
        }
    }

    private fun setFilterValues2() {
        viewModel.transactionFilters.value?.maxAmount?.toFloat()?.let {
            if (!cbOutTransFilter.isChecked && !cbInTransFilter.isChecked && it == rsbAmount.leftSeekBar.progress) {
                YAPApplication.hasFilterStateChanged =
                    Utils.getTwoDecimalPlaces(rsbAmount.leftSeekBar.progress.toDouble()) != Utils.getTwoDecimalPlaces(
                        it.toDouble()
                    )
                YAPApplication.isAllChecked = false
                YAPApplication.clearFilters()
            } else {
                var count = 0
                if (cbOutTransFilter.isChecked) count++
                if (cbInTransFilter.isChecked) count++
                if (it != rsbAmount.leftSeekBar.progress) count++
                setIntentAction(count)
            }
            finish()
        }
    }

    private fun setIntentAction(totalAppliedFilter: Int) {
        val request = TransactionRequest(
            number = 0,
            size = YAPApplication.pageSize,
            amountStartRange = Utils.getTwoDecimalPlaces(rsbAmount.minProgress.toDouble()),
            amountEndRange = Utils.getTwoDecimalPlaces(rsbAmount.leftSeekBar.progress.toDouble()),
            txnType = getCurrentTxnType(),
            title = null,
            totalAppliedFilter = totalAppliedFilter
        )
        val intent = Intent()
        intent.putExtra("txnRequest", request)
        intent.putExtra("hasFilterStateChanged", hasFiltersStateChanged())
        setResult(Activity.RESULT_OK, intent)
    }


    private fun hasFiltersStateChanged(): Boolean {
        var isStateChanged: Boolean

        // check if old txnType state is null and new txnType state is null then there is a change
//        if (YAPApplication.homeTransactionsRequest.txnType == null && getCurrentTxnType() != null)
//            return true
        if (viewModel.state.selectedTxnType.get() == null && getCurrentTxnType() != null)
            return true

        // check if old endRange state is null and new selected endRange state is not equal to maxRange then there is a change
        if (viewModel.state.selectedEndRange.get() == -1.0 && Utils.getTwoDecimalPlaces(
                rsbAmount.leftSeekBar.progress.toDouble()
            ) != Utils.getTwoDecimalPlaces(viewModel.transactionFilters.value?.maxAmount!!)
        ) return true

//if (YAPApplication.homeTransactionsRequest.amountEndRange == null && Utils.getTwoDecimalPlaces(
//                rsbAmount.leftSeekBar.progress.toDouble()
//            ) != Utils.getTwoDecimalPlaces(viewModel.transactionFilters.value?.maxAmount!!)
//        ) return true

        // when not null compare old states with new states
        whenNotNull(viewModel.state.selectedTxnType.get()) {
            isStateChanged = it != getCurrentTxnType()
            if (isStateChanged) return true
        }
//        whenNotNull(YAPApplication.homeTransactionsRequest.txnType) {
//            isStateChanged = it != getCurrentTxnType()
//            if (isStateChanged) return true
//        }
//
        whenNotNull(viewModel.state.selectedEndRange.get()) {
            isStateChanged =
                it != Utils.getTwoDecimalPlaces(rsbAmount.leftSeekBar.progress.toDouble())
            if (isStateChanged) return true
        }
//        whenNotNull(YAPApplication.homeTransactionsRequest.amountEndRange) {
//            isStateChanged =
//                it != Utils.getTwoDecimalPlaces(rsbAmount.leftSeekBar.progress.toDouble())
//            if (isStateChanged) return true
//        }

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
        if (!cbOutTransFilter.isChecked && !cbInTransFilter.isChecked) {
            return null
        } else {
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
                    Constants.MANUAL_CREDIT
                }
                cbOutTransFilter.isChecked -> {
                    YAPApplication.isAllChecked = false
                    Constants.MANUAL_DEBIT
                }
                else -> null
            }
        }

    }
}