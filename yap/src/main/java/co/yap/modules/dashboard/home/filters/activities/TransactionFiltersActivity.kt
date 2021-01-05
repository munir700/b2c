package co.yap.modules.dashboard.home.filters.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.CompoundButton
import androidx.databinding.Observable
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.app.YAPApplication
import co.yap.modules.dashboard.home.filters.interfaces.ITransactionFilters
import co.yap.modules.dashboard.home.filters.models.TransactionFilters
import co.yap.modules.dashboard.home.filters.viewmodels.TransactionFiltersViewModel
import co.yap.translation.Strings
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.BaseState
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.extentions.isNetworkAvailable
import com.google.android.material.chip.Chip
import com.jaygoo.widget.OnRangeChangedListener
import com.jaygoo.widget.RangeSeekBar
import kotlinx.android.synthetic.main.activity_transaction_filters.*
import kotlinx.android.synthetic.main.content_fragment_yap_home.view.*

class TransactionFiltersActivity : BaseBindingActivity<ITransactionFilters.ViewModel>(),
    ITransactionFilters.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_transaction_filters

    override val viewModel: ITransactionFilters.ViewModel
        get() = ViewModelProviders.of(this).get(TransactionFiltersViewModel::class.java)

    companion object {
        const val KEY_FILTER_TXN_FILTERS = "txnFilters"
        fun newIntent(
            context: Context,
            txnFilters: TransactionFilters
        ): Intent {
            val intent = Intent(context, TransactionFiltersActivity::class.java)
            intent.putExtra(KEY_FILTER_TXN_FILTERS, txnFilters)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setObservers()
        intent?.let {
            viewModel.txnFilters.value?.catagories?.clear()
            if (it.hasExtra(KEY_FILTER_TXN_FILTERS)) {
                viewModel.txnFilters.value = it.getParcelableExtra(KEY_FILTER_TXN_FILTERS)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.state.hasInternet.set(isNetworkAvailable())
    }

    private fun setObservers() {
        viewModel.clickEvent.observe(this, clickEventObserver)
        viewModel.transactionFilters.observe(this, searchFilterAmountObserver)
        if (viewModel.state is BaseState) {
            (viewModel.state as BaseState).addOnPropertyChangedCallback(stateObserver)
        }
        setChipListener()
    }

    private fun setChipListener() {
        for (index in 0 until chipGroup.childCount) {
            val chip:Chip = chipGroup.getChildAt(index) as Chip
            chip.setOnCheckedChangeListener{view, isChecked ->
                if (isChecked && !viewModel.txnFilters.value?.catagories?.contains(view.text.toString())!!){
                    viewModel.txnFilters.value?.catagories?.add(view.text.toString())
                }else{
                    viewModel.txnFilters.value?.catagories?.remove(view.text.toString())
                }
            }
        }
    }

    private fun initViews() {
        viewModel.txnFilters.value?.let {
            cbInTransFilter.isChecked = it.incomingTxn ?: false
            cbOutTransFilter.isChecked = it.outgoingTxn ?: false
            cbPenTransFilter.isChecked =  it.pendingTxn ?: false
            setCheckedCategories(it)
        }
    }

    private fun setCheckedCategories(it: TransactionFilters) {
        for (index in 0 until chipGroup.childCount){
            val chip:Chip = chipGroup.getChildAt(index) as Chip
           chip.isChecked = it.catagories?.contains(chip.text.toString()) ?: false
        }
    }

    private fun setRangeSeekBar(transactionFilters: co.yap.networking.transactions.responsedtos.TransactionFilters?) {
        try {
            transactionFilters?.let {
                rsbAmount?.setRange(
                    it.minAmount?.toFloat() ?: 0f,
                    it.maxAmount?.toFloat() ?: 1f
                )
            }
            viewModel.txnFilters.value?.let {
                if (it.amountEndRange != null && it.amountEndRange != -1.0 && it.amountEndRange != transactionFilters?.maxAmount) {
                    rsbAmount?.setProgress(
                        it.amountEndRange?.toFloat() ?: 1f,
                        it.amountEndRange?.toFloat() ?: 1f
                    )
                } else {
                    transactionFilters?.let { searchAmt ->
                        rsbAmount?.setProgress(
                            searchAmt.maxAmount?.toFloat() ?: 1f,
                            searchAmt.maxAmount?.toFloat() ?: 1f
                        )
                    }
                }
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
            R.id.btnApplyFilters -> if (isNetworkAvailable()) setIntentAction() else showToast(
                getString(
                    Strings.common_display_text_error_no_internet
                )
            )

        }
    }

    private val searchFilterAmountObserver =
        Observer<co.yap.networking.transactions.responsedtos.TransactionFilters> {
            if (it != null) {
                initViews()
                setRangeSeekBar(it)
            }
        }

    //Observer used to check if something went wrong with api then close the activity
    private val stateObserver = object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
            if (propertyId == BR.error && viewModel.state.error.isNotBlank()) {
                //showToast(viewModel.state.error)
                //finish()
            }
        }
    }

    private fun resetAllFilters() {
        val request = TransactionFilters(
            null,
            null, false, pendingTxn = false,outgoingTxn = false,catagories = arrayListOf()
        )
        val intent = Intent()
        intent.putExtra("txnRequest", request)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    private fun setIntentAction() {
        var appliedFilter = 0
        if (cbInTransFilter.isChecked) appliedFilter++
        if (cbOutTransFilter.isChecked) appliedFilter++
        if (cbPenTransFilter.isChecked) appliedFilter++
        if (viewModel.txnFilters.value?.catagories?.size ?: 0 >= 1) appliedFilter = viewModel.txnFilters.value?.catagories?.size ?: appliedFilter
        viewModel.txnFilters.value?.amountEndRange?.let {
            if (rsbAmount.leftSeekBar.progress != viewModel.transactionFilters.value?.maxAmount?.toFloat() ) appliedFilter++
            setIntentRequest(appliedFilter)
        } ?: setIntentRequest(appliedFilter + 1)
    }

    private fun setIntentRequest(appliedFilter: Int) {
        val request = TransactionFilters(
            amountStartRange = Utils.getTwoDecimalPlaces(rsbAmount.minProgress.toDouble()),
            amountEndRange = Utils.getTwoDecimalPlaces(rsbAmount.leftSeekBar.progress.toDouble()),
            incomingTxn = cbInTransFilter.isChecked,
            outgoingTxn = cbOutTransFilter.isChecked,
            catagories = viewModel.txnFilters.value?.catagories,
            totalAppliedFilter = appliedFilter
        )
        val intent = Intent()
        intent.putExtra("txnRequest", request)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    override fun onDestroy() {
        viewModel.clickEvent.removeObservers(this)
        super.onDestroy()
    }

    override fun onBackPressed() {
        YAPApplication.hasFilterStateChanged = false
        super.onBackPressed()
    }

    override fun onToolBarClick(id: Int) {
        when (id) {
            R.id.ivLeftIcon -> {
                finish()
            }
        }
    }
}