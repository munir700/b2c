package co.yap.modules.dashboard.transaction.detail

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.R
import co.yap.modules.dashboard.transaction.detail.adaptor.TransactionDetailItemAdapter
import co.yap.modules.dashboard.transaction.detail.composer.TransactionDetailComposer
import co.yap.modules.dashboard.transaction.receipt.adapter.TransactionReceiptAdapter
import co.yap.networking.models.ApiResponse
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.transactions.TransactionsRepository
import co.yap.networking.transactions.requestdtos.TotalPurchaseRequest
import co.yap.networking.transactions.responsedtos.ReceiptModel
import co.yap.networking.transactions.responsedtos.TotalPurchasesResponse
import co.yap.networking.transactions.responsedtos.transaction.Transaction
import co.yap.networking.transactions.responsedtos.transactionreciept.TransactionReceiptResponse
import co.yap.translation.Strings
import co.yap.widgets.bottomsheet.BottomSheetItem
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.Dispatcher
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.enums.PhotoSelectionType
import co.yap.yapcore.enums.TransactionProductCode
import co.yap.yapcore.enums.TxnType
import co.yap.yapcore.helpers.DateUtils.FORMAT_LONG_OUTPUT
import co.yap.yapcore.helpers.extentions.getFormattedTime
import co.yap.yapcore.helpers.extentions.getTransactionNoteDate
import co.yap.yapcore.helpers.extentions.isTransactionInProgress
import co.yap.yapcore.helpers.extentions.isTransactionRejected
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.util.*


class TransactionDetailsViewModel(application: Application) :
    BaseViewModel<ITransactionDetails.State>(application), ITransactionDetails.ViewModel {
    val repository: TransactionsRepository = TransactionsRepository
    override val state: TransactionDetailsState =
        TransactionDetailsState()
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override var transaction: ObservableField<Transaction> = ObservableField()
    override var adapter: TransactionReceiptAdapter =
        TransactionReceiptAdapter(
            mutableListOf()
        )
    override var transactionAdapter: TransactionDetailItemAdapter =
        TransactionDetailItemAdapter(arrayListOf()
        )
    override var responseReciept: MutableLiveData<ArrayList<String>> = MutableLiveData()
    override var itemsComposer: TransactionDetailComposer = TransactionDetailComposer()

    override fun onCreate() {
        super.onCreate()
        setStatesData()
    }

    override fun composeTransactionDetail(transaction: Transaction) {
        state.transactionData.set(itemsComposer.compose(transaction))
        state.transactionData.get()?.let {
            transactionAdapter.setList(it.transactionItem)
            state.txnNoteValue.set(it.noteValue)
            state.transactionNoteDate = it.noteAddedDate
            state.coverImage.set(it.coverImage)
            it.showTotalPurchase?.let { it1 -> state.showTotalPurchases.set(it1) }
            it.showError?.let { bool -> state.showErrorMessage.set(bool) }
        }
    }

    override fun getTotalPurchaseEndpoint(): TotalPurchaseRequest {
        transaction.get()?.let { data ->
            return when (data.productCode) {
                TransactionProductCode.Y2Y_TRANSFER.pCode -> {
                    TotalPurchaseRequest(txnType = data.txnType ?: "",
                        productCode = data.productCode ?: "",
                        receiverCustomerId = data.customerId2 ?: "")
                }
                TransactionProductCode.SWIFT.pCode, TransactionProductCode.RMT.pCode, TransactionProductCode.UAEFTS.pCode, TransactionProductCode.DOMESTIC.pCode -> {
                    TotalPurchaseRequest(txnType = data.txnType ?: "",
                        productCode = data.productCode ?: "",
                        beneficiaryId = data.beneficiaryId ?: "")
                }
                else -> {
                    TotalPurchaseRequest(txnType = data.txnType ?: "",
                        productCode = data.productCode ?: "")
                }
            }

        }
        return TotalPurchaseRequest(txnType = transaction.get()?.txnType ?: "",
            productCode = transaction.get()?.productCode ?: "")
    }

    override fun requestAllApis() {
        requestReceiptsAndTotalPurchases { totalPurchasesResponse, receiptResponse ->
            launch (Dispatcher.Main){
                when(totalPurchasesResponse){
                    is RetroApiResponse.Success ->{}
                }
                when(receiptResponse){
                    is RetroApiResponse.Success ->{
                        responseReciept.value = receiptResponse.data.trxnReceiptList as ArrayList<String>?
                    }
                }
                state.viewState.value = false
            }
        }

    }

    private fun setStatesData() {
        transaction.get()?.let { transaction ->
            requestAllApis()
            setTransactionNoteDate()
            state.toolbarTitle = transaction.getFormattedTime(FORMAT_LONG_OUTPUT)
            state.receiptVisibility.set(isShowReceiptSection(transaction))
        }
        state.isTransactionInProcessOrRejected.set(
            transaction.get()
                .isTransactionRejected() || transaction.get().isTransactionInProgress()
        )

    }

    private fun requestReceiptsAndTotalPurchases(responses: (RetroApiResponse<TotalPurchasesResponse>?, RetroApiResponse<TransactionReceiptResponse>?) -> Unit) {
        launch(Dispatcher.Background) {
            state.viewState.postValue(true)
            coroutineScope {
                val totalPurchaseResponse = async {
                    repository.getTotalPurchases(
                        getTotalPurchaseEndpoint()
                    )
                }
                val response = totalPurchaseResponse.await()
                when (response) {
                    is RetroApiResponse.Success -> {
                        transaction.get()?.let { it ->
                            val receiptResponse = if (isShowReceiptSection(transaction = it))
                                async {
                                    repository.getAllTransactionReceipts(
                                        transactionId = it.transactionId ?: ""
                                    )
                                } else null
                            responses(response, receiptResponse?.await())
                        }

                    }
                    is RetroApiResponse.Error -> {
                        state.viewState.postValue(false)
                    }
                }
            }
        }
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

/*    override fun getAllReceipts() {
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
    }*/

    override fun isShowReceiptSection(transaction: Transaction): Boolean {
        return when (transaction.productCode) {
            TransactionProductCode.ATM_DEPOSIT.pCode, TransactionProductCode.ATM_WITHDRAWL.pCode, TransactionProductCode.POS_PURCHASE.pCode -> true
            else -> false
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
        adapter.setList(getReceiptItems(receiptLis))
        state.receiptTitle.set(getReceiptTitle(adapter.getDataList()))
    }

    private fun setTransactionNoteDate() {
        if (transaction.get().getTransactionNoteDate(FORMAT_LONG_OUTPUT).isEmpty()) {
            state.transactionNoteDate =
                "Note added " + if (transaction.get()?.txnType == TxnType.DEBIT.type) transaction.get()?.transactionNoteDate else transaction.get()?.receiverTransactionNoteDate

        } else {
            state.transactionNoteDate = "Note added " + transaction.get()
                .getTransactionNoteDate(FORMAT_LONG_OUTPUT)
        }
    }
}