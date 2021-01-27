package co.yap.modules.dashboard.transaction.viewmodels

import android.app.Application
import androidx.databinding.ObservableField
import co.yap.R
import co.yap.modules.dashboard.transaction.interfaces.ITransactionDetails
import co.yap.modules.dashboard.transaction.states.TransactionDetailsState
import co.yap.networking.transactions.responsedtos.transaction.Transaction
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.enums.TransactionProductCode
import co.yap.yapcore.helpers.DateUtils.FORMAT_LONG_OUTPUT
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.extentions.*
import co.yap.yapcore.managers.SessionManager


class TransactionDetailsViewModel(application: Application) :
    BaseViewModel<ITransactionDetails.State>(application), ITransactionDetails.ViewModel {

    override val state: TransactionDetailsState = TransactionDetailsState()
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override var transaction: ObservableField<Transaction> = ObservableField()
    var spentLabelText: ObservableField<String> = ObservableField()

    override fun onCreate() {
        super.onCreate()
        setStatesData()
    }

    override fun handlePressOnEditNoteClickEvent(id: Int) {
        clickEvent.postValue(id)
        var abc = transaction.get().getSpentLabelText()
    }

    private fun setStatesData() {
        transaction.get()?.let { transaction ->
            setToolbarTitle()
            setTransactionNoteDate()
            state.txnNoteValue.set(transaction.transactionNote)
            setSenderOrReceiver(transaction)
            state.categoryTitle.set(transaction.getCategoryTitle())
            state.categoryIcon.set(if (transaction.getCategoryIcon() == -1) R.drawable.ic_other else transaction.getCategoryIcon())
            state.exchangeRate?.set(getExchangeRate(transaction))
        }
        spentLabelText.set(this.transaction.get().getSpentLabelText())


    }

    private fun setToolbarTitle() {
        state.toolbarTitle = transaction.get().getFormattedTime(FORMAT_LONG_OUTPUT)
    }

    private fun setTransactionNoteDate() {
        if (transaction.get().getTransactionNoteDate(FORMAT_LONG_OUTPUT).isEmpty()) {
            state.transactionNoteDate =
                state.editNotePrefixText + transaction.get()?.transactionNoteDate
        } else {
            state.transactionNoteDate =
                state.editNotePrefixText + transaction.get()
                    .getTransactionNoteDate(FORMAT_LONG_OUTPUT)
        }
    }

    private fun setSenderOrReceiver(transaction: Transaction) {
        when (transaction.productCode ?: "") {
            TransactionProductCode.Y2Y_TRANSFER.pCode, TransactionProductCode.UAEFTS.pCode, TransactionProductCode.DOMESTIC.pCode, TransactionProductCode.RMT.pCode, TransactionProductCode.SWIFT.pCode, TransactionProductCode.CASH_PAYOUT.pCode -> {
                state.isTransferTxn.set(true)
            }
        }
    }

    fun getExchangeRate(transaction: Transaction): Double? {
//        var fxRate: Double? = transaction?.settlementAmount?.let {
//            transaction?.cardHolderBillingAmount?.div(
//                it
//            )
//        }
        var fxRate: Double? =
            transaction.settlementAmount?.let { transaction.cardHolderBillingAmount?.div(it) }


        if (transaction.settlementAmount?.let {
                transaction?.cardHolderBillingAmount?.compareTo(
                    it
                )
            } == -1) {

            fxRate = getDecimalFormatUpTo(
                selectedCurrencyDecimal = Utils.getConfiguredDecimalsDashboard(
                    SessionManager.getDefaultCurrency()
                ) ?: getDecimalFromValue("6"),
                amount = fxRate.toString(),
                withComma = true
            ).toDouble()

            // cardHolderBillingAmount is smaller than settlementAmount
            //   Please round to the 6 decimal if cardHolderBillingAmount is smaller than settlementAmount
        } else {
            //   and to the 3rd decimal

            fxRate = getDecimalFormatUpTo(
                selectedCurrencyDecimal = Utils.getConfiguredDecimalsDashboard(
                    SessionManager.getDefaultCurrency()
                ) ?: getDecimalFromValue("3"),
                amount = fxRate.toString(),
                withComma = true
            ).toDouble()
        }



        return fxRate

    }
}