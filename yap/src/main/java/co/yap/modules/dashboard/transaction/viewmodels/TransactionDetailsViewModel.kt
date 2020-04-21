package co.yap.modules.dashboard.transaction.viewmodels

import android.app.Application
import androidx.databinding.ObservableField
import co.yap.modules.dashboard.transaction.interfaces.ITransactionDetails
import co.yap.modules.dashboard.transaction.states.TransactionDetailsState
import co.yap.networking.transactions.responsedtos.transaction.Content
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.enums.TransactionProductCode
import co.yap.yapcore.helpers.DateUtils.FORMAT_LONG_INPUT
import co.yap.yapcore.helpers.DateUtils.FORMAT_LONG_OUTPUT
import co.yap.yapcore.helpers.DateUtils.reformatStringDate
import co.yap.yapcore.helpers.DateUtils.stringToDate
import co.yap.yapcore.helpers.extentions.getCategoryIcon
import co.yap.yapcore.helpers.extentions.getCategoryTitle


class TransactionDetailsViewModel(application: Application) :
    BaseViewModel<ITransactionDetails.State>(application), ITransactionDetails.ViewModel {

    override val state: TransactionDetailsState = TransactionDetailsState()
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override var transaction: ObservableField<Content> = ObservableField()
    override fun onCreate() {
        super.onCreate()
        setStatesData()
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

    private fun setStatesData() {
        transaction.get()?.let { transaction ->
            setToolbarTitle(transaction.updatedDate ?: "")
            state.txnNoteValue.set(transaction.transactionNote)
            setSenderOrReceiver(transaction)
            state.categoryTitle.set(transaction.getCategoryTitle())
            state.categoryIcon.set(transaction.getCategoryIcon())
        }
    }

    private fun setToolbarTitle(creationDate: String) {
        try {
            val date =
                stringToDate(creationDate, FORMAT_LONG_INPUT)
            state.toolBarTitle = reformatStringDate(
                creationDate, FORMAT_LONG_INPUT,
                FORMAT_LONG_OUTPUT
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setSenderOrReceiver(transaction: Content) {
        when (transaction.productCode ?: "") {
            TransactionProductCode.Y2Y_TRANSFER.pCode, TransactionProductCode.UAEFTS.pCode, TransactionProductCode.DOMESTIC.pCode, TransactionProductCode.RMT.pCode, TransactionProductCode.SWIFT.pCode, TransactionProductCode.CASH_PAYOUT.pCode -> {
                state.isTransferTxn.set(true)
            }
        }
    }

}