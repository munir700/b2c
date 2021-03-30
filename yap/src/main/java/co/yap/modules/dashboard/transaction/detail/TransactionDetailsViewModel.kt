package co.yap.modules.dashboard.transaction.detail

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.R
import co.yap.modules.dashboard.transaction.receipt.adapter.TransactionReceiptAdapter
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.transactions.TransactionsRepository
import co.yap.networking.transactions.responsedtos.ReceiptModel
import co.yap.networking.transactions.responsedtos.transaction.Transaction
import co.yap.translation.Strings
import co.yap.widgets.bottomsheet.BottomSheetItem
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.enums.*
import co.yap.yapcore.helpers.DateUtils.FORMAT_LONG_OUTPUT
import co.yap.yapcore.helpers.extentions.*


class TransactionDetailsViewModel(application: Application) :
    BaseViewModel<ITransactionDetails.State>(application), ITransactionDetails.ViewModel {

    override val state: TransactionDetailsState =
        TransactionDetailsState()
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override var transaction: ObservableField<Transaction> = ObservableField()
    override var adapter: TransactionReceiptAdapter =
        TransactionReceiptAdapter(
            mutableListOf()
        )
    override var responseReciept: MutableLiveData<ArrayList<String>> = MutableLiveData()
    val repository: TransactionsRepository = TransactionsRepository

    var spentLabelText: ObservableField<String> = ObservableField()

    override fun onCreate() {
        super.onCreate()
        setStatesData()
    }

    override fun getReceiptTitle(list: List<ReceiptModel>): String {
        return when {
            list.isNullOrEmpty() -> getString(Strings.screen_transaction_details_receipt_label)
            list.size == 1 ->
                getString(Strings.screen_transaction_details_single_added_receipt_label).format(
                    adapter.getDataList().size
                )
            list.size > 1 -> getString(Strings.screen_transaction_details_added_receipt_label).format(
                adapter.getDataList().size)
            else -> getString(Strings.screen_transaction_details_receipt_label)
        }

    }


    override fun getReceiptItems(receiptLis: List<String>): List<ReceiptModel> {
        val list: MutableList<ReceiptModel> = arrayListOf()
        for (i in 0..receiptLis.size.minus(1)) {
            list.add(ReceiptModel(receiptItemName(i), receiptLis[i]))
        }
        return list
    }

    override fun receiptItemName(index: Int): String {
        return "Receipt ${index.plus(1)}"
    }

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

    override fun deleteReceipt(position: Int) {
        adapter.removeItemAt(position)
    }

    override fun getAllReceipts() {
        launch {
            state.loading = true
            when (val response = repository.getAllTransactionReceipts(
                transactionId = transaction.get()?.transactionId ?: ""
            )) {
                is RetroApiResponse.Success -> {
                    responseReciept.value = response.data.trxnReceiptList as ArrayList<String>?
                    state.loading = false

                }
                is RetroApiResponse.Error -> {
                    state.loading = false
                    state.toast = response.error.message
                }
            }
        }
    }

    override fun handlePressOnEditNoteClickEvent(id: Int) {
        clickEvent.postValue(id)
    }

    private fun setStatesData() {
        transaction.get()?.let { transaction ->
            if (isShowReceiptSection(transaction)) getAllReceipts()

            setToolbarTitle()
            setTransactionNoteDate()
            val note =
                if (transaction.txnType == TxnType.DEBIT.type) transaction.transactionNote.decodeToUTF8() else transaction.receiverTransactionNote.decodeToUTF8()
            state.txnNoteValue.set(note)
            setSenderOrReceiver(transaction)
            state.receiptVisibility.set(isShowReceiptSection(transaction))
            state.categoryTitle.set(getTransferCategoryTitle(transaction))
            state.categoryIcon.set(getTransferCategoryIcon(transaction))
            if (transaction.isNonAEDTransaction()) {
                state.exchangeRate?.set(getExchangeRate(transaction))
            } else {
                state.exchangeRate?.set(null)
            }
        }
        spentLabelText.set(this.transaction.get().getSpentLabelText())
    }

    override fun isShowReceiptSection(transaction: Transaction): Boolean {
        return when (transaction.productCode) {
            TransactionProductCode.ATM_DEPOSIT.pCode, TransactionProductCode.ATM_WITHDRAWL.pCode, TransactionProductCode.POS_PURCHASE.pCode -> true
            else -> false
        }
    }

    private fun setToolbarTitle() {
        state.toolbarTitle = transaction.get().getFormattedTime(FORMAT_LONG_OUTPUT)
    }

    private fun setTransactionNoteDate() {
        if (transaction.get().getTransactionNoteDate(FORMAT_LONG_OUTPUT).isEmpty()) {
            state.transactionNoteDate =
                state.editNotePrefixText + if (transaction.get()?.txnType == TxnType.DEBIT.type) transaction.get()?.transactionNoteDate else transaction.get()?.receiverTransactionNoteDate
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

    override fun getTransferType(transaction: Transaction): String {
        return when {
            transaction.isTransactionRejected() -> "Transfer Rejected"
            transaction.isTransactionInProgress() -> "Transfer Pending"
            TransactionProductCode.Y2Y_TRANSFER.pCode == transaction.productCode -> "YTY transfer"
            else -> transaction.getTransferType()
        }
    }

    override fun getLocation(transaction: Transaction?): String {
        return when (transaction?.productCode) {
            TransactionProductCode.FUND_LOAD.pCode -> transaction.otherBankName ?: ""
            else -> transaction?.cardAcceptorLocation ?: ""
        }
    }

    override fun getTransferCategoryTitle(transaction: Transaction?): String {
        transaction?.let {
            transaction.productCode?.let { productCode ->
                if (TransactionProductType.IS_TRANSACTION_FEE == transaction.getProductType()) {
                    return "Fee"
                }
                return (when (productCode) {
                    TransactionProductCode.Y2Y_TRANSFER.pCode -> if (transaction.txnType == TxnType.DEBIT.type) "Outgoing Transfer" else "Incoming Transfer"
                    TransactionProductCode.TOP_UP_VIA_CARD.pCode, TransactionProductCode.CASH_DEPOSIT_AT_RAK.pCode, TransactionProductCode.CHEQUE_DEPOSIT_AT_RAK.pCode, TransactionProductCode.INWARD_REMITTANCE.pCode, TransactionProductCode.LOCAL_INWARD_TRANSFER.pCode -> "Incoming Transfer"
                    TransactionProductCode.TOP_UP_SUPPLEMENTARY_CARD.pCode, TransactionProductCode.WITHDRAW_SUPPLEMENTARY_CARD.pCode -> ""
                    TransactionProductCode.UAEFTS.pCode, TransactionProductCode.DOMESTIC.pCode, TransactionProductCode.SWIFT.pCode, TransactionProductCode.RMT.pCode, TransactionProductCode.CASH_PAYOUT.pCode -> {
                        "Outgoing Transfer"
                    }
                    TransactionProductCode.CARD_REORDER.pCode -> "Fee"
                    TransactionProductCode.FUND_LOAD.pCode -> "Incoming Funds"
                    TransactionProductCode.POS_PURCHASE.pCode -> transaction.merchantCategoryName
                        ?: ""
                    TransactionProductCode.ATM_DEPOSIT.pCode -> "Cash deposit"
                    TransactionProductCode.ATM_WITHDRAWL.pCode, TransactionProductCode.MASTER_CARD_ATM_WITHDRAWAL.pCode -> {
                        if (transaction.category.equals(
                                "REVERSAL",
                                true
                            )
                        ) "Reversal" else "Cash withdraw"
                    }
                    else -> ""
                })
            } ?: return ""
        } ?: return ""
    }

    override fun getTransferCategoryIcon(transaction: Transaction?): Int {
        transaction?.let {
            if (transaction.getProductType() == TransactionProductType.IS_TRANSACTION_FEE) {
                return R.drawable.ic_expense
            }
            return (when (transaction.productCode) {
                TransactionProductCode.Y2Y_TRANSFER.pCode -> R.drawable.ic_send_money
                TransactionProductCode.TOP_UP_SUPPLEMENTARY_CARD.pCode, TransactionProductCode.WITHDRAW_SUPPLEMENTARY_CARD.pCode -> 0
                TransactionProductCode.UAEFTS.pCode, TransactionProductCode.DOMESTIC.pCode, TransactionProductCode.SWIFT.pCode, TransactionProductCode.RMT.pCode, TransactionProductCode.CASH_PAYOUT.pCode -> {
                    R.drawable.ic_send_money
                }
                TransactionProductCode.CARD_REORDER.pCode -> R.drawable.ic_expense
                TransactionProductCode.ATM_WITHDRAWL.pCode, TransactionProductCode.MASTER_CARD_ATM_WITHDRAWAL.pCode, TransactionProductCode.CASH_DEPOSIT_AT_RAK.pCode, TransactionProductCode.CHEQUE_DEPOSIT_AT_RAK.pCode, TransactionProductCode.INWARD_REMITTANCE.pCode, TransactionProductCode.LOCAL_INWARD_TRANSFER.pCode, TransactionProductCode.TOP_UP_VIA_CARD.pCode, TransactionProductCode.FUND_LOAD.pCode, TransactionProductCode.ATM_DEPOSIT.pCode -> {
                    R.drawable.ic_cash
                }
                TransactionProductCode.POS_PURCHASE.pCode -> if (transaction.merchantCategoryName.getMerchantCategoryIcon() == -1) R.drawable.ic_other_outgoing else transaction.merchantCategoryName.getMerchantCategoryIcon()

                else -> 0
            })
        } ?: return 0
    }


    override fun getSpentAmount(transaction: Transaction?): Double {
        transaction?.let {
            return when {
                it.status == TransactionStatus.FAILED.name -> 0.00
                it.getProductType() == TransactionProductType.IS_TRANSACTION_FEE && it.productCode != TransactionProductCode.MANUAL_ADJUSTMENT.pCode -> {
                    0.00
                }
                it.productCode == TransactionProductCode.SWIFT.pCode || it.productCode == TransactionProductCode.RMT.pCode -> {
                    (it.settlementAmount ?: 0.00)
                }
                it.isNonAEDTransaction() -> {
                    it.cardHolderBillingAmount ?: 0.00
                }
                else -> it.amount ?: 0.00
            }
        } ?: return 0.00
    }

    override fun getCalculatedTotalAmount(transaction: Transaction?): Double {
        transaction?.let {
            return when {
                it.productCode == TransactionProductCode.RMT.pCode || it.productCode == TransactionProductCode.SWIFT.pCode -> {
                    val totalFee = (it.postedFees ?: 0.00).plus(it.vatAmount ?: 0.0)
                    (it.settlementAmount ?: 0.00).plus(totalFee)
                }
                it.isNonAEDTransaction() -> {
                    (it.cardHolderBillingAmount ?: 0.00).plus(it.markupFees ?: 0.00)
                        .plus(it.vatAmount ?: 0.0)
                }
                else -> if (it.txnType == TxnType.DEBIT.type) it.totalAmount ?: 0.00 else it.amount
                    ?: 0.00
            }
        } ?: return 0.00
    }

    override fun getForeignAmount(transaction: Transaction?): Double {
        transaction?.let {
            return when (transaction.productCode) {
                TransactionProductCode.RMT.pCode, TransactionProductCode.SWIFT.pCode -> {
                    transaction.amount ?: 0.00
                }
                else -> 0.00
            }
        } ?: return 0.00
    }

    override fun getStatusIcon(transaction: Transaction?): Int {
        return if (transaction?.isTransactionInProgress() == true) android.R.color.transparent
        else when (transaction?.productCode) {
            TransactionProductCode.ATM_WITHDRAWL.pCode -> {
                R.drawable.ic_identifier_atm_withdrawl
            }
            TransactionProductCode.ATM_DEPOSIT.pCode -> {
                R.drawable.ic_identifier_atm_deposite
            }

            else -> android.R.color.transparent
        }
    }

    //getString(
    //                Strings.transaction_narration_y2y_transfer_detail
    //            )
    override fun getAddReceiptOptions(): ArrayList<BottomSheetItem> {
        val list = arrayListOf<BottomSheetItem>()
        list.add(
            BottomSheetItem(
                icon = R.drawable.ic_camera,
                title = getString(Strings.screen_update_profile_photo_display_text_open_camera),
                tag = PhotoSelectionType.CAMERA.name
            )
        )
        list.add(
            BottomSheetItem(
                icon = R.drawable.ic_folder,
                title = getString(Strings.screen_transaction_details_display_sheet_text_upload_from_files),
                tag = PhotoSelectionType.GALLERY.name
            )
        )
        return list
    }

    override fun setAdapterList(receiptLis: List<String>) {
        adapter.setList(getReceiptItems(receiptLis))
        state.receiptTitle.set(getReceiptTitle(adapter.getDataList()))
    }

    private fun getExchangeRate(transaction: Transaction): Double? {
        var fxRate: Double? =
            transaction.amount?.let { transaction.cardHolderBillingAmount?.div(it) }

        if (transaction.amount?.let {
                transaction.cardHolderBillingAmount?.compareTo(
                    it
                )
            } == -1) { // cardHolderBillingAmount is smaller than amount
            // round to the 6 decimal if cardHolderBillingAmount is smaller than amount
            fxRate = getDecimalFormatUpTo(
                6,
                amount = fxRate.toString(),
                withComma = true
            ).toDouble()
        } else {
            //   and to the 3rd decimal
            fxRate = getDecimalFormatUpTo(
                3,
                amount = fxRate.toString(),
                withComma = true
            ).toDouble()
        }

        return fxRate
    }

}