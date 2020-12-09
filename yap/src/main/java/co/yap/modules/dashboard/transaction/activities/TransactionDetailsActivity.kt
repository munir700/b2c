package co.yap.modules.dashboard.transaction.activities

import android.app.Activity
import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.R
import co.yap.databinding.ActivityTransactionDetailsBinding
import co.yap.modules.dashboard.transaction.addreceipt.AddTransactionReceiptFragment
import co.yap.modules.dashboard.transaction.interfaces.ITransactionDetails
import co.yap.modules.dashboard.transaction.previewreceipt.PreviewTransactionReceiptFragment
import co.yap.modules.dashboard.transaction.viewmodels.TransactionDetailsViewModel
import co.yap.modules.others.note.activities.TransactionNoteActivity
import co.yap.networking.transactions.responsedtos.ReceiptModel
import co.yap.networking.transactions.responsedtos.transaction.Transaction
import co.yap.translation.Strings
import co.yap.widgets.bottomsheet.BottomSheetItem
import co.yap.yapcore.BR
import co.yap.yapcore.BaseBindingImageActivity
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.constants.Constants.FILE_PATH
import co.yap.yapcore.constants.RequestCodes
import co.yap.yapcore.enums.*
import co.yap.yapcore.helpers.DateUtils
import co.yap.yapcore.helpers.ExtraKeys
import co.yap.yapcore.helpers.ImageBinding
import co.yap.yapcore.helpers.extentions.*
import co.yap.yapcore.helpers.showReceiptSuccessDialog
import co.yap.yapcore.interfaces.OnItemClickListener
import co.yap.yapcore.managers.SessionManager
import pl.aprilapps.easyphotopicker.MediaFile

//pass image url or uri along with title
//activity?.startImagePreviewerActivity( activity , imageSrc = "https://scoopak.com/wp-content/uploads/2013/06/free-hd-natural-wallpapers-download-for-pc.jpg",title = "")

class TransactionDetailsActivity : BaseBindingImageActivity<ITransactionDetails.ViewModel>(),
    ITransactionDetails.View {

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_transaction_details

    override val viewModel: ITransactionDetails.ViewModel
        get() = ViewModelProviders.of(this).get(TransactionDetailsViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.clickEvent.observe(this, clickEvent)
        viewModel.transaction.set(
            intent?.getParcelableExtra(
                ExtraKeys.TRANSACTION_OBJECT_STRING.name
            ) as Transaction
        )
        setSpentLabel()
        setAmount()
        setMapImageView()
        setTransactionImage()
        setTransactionTitle()
        setCardMaskNo()
        setSubTitle()
        setTotalAmount()
        setTxnFailedReason()
        setContentDataColor(viewModel.transaction.get())
        setLocationText()
        setReceiptListener()
    }

    private fun setAmount() {
        getBindings().tvCardSpendAmount.text = viewModel.transaction.get()?.let {
            when {

                it.status == TransactionStatus.FAILED.name -> "0.00".toFormattedCurrency(
                    showCurrency = false)
                it.getLabelValues() == TransactionLabelsCode.IS_TRANSACTION_FEE && it.productCode != TransactionProductCode.MANUAL_ADJUSTMENT.pCode -> {
                    "0.00".toFormattedCurrency()
                }
                it.productCode == TransactionProductCode.SWIFT.pCode || it.productCode == TransactionProductCode.RMT.pCode -> {
                    (it.settlementAmount ?: "0.00").toString().toFormattedCurrency()
                }
                else -> it.amount.toString().toFormattedCurrency()
            }

        } ?: "0.00"
    }

    private fun setTxnFailedReason() {
        val msg = viewModel.transaction.get()?.let {
            when {
                it.isTransactionInProgress() || it.isTransactionRejected() -> {
                    getBindings().tvTransactionHeading.setTextColor(this.getColors(R.color.colorPrimaryDarkFadedLight))
                    getBindings().tvTotalAmountValue.setTextColor(this.getColors(R.color.colorFaded))
                    getBindings().tvTransactionSubheading.alpha = 0.5f
                    getBindings().ivCategoryIcon.alpha = 0.5f
                    return@let if (it.isTransactionRejected()) getCancelReason() else getCutOffMsg(
                        it
                    )
                }
                else -> ""
            }
        } ?: ""
        if (msg.isBlank()) {
            getBindings().cancelReasonLy.visibility = View.GONE
        } else {
            getBindings().tvCanceReason.text = msg
        }
    }

    private fun getCutOffMsg(transaction: Transaction): String {
        return if (transaction.showCutOffMsg()) getString(R.string.screen_transaction_detail_text_cut_off_msg) else ""
    }

    private fun getCancelReason(): String {
        return getString(R.string.screen_transaction_detail_text_cancelled_reason)
    }

    private fun setTotalAmount() {
        val totalAmount = viewModel.transaction.get()?.let {
            when (it.productCode) {
                TransactionProductCode.RMT.pCode, TransactionProductCode.SWIFT.pCode -> {
                    val totalFee = (it.postedFees ?: 0.00).plus(it.vatAmount ?: 0.0) ?: 0.0
                    (it.settlementAmount ?: 0.00).plus(totalFee).toString()
                }
                else -> if (it.txnType == TxnType.DEBIT.type) it.totalAmount.toString() else it.amount.toString()
            }
        } ?: "0.0"
        getBindings().tvTotalAmountValueCalculated.text =
            totalAmount.toFormattedCurrency()
        getBindings().tvTotalAmountValue.text =
            if (viewModel.transaction.get()?.txnType == TxnType.DEBIT.type) "- ${
                totalAmount.toFormattedCurrency(
                    showCurrency = false,
                    currency = SessionManager.getDefaultCurrency()
                )
            }" else "+ ${
                totalAmount.toFormattedCurrency(showCurrency = false,
                    currency = SessionManager.getDefaultCurrency())
            }"

        // hiding visibility on nada's request
        viewModel.transaction.get()?.let {
            when {
                it.getLabelValues() == TransactionLabelsCode.IS_TRANSACTION_FEE && it.productCode != TransactionProductCode.MANUAL_ADJUSTMENT.pCode -> {
                    getBindings().tvTotalAmountValueCalculated.visibility = View.VISIBLE
                    getBindings().tvTotalAmount.visibility = View.VISIBLE
                }
                else -> {
                    getBindings().tvTotalAmountValueCalculated.visibility = View.GONE
                    getBindings().tvTotalAmount.visibility = View.GONE
                }
            }
        }
    }

    private fun setSubTitle() {
        val subTitle = viewModel.transaction.get()?.let {
            when {
                it.isTransactionRejected() -> "Transfer Rejected"
                it.isTransactionInProgress() -> "Transfer Pending"
                else -> ""
            }
        }

        if (subTitle.isNullOrBlank()) {
            getBindings().tvTxnSubTitle.text =
                if (TransactionProductCode.Y2Y_TRANSFER.pCode == viewModel.transaction.get()?.productCode) getString(
                    Strings.transaction_narration_y2y_transfer_detail
                ) else viewModel.transaction.get()
                    ?.getTransactionTypeTitle()
        } else {
            getBindings().tvTxnSubTitle.text = subTitle
        }
    }

    private fun setLocationText() {
        val location = viewModel.transaction.get()?.let {
            when (it.productCode) {
                TransactionProductCode.FUND_LOAD.pCode -> it.otherBankName ?: ""
                else -> it.cardAcceptorLocation ?: ""
            }
        }
        getBindings().tvLocation.visibility =
            if (location.isNullOrEmpty()) View.GONE else View.VISIBLE
        getBindings().tvLocation.text = location

    }

    private fun setCardMaskNo() {
        val maskCardNo = viewModel.transaction.get()?.maskedCardNo?.split(" ")?.lastOrNull()
        maskCardNo?.let {
            getBindings().tvCardMask.text = "*${maskCardNo}"
        }
    }

    private fun setSpentLabel() {
        getBindings().tvCardSpent.text = viewModel.transaction.get().getSpentLabelText()
    }

    private fun setReceiptListener() {
        viewModel.adapter.setItemListener(onReceiptClickListener)
    }

    private val onReceiptClickListener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            when (data) {
                is BottomSheetItem -> handleReceiptOptionClick(data)
                is ReceiptModel -> openAddedReceipt(data)
            }
        }
    }

    var clickEvent = Observer<Int> {
        when (it) {
            R.id.clNote, R.id.clEditIcon ->
                if (viewModel.state.txnNoteValue.get().isNullOrBlank()) {
                    openNoteScreen()
                } else
                    openNoteScreen(noteValue = viewModel.state.txnNoteValue.get() ?: "")
            R.id.clRecipt -> {
                showAddReceiptOptions()
            }
        }
    }

    private fun showAddReceiptOptions() {
        launchSheet(
            itemClickListener = onReceiptClickListener,
            itemsList = viewModel.getAddReceiptOptions(),
            heading = getString(Strings.screen_transaction_details_display_sheet_heading),
            subHeading = getString(Strings.screen_transaction_details_display_sheet_sub_heading)
        )
    }

    private fun handleReceiptOptionClick(bottomSheetItem: BottomSheetItem) {
        when (bottomSheetItem.tag) {
            PhotoSelectionType.CAMERA.name -> {
                startFragment<AddTransactionReceiptFragment>(
                    fragmentName = AddTransactionReceiptFragment::class.java.name
                )
            }

            PhotoSelectionType.GALLERY.name -> openImagePicker(PhotoSelectionType.GALLERY)
        }
    }

    private fun openAddedReceipt(receiptModel: ReceiptModel) {

    }

    private fun setTransactionTitle() {
        viewModel.state.transactionTitle.set(viewModel.transaction.get().getTransactionTitle())
    }

    private fun setMapImageView() {
        val mapResId = viewModel.transaction.get().getMapImage()
        if (mapResId != -1)
            getBindings().ivMap.setImageResource(mapResId)
        else
            getBindings().ivMap.visibility = View.GONE
    }

    private fun setTransactionImage() {
        viewModel.transaction.get()?.let { transaction ->
            when (TransactionProductCode.Y2Y_TRANSFER.pCode) {
                transaction.productCode ?: "" -> {
                    ImageBinding.loadAvatar(
                        getBindings().ivPicture,
                        if (TxnType.valueOf(
                                transaction.txnType ?: ""
                            ) == TxnType.DEBIT
                        ) transaction.receiverProfilePictureUrl else transaction.senderProfilePictureUrl,
                        if (transaction.txnType == TxnType.DEBIT.type) transaction.receiverName else transaction.senderName,
                        android.R.color.transparent,
                        R.dimen.text_size_h2
                    )
                }
                else -> {
                    val txnIconResId = transaction.getTransactionIcon()
                    if (txnIconResId != -1) {
                        getBindings().ivPicture.setImageResource(txnIconResId)
                        when (txnIconResId) {
                            R.drawable.ic_rounded_plus -> {
                                getBindings().ivPicture.setBackgroundResource(R.drawable.bg_round_grey)
                            }
                            R.drawable.ic_grey_minus_transactions, R.drawable.ic_grey_plus_transactions -> {
                                getBindings().ivPicture.setBackgroundResource(R.drawable.bg_round_disabled_transaction)
                            }
                        }
                    } else
                        setInitialsAsTxnImage(transaction)
                }
            }
        }
    }

    private fun setInitialsAsTxnImage(transaction: Transaction) {
        ImageBinding.loadAvatar(
            getBindings().ivPicture,
            "",
            transaction.title,
            android.R.color.transparent,
            R.dimen.text_size_h2
        )

    }

    private fun openNoteScreen(noteValue: String = "") {
        startActivityForResult(
            TransactionNoteActivity.newIntent(
                this,
                noteValue,
                viewModel.transaction.get()?.transactionId ?: ""
            ), Constants.INTENT_ADD_NOTE_REQUEST
        )
    }

    private fun setContentDataColor(transaction: Transaction?) {
        //strike-thru textview
        transaction?.let {
            getBindings().tvTotalAmountValue.paintFlags =
                if (transaction.isTransactionCancelled() || transaction.status == TransactionStatus.FAILED.name) getBindings().tvTotalAmountValue.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG else 0
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                Constants.INTENT_ADD_NOTE_REQUEST -> {
                    viewModel.state.txnNoteValue.set(
                        data?.getStringExtra(Constants.KEY_NOTE_VALUE).toString()
                    )
                    viewModel.transaction.get()?.transactionNote =
                        data?.getStringExtra(Constants.KEY_NOTE_VALUE).toString()
                    viewModel.state.transactionNoteDate =
                        viewModel.state.editNotePrefixText + DateUtils.getCurrentDateWithFormat(
                            DateUtils.FORMAT_LONG_OUTPUT
                        )
                    viewModel.transaction.get()?.transactionNoteDate =
                        DateUtils.getCurrentDateWithFormat(DateUtils.FORMAT_LONG_OUTPUT)
                }
                RequestCodes.REQUEST_ADD_RECEIPT -> {
                    this.showReceiptSuccessDialog(
                        description = getString(Strings.screen_transaction_details_receipt_success_label),
                        addAnotherText = getString(Strings.screen_transaction_add_another_receipt),
                        callback = {
                            when (it) {
                                R.id.btnActionDone -> {
                                    viewModel.addNewReceipt(ReceiptModel())
                                }
                                R.id.tvAddAnother -> {
                                    showAddReceiptOptions()
                                }
                            }
                        }
                    )
                }

                RequestCodes.REQUEST_DELETE_RECEIPT -> {
                    viewModel.deleteReceipt(0)
                }
            }
        }
    }

    override fun onImageReturn(mediaFile: MediaFile) {
        startFragment<PreviewTransactionReceiptFragment>(
            fragmentName = PreviewTransactionReceiptFragment::class.java.name,
            bundle = bundleOf(FILE_PATH to mediaFile.file.absolutePath)
        )
    }

    fun getBindings(): ActivityTransactionDetailsBinding {
        return viewDataBinding as ActivityTransactionDetailsBinding
    }

    override fun onToolBarClick(id: Int) {
        when (id) {
            R.id.ivLeftIcon -> {
                setResult()
            }
        }
    }

    private fun setResult() {
        val intent = Intent()
        intent.putExtra(
            ExtraKeys.TRANSACTION_OBJECT_STRING.name,
            viewModel.transaction.get() as Transaction
        )
        intent.putExtra(
            ExtraKeys.TRANSACTION_OBJECT_GROUP_POSITION.name, getIntent().getIntExtra(
                ExtraKeys.TRANSACTION_OBJECT_GROUP_POSITION.name, -1
            )
        )
        intent.putExtra(
            ExtraKeys.TRANSACTION_OBJECT_CHILD_POSITION.name, getIntent().getIntExtra(
                ExtraKeys.TRANSACTION_OBJECT_CHILD_POSITION.name, -1
            )
        )

        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    override fun onBackPressed() {
        setResult()
    }
}