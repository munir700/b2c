package co.yap.modules.dashboard.transaction.viewmodels

import android.app.Application
import androidx.databinding.ObservableField
import co.yap.R
import co.yap.modules.dashboard.transaction.interfaces.ITransactionDetails
import co.yap.modules.dashboard.transaction.states.TransactionDetailsState
import co.yap.networking.transactions.responsedtos.transaction.Content
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.enums.Transaction
import co.yap.yapcore.enums.TransactionProductCode
import co.yap.yapcore.enums.TxnType
import co.yap.yapcore.helpers.DateUtils.FORMAT_LONG_INPUT
import co.yap.yapcore.helpers.DateUtils.FORMAT_LONG_OUTPUT
import co.yap.yapcore.helpers.DateUtils.datetoString
import co.yap.yapcore.helpers.DateUtils.stringToDate


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
            setToolbarTitle(transaction.creationDate ?: "")
            state.txnNoteValue.set(transaction.transactionNote)
            setSenderOrReceiver(transaction)
            state.categoryTitle.set(getCategoryTitle())
            state.categoryIcon.set(getCategoryIcon())
            //spentVisibility()
        }
    }

    private fun setToolbarTitle(creationDate: String) {
        try {
            val date =
                stringToDate(creationDate, FORMAT_LONG_INPUT)
            state.toolBarTitle = datetoString(date, FORMAT_LONG_OUTPUT)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setSenderOrReceiver(transaction: Content) {
        when (transaction.productCode ?: "") {
                TransactionProductCode.Y2Y_TRANSFER.pCode -> {
                    state.isYtoYTransfer.set(true)
            }
        }
    }

    private fun getCategoryTitle(): String {
        transaction.get()?.productCode?.let { productCode ->
            if (Transaction.isFee(productCode)) {
                return "Fee"
            }
            return (when (productCode) {
                TransactionProductCode.Y2Y_TRANSFER.pCode -> if (transaction.get()?.txnType == TxnType.DEBIT.type) "Outgoing Transfer" else "Incoming Transfer"
                TransactionProductCode.TOP_UP_VIA_CARD.pCode -> "Incoming Transfer"
                TransactionProductCode.TOP_UP_SUPPLEMENTARY_CARD.pCode, TransactionProductCode.WITHDRAW_SUPPLEMENTARY_CARD.pCode -> ""
                TransactionProductCode.UAEFTS.pCode, TransactionProductCode.DOMESTIC.pCode, TransactionProductCode.SWIFT.pCode, TransactionProductCode.RMT.pCode, TransactionProductCode.CASH_PAYOUT.pCode -> {
                    "Outgoing Transfer"
                }
                TransactionProductCode.CARD_REORDER.pCode -> "Fee"
                TransactionProductCode.INWARD_REMITTANCE.pCode, TransactionProductCode.LOCAL_INWARD_TRANSFER.pCode -> "Incoming Funds"
                TransactionProductCode.ATM_WITHDRAWL.pCode, TransactionProductCode.POS_PURCHASE.pCode, TransactionProductCode.MASTER_CARD_ATM_WITHDRAWAL.pCode -> {
                    "Cash"
                }
                else -> ""
            })
        } ?: return ""
    }

    private fun getCategoryIcon(): Int {
        transaction.get()?.productCode?.let { productCode ->
            if (Transaction.isFee(productCode)) {
                return R.drawable.ic_expense
            }
            return (when (productCode) {
                TransactionProductCode.Y2Y_TRANSFER.pCode -> R.drawable.ic_send_money
                TransactionProductCode.TOP_UP_SUPPLEMENTARY_CARD.pCode, TransactionProductCode.WITHDRAW_SUPPLEMENTARY_CARD.pCode -> 0
                TransactionProductCode.UAEFTS.pCode, TransactionProductCode.DOMESTIC.pCode, TransactionProductCode.SWIFT.pCode, TransactionProductCode.RMT.pCode, TransactionProductCode.CASH_PAYOUT.pCode -> {
                    R.drawable.ic_send_money
                }
                TransactionProductCode.CARD_REORDER.pCode -> R.drawable.ic_expense
                TransactionProductCode.ATM_WITHDRAWL.pCode, TransactionProductCode.MASTER_CARD_ATM_WITHDRAWAL.pCode, TransactionProductCode.CASH_DEPOSIT_AT_RAK.pCode, TransactionProductCode.CHEQUE_DEPOSIT_AT_RAK.pCode, TransactionProductCode.INWARD_REMITTANCE.pCode, TransactionProductCode.LOCAL_INWARD_TRANSFER.pCode, TransactionProductCode.TOP_UP_VIA_CARD.pCode -> {
                    R.drawable.ic_cash
                }
                TransactionProductCode.POS_PURCHASE.pCode -> getMerchantCategoryIcon()

                else -> 0
            })
        } ?: return 0
    }

    private fun getMerchantCategoryIcon(): Int {
        return (when {
            transaction.get()?.merchantCategoryName.equals(
                "shopping",
                true
            ) -> R.drawable.ic_shopping_no_bg
            transaction.get()?.merchantCategoryName.equals(
                "education",
                true
            ) -> R.drawable.ic_education_no_bg
            transaction.get()?.merchantCategoryName.equals(
                "utilities",
                true
            ) -> R.drawable.ic_utilities_no_bg
            transaction.get()?.merchantCategoryName.equals(
                "healthAndBeauty",
                true
            ) -> R.drawable.ic_health_and_beauty_no_bg
            transaction.get()?.merchantCategoryName.equals(
                "Insurance",
                true
            ) -> R.drawable.ic_insurance_no_bg
            else -> R.drawable.ic_other_no_bg
        })
    }
}