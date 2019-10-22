package co.yap.modules.transaction_filters.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.transaction_filters.interfaces.ITransactionFilters
import co.yap.modules.transaction_filters.viewmodels.TransactionFiltersViewModel
import co.yap.yapcore.BaseBindingActivity
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
        setRangeSeekBar()
        setObservers()
    }

    private fun setObservers() {
        viewModel.clickEvent.observe(this, clickEventObserver)
    }

    private fun setRangeSeekBar() {
        //TODO: set left value and right value from backend provided values
        rsbAmount?.setProgress(0f, 20000f)
        viewModel.updateRangeValue(rsbAmount)
        rsbAmount.setOnRangeChangedListener(object : OnRangeChangedListener {
            override fun onStartTrackingTouch(view: RangeSeekBar?, isLeft: Boolean) {

            }

            override fun onRangeChanged(
                rangeSeekbar: RangeSeekBar?,
                leftValue: Float,
                rightValue: Float,
                isFromUser: Boolean
            ) {
                viewModel.updateRangeValue(rangeSeekbar!!)
            }

            override fun onStopTrackingTouch(view: RangeSeekBar?, isLeft: Boolean) {

            }

        })
    }

    private val clickEventObserver = Observer<Int> {
        when (it) {
            R.id.tvClearFilters -> {
                resetAllFilters()
            }
            R.id.btnApplyFilters -> setFilterValues()
            R.id.IvClose -> {
                showToast("corss clicked")
            }
        }
    }

    private fun resetAllFilters() {
        rsbAmount?.setProgress(0f, 20000f)
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
        setResult(INTENT_FILTER_REQUEST,data)
        finish()
    }
}