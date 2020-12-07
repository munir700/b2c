package co.yap.modules.dashboard.transaction.viewmodels

import android.app.Application
import androidx.databinding.ObservableField
import co.yap.R
import co.yap.modules.dashboard.transaction.TransactionReceiptAdapter
import co.yap.modules.dashboard.transaction.interfaces.ITransactionDetails
import co.yap.modules.dashboard.transaction.states.TransactionDetailsState
import co.yap.networking.transactions.responsedtos.ReceiptModel
import co.yap.networking.transactions.responsedtos.transaction.Transaction
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
import java.util.ArrayList


class TransactionDetailsViewModel(application: Application) :
    BaseViewModel<ITransactionDetails.State>(application), ITransactionDetails.ViewModel {

    override val state: TransactionDetailsState = TransactionDetailsState()
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override var transaction: ObservableField<Transaction> = ObservableField()
    override var adapter: TransactionReceiptAdapter = TransactionReceiptAdapter(mutableListOf())

    override fun onCreate() {
        super.onCreate()
        setStatesData()
        adapter.setList(getReciptItems())
    }

    private fun getReciptItems(): List<ReceiptModel> {
        val list: MutableList<ReceiptModel> = arrayListOf()
        list.add(ReceiptModel("Receipt 1"))
        list.add(ReceiptModel("Receipt 4"))
        list.add(ReceiptModel("Receipt 5"))
        list.add(ReceiptModel("Receipt 6"))
        list.add(ReceiptModel("Receipt 6"))
        return list
    }

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
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
            state.categoryTitle.set(transaction.getCategoryTitle())
            state.categoryIcon.set(transaction.getCategoryIcon())
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

     fun getUploadProfileOptions(isShowRemovePhoto: Boolean): ArrayList<BottomSheetItem> {
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
                icon = R.drawable.ic_choose_photo,
                title = getString(Strings.screen_update_profile_photo_display_text_choose_photo),
                tag = PhotoSelectionType.GALLERY.name
            )
        )
        if (isShowRemovePhoto)
            list.add(
                BottomSheetItem(
                    icon = R.drawable.ic_remove,
                    title = getString(Strings.screen_update_profile_photo_display_text_remove_photo),
                    tag = PhotoSelectionType.REMOVE_PHOTO.name
                )
            )

        return list
    }

}