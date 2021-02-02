package co.yap.modules.dashboard.transaction.viewmodels

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.R
import co.yap.modules.dashboard.transaction.TransactionReceiptAdapter
import co.yap.modules.dashboard.transaction.interfaces.ITransactionDetails
import co.yap.modules.dashboard.transaction.states.TransactionDetailsState
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.transactions.TransactionsRepository
import co.yap.networking.transactions.responsedtos.ReceiptModel
import co.yap.networking.transactions.responsedtos.transaction.Transaction
import co.yap.networking.transactions.responsedtos.transactionreciept.TransactionReceipt
import co.yap.translation.Strings
import co.yap.widgets.bottomsheet.BottomSheetItem
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.enums.PhotoSelectionType
import co.yap.yapcore.enums.TransactionProductCode
import co.yap.yapcore.helpers.DateUtils.FORMAT_LONG_OUTPUT
import co.yap.yapcore.helpers.extentions.getCategoryIcon
import co.yap.yapcore.helpers.extentions.getCategoryTitle
import co.yap.yapcore.helpers.extentions.getFormattedTime
import co.yap.yapcore.helpers.extentions.getTransactionNoteDate
import java.util.*

class TransactionDetailsViewModel(application: Application) :
    BaseViewModel<ITransactionDetails.State>(application), ITransactionDetails.ViewModel {

    override val state: TransactionDetailsState = TransactionDetailsState()
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override var transaction: ObservableField<Transaction> = ObservableField()
    override var adapter: TransactionReceiptAdapter = TransactionReceiptAdapter(mutableListOf())
    override var responseReciept: MutableLiveData<TransactionReceipt> = MutableLiveData()
    val repository: TransactionsRepository = TransactionsRepository

    override fun onCreate() {
        super.onCreate()
        getAllReceipts()
        setStatesData()
    }

    private fun setReceiptLabel(list: List<ReceiptModel>) {
        state.receiptLabel.set(
            when {
                list.isNullOrEmpty() -> {
                    getString(Strings.screen_transaction_details_receipt_label)
                }
                list.size == 1 -> {
                    getString(Strings.screen_transaction_details_single_added_receipt_label).format(
                        adapter.getDataList().size
                    )
                }
                list.size > 1 -> {
                    getString(Strings.screen_transaction_details_added_receipt_label).format(adapter.getDataList().size)
                }
                else -> getString(Strings.screen_transaction_details_receipt_label)
            }
        )
    }

    private fun getReciptItems(receiptLis: List<String>): List<ReceiptModel> {
        val list: MutableList<ReceiptModel> = arrayListOf()
        for (i in 0..receiptLis.size.minus(1)) {
            list.add(ReceiptModel("Receipt ${i.plus(1)}", receiptLis[i]))
        }
        return list
    }

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

    override fun addNewReceipt(receipt: ReceiptModel) {
        adapter.setItemAt(adapter.getDataList().size, receipt)
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
                    response.data.let { resp ->
                        responseReciept.value = resp.data
                    }
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
            setToolbarTitle()
            setTransactionNoteDate()
            state.txnNoteValue.set(transaction.transactionNote)
            setSenderOrReceiver(transaction)
            setReceiptVisible(transaction)
            state.categoryTitle.set(transaction.getCategoryTitle())
            state.categoryIcon.set(transaction.getCategoryIcon())
        }
    }

    private fun setReceiptVisible(transaction: Transaction) {
        when (transaction.productCode) {
            TransactionProductCode.ATM_DEPOSIT.pCode -> {
                state.receiptVisibility.set(true)
            }
            TransactionProductCode.ATM_WITHDRAWL.pCode -> {
                state.receiptVisibility.set(true)
            }
            TransactionProductCode.POS_PURCHASE.pCode -> {
                state.receiptVisibility.set(true)
            }
            else -> {
                state.receiptVisibility.set(false)
            }
        }

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
        adapter.setList(getReciptItems(receiptLis))
        setReceiptLabel(adapter.getDataList())
    }

}