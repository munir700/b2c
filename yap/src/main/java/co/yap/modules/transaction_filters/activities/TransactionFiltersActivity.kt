package co.yap.modules.transaction_filters.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.databinding.Observable
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.transaction_filters.interfaces.ITransactionFilters
import co.yap.modules.transaction_filters.viewmodels.TransactionFiltersViewModel
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
            transactionFilters.minAmount.toFloat(),
            transactionFilters.maxAmount.toFloat()
        )
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
                finish()
            }
        }
    }
    private val searchFilterAmountObserver = Observer<TransactionFilters> {
        if (it != null) {
            setRangeSeekBar(it)
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
        rsbAmount?.setProgress(
            viewModel.transactionFilters.value?.minAmount?.toFloat()!!,
            viewModel.transactionFilters.value?.maxAmount?.toFloat()!!
        )
        viewModel.updateRangeValue(rsbAmount)
        cbInTransFilter.isChecked = false
        cbOutTransFilter.isChecked = false
    }

    private fun setFilterValues() {
        val data = Intent()
        data.putExtra(KEY_FILTER_IN_TRANSACTION, cbInTransFilter.isChecked)
        data.putExtra(KEY_FILTER_OUT_TRANSACTION, cbOutTransFilter.isChecked)
        data.putExtra(KEY_FILTER_START_AMOUNT, rsbAmount.leftSeekBar.progress.toInt())
        data.putExtra(KEY_FILTER_END_AMOUNT, rsbAmount.rightSeekBar.progress.toInt())

        setResult(INTENT_FILTER_REQUEST, data)
        finish()
    }

}