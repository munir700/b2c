package co.yap.modules.dashboard.transaction.viewmodels

import android.annotation.SuppressLint
import android.app.Application
import co.yap.modules.dashboard.transaction.interfaces.ITransactionDetails
import co.yap.modules.dashboard.transaction.states.TransactionDetailsState
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.helpers.Utils
import java.text.SimpleDateFormat
import java.util.*


class TransactionDetailsViewModel(application: Application) :
    BaseViewModel<ITransactionDetails.State>(application), ITransactionDetails.ViewModel {

    override val state: TransactionDetailsState = TransactionDetailsState()
    override var clickEvent: SingleClickEvent = SingleClickEvent()

    @SuppressLint("SimpleDateFormat")
    override fun onCreate() {
        super.onCreate()
        val dateString = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("MMM dd, YYYY ・ HH:mmaa")
        state.toolBarTitle = dateFormat.format(dateString)
        state.transactionTitle = "Transaction Details"
        state.spentTitle = "Spent"
        state.feeTitle = "Fee"
        state.totalTitle = "Total"
        state.addNoteTitle = "Add a note"
        state.noteValue = "Stay organized by adding transaction notes"
        state.spentAmount = Utils.getFormattedCurrency("1500")
        state.feeAmount = Utils.getFormattedCurrency("0")
        state.totalAmount = Utils.getFormattedCurrency(
            addValues(
                spentAmount = state.spentAmount,
                feeAmount = state.feeAmount
            ).toString()
        )
    }

    override fun handlePressOnBackButton(id: Int) {
        clickEvent.postValue(id)
    }
    override fun handlePressOnEditNoteClickEvent(id: Int) {
       clickEvent.postValue(id)
    }

    override fun handlePressOnShareButton(id: Int) {
        clickEvent.postValue(id)
    }

    private fun addValues(spentAmount: String = "", feeAmount: String = ""): Double {
        if (feeAmount != "") {
            return Utils.getFormattedCurrencyWithoutComma("1500.00").toDouble() + Utils.getFormattedCurrencyWithoutComma(
                "0.00"
            ).toDouble()
        }
        return 0.00

    }
}