package co.yap.modules.dashboard.transaction.detail

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.R
import co.yap.databinding.ActivityTransactionDetailsBinding
import co.yap.modules.dashboard.transaction.receipt.add.AddTransactionReceiptFragment
import co.yap.modules.dashboard.transaction.receipt.previewer.PreviewTransactionReceiptFragment
import co.yap.modules.dashboard.transaction.receipt.viewer.ImageViewerActivity
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

class TransactionDetailsActivity : BaseBindingImageActivity<ITransactionDetails.ViewModel>(),
    ITransactionDetails.View {

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_transaction_details

    override val viewModel: ITransactionDetails.ViewModel
        get() = ViewModelProviders.of(this).get(TransactionDetailsViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addObservers()
        setSpentLabel()
        setAmount()
        setMapImageView()
        setTransactionImage()
        setCardMaskNo()
        setSubTitle()
        setTotalAmount()
        setDestinationAmount()
        setTxnFailedReason()
        setContentDataColor(viewModel.transaction.get())
        setLocationText()
        setStatusIcon()
    }

    private fun addObservers() {
        viewModel.clickEvent.observe(this, clickEvent)
        if (intent?.hasExtra(ExtraKeys.TRANSACTION_OBJECT_STRING.name) == true) {
            intent.getParcelableExtra<Transaction>(ExtraKeys.TRANSACTION_OBJECT_STRING.name)?.let {
                viewModel.transaction.set(
                    it
                )
            }
        }
    }

    private fun setStatusIcon() {
        getBindings().ivIncoming.setImageResource(viewModel.getStatusIcon(viewModel.transaction.get()))
    }

    private fun setDestinationAmount() {
        getBindings().tvDestinationAmount.text =
            viewModel.getForeignAmount(
                transaction = viewModel.transaction.get()
            ).toString()
                .toFormattedCurrency(
                    showCurrency = true,
                    currency = viewModel.transaction.get().getCurrency(),
                    withComma = true
                )
        setReceiptListener()
        setObserver()
    }

    private fun setObserver() {
        viewModel.responseReciept.observe(this, Observer {
            viewModel.setAdapterList(it)
        })
    }

    private fun setAmount() {
        getBindings().tvCardSpendAmount.text =
            viewModel.getSpentAmount(viewModel.transaction.get()).toString()
                .toFormattedCurrency(showCurrency = viewModel.transaction.get()?.status != TransactionStatus.FAILED.name)

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
        val totalAmount = viewModel.getCalculatedTotalAmount(viewModel.transaction.get()).toString()

        getBindings().tvTotalAmountValueCalculated.text =
            totalAmount.toFormattedCurrency()
        getBindings().tvTotalAmountValue.text =
            if (viewModel.transaction.get()?.txnType == TxnType.DEBIT.type) "- ${
                totalAmount.toFormattedCurrency(
                    showCurrency = false,
                    currency = SessionManager.getDefaultCurrency()
                )
            }" else "+ ${
                totalAmount.toFormattedCurrency(
                    showCurrency = false,
                    currency = SessionManager.getDefaultCurrency()
                )
            }"

        viewModel.transaction.get()?.let {
            when {
                it.getProductType() == TransactionProductType.IS_TRANSACTION_FEE && it.productCode != TransactionProductCode.MANUAL_ADJUSTMENT.pCode -> {
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
        viewModel.transaction.get()?.let {
            getBindings().tvTxnSubTitle.text =
                viewModel.getTransferType(it)
        }
    }

    private fun setLocationText() {
        val location = viewModel.getLocation(viewModel.transaction.get())
        getBindings().tvLocation.visibility =
            if (location.isEmpty()) View.GONE else View.VISIBLE
        getBindings().tvLocation.text = location

    }

    private fun setCardMaskNo() {
        val maskCardNo = viewModel.transaction.get()?.maskedCardNo?.split(" ")?.lastOrNull()
        maskCardNo?.let {
            getBindings().tvCardMask.text = "*${maskCardNo}"
        }
    }

    private fun setSpentLabel() {
        if (viewModel.transaction.get()?.productCode.equals(TransactionProductCode.POS_PURCHASE.pCode) && viewModel.transaction.get()?.currency != SessionManager.getDefaultCurrency()) {
            getBindings().tvCardSpent.text = "Spent in AED"
        } else {
            getBindings().tvCardSpent.text = viewModel.transaction.get().getSpentLabelText()
        }
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
                startFragmentForResult<AddTransactionReceiptFragment>(
                    fragmentName = AddTransactionReceiptFragment::class.java.name,
                    bundle = bundleOf(ExtraKeys.TRANSACTION_ID.name to viewModel.transaction.get()?.transactionId)
                ) { resultCode, _ ->
                    if (resultCode == Activity.RESULT_OK)
                        showAddReceiptSuccessDialog()
                }
            }

            PhotoSelectionType.GALLERY.name -> openImagePicker(PhotoSelectionType.GALLERY)
        }
    }

    private fun showAddReceiptSuccessDialog() {
        this.showReceiptSuccessDialog(
            description = getString(Strings.screen_transaction_details_receipt_success_label),
            addAnotherText = getString(Strings.screen_transaction_add_another_receipt),
            callback = {
                when (it) {
                    R.id.btnActionDone -> {
                        viewModel.getAllReceipts()
                    }
                    R.id.tvAddAnother -> {
                        viewModel.getAllReceipts()
                        showAddReceiptOptions()
                    }
                }
            }
        )
    }

    private fun openAddedReceipt(receiptModel: ReceiptModel) {
        launchActivity<ImageViewerActivity>(requestCode = RequestCodes.REQUEST_DELETE_RECEIPT) {
            putExtra(ExtraKeys.TRANSACTION_RECEIPT.name, receiptModel)
            putExtra(ExtraKeys.TRANSACTION_ID.name, viewModel.transaction.get()?.transactionId)
            putExtra(
                ExtraKeys.TRANSACTION_RECEIPT_LIST.name,
                viewModel.adapter.getDataList() as ArrayList<ReceiptModel>
            )
        }
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
                    val txnIconResId = transaction.getIcon()
                    if (transaction.productCode == TransactionProductCode.WITHDRAW_SUPPLEMENTARY_CARD.pCode || transaction.productCode == TransactionProductCode.TOP_UP_SUPPLEMENTARY_CARD.pCode) {
                        setVirtualCardIcon(transaction, getBindings().ivPicture)
                    } else if (txnIconResId != -1) {
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
                viewModel.transaction.get()?.transactionId ?: "",
                viewModel.transaction.get()?.txnType ?: ""
            ), Constants.INTENT_ADD_NOTE_REQUEST
        )
    }

    private fun setContentDataColor(transaction: Transaction?) {
        //strike-thru textview
        transaction?.let {
            getBindings().tvTotalAmountValue.paintFlags =
                if (transaction.isTransactionRejected() || transaction.status == TransactionStatus.FAILED.name) getBindings().tvTotalAmountValue.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG else 0
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
                    if (viewModel.transaction.get()?.txnType == TxnType.DEBIT.type) {
                        viewModel.transaction.get()?.transactionNote =
                            data?.getStringExtra(Constants.KEY_NOTE_VALUE).toString()
                        viewModel.transaction.get()?.receiverTransactionNoteDate =
                            DateUtils.getCurrentDateWithFormat(DateUtils.FORMAT_LONG_OUTPUT)
                    } else {
                        viewModel.transaction.get()?.receiverTransactionNote =
                            data?.getStringExtra(Constants.KEY_NOTE_VALUE).toString()
                        viewModel.transaction.get()?.receiverTransactionNoteDate =
                            DateUtils.getCurrentDateWithFormat(DateUtils.FORMAT_LONG_OUTPUT)
                    }

                    viewModel.state.transactionNoteDate =
                        viewModel.state.editNotePrefixText + DateUtils.getCurrentDateWithFormat(
                            DateUtils.FORMAT_LONG_OUTPUT
                        )
                }

                RequestCodes.REQUEST_DELETE_RECEIPT -> {
                    viewModel.getAllReceipts()
                }
            }
        }
    }

    private fun setVirtualCardIcon(
        transaction: Transaction,
        imageView: ImageView
    ) {
        transaction.virtualCardDesign?.let {
            try {
                val startColor = Color.parseColor(it.designCodeColors?.firstOrNull()?.colorCode)
                val endColor = Color.parseColor(
                    if (it.designCodeColors?.size ?: 0 > 1) it.designCodeColors?.get(1)?.colorCode else it.designCodeColors?.firstOrNull()?.colorCode
                )
                val gd = GradientDrawable(
                    GradientDrawable.Orientation.TOP_BOTTOM, intArrayOf(startColor, endColor)
                )
                gd.shape = GradientDrawable.OVAL

                imageView.background = null
                imageView.background = gd
                imageView.setImageResource(R.drawable.ic_virtual_card_yap_it)

            } catch (e: Exception) {
            }
        } ?: imageView.setImageResource(R.drawable.ic_virtual_card_yap_it)
    }

    override fun onImageReturn(mediaFile: MediaFile) {
        startFragmentForResult<PreviewTransactionReceiptFragment>(
            fragmentName = PreviewTransactionReceiptFragment::class.java.name,
            bundle = bundleOf(
                FILE_PATH to mediaFile.file.absolutePath,
                ExtraKeys.TRANSACTION_ID.name to viewModel.transaction.get()?.transactionId
            )
        ) { resultCode, _ ->
            if (resultCode == Activity.RESULT_OK)
                showAddReceiptSuccessDialog()
        }
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
        if (viewModel.transaction.get() is Transaction) {
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
        }
        finish()
    }

    fun getBindings(): ActivityTransactionDetailsBinding {
        return viewDataBinding as ActivityTransactionDetailsBinding
    }

    override fun onBackPressed() {
        setResult()
    }
}