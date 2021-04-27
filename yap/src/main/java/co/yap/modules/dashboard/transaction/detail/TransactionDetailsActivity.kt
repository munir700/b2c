package co.yap.modules.dashboard.transaction.detail

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast.LENGTH_SHORT
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.R
import co.yap.databinding.ActivityTransactionDetailsBinding
import co.yap.modules.dashboard.transaction.feedback.TransactionFeedbackFragment
import co.yap.modules.dashboard.transaction.category.TransactionCategoryFragment
import co.yap.modules.dashboard.transaction.receipt.add.AddTransactionReceiptFragment
import co.yap.modules.dashboard.transaction.receipt.previewer.PreviewTransactionReceiptFragment
import co.yap.modules.dashboard.transaction.receipt.viewer.ImageViewerActivity
import co.yap.modules.others.note.activities.TransactionNoteActivity
import co.yap.networking.transactions.responsedtos.ReceiptModel
import co.yap.networking.transactions.responsedtos.transaction.TapixCategory
import co.yap.networking.transactions.responsedtos.transaction.Transaction
import co.yap.translation.Strings
import co.yap.widgets.bottomsheet.BottomSheetItem
import co.yap.yapcore.BR
import co.yap.yapcore.BaseBindingImageActivity
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.constants.Constants.FILE_PATH
import co.yap.yapcore.constants.RequestCodes
import co.yap.yapcore.enums.PhotoSelectionType
import co.yap.yapcore.enums.TransactionProductCode
import co.yap.yapcore.enums.TransactionStatus
import co.yap.yapcore.enums.TxnType
import co.yap.yapcore.helpers.DateUtils
import co.yap.yapcore.helpers.ExtraKeys
import co.yap.yapcore.helpers.ImageBinding
import co.yap.yapcore.helpers.extentions.*
import co.yap.yapcore.helpers.showReceiptSuccessDialog
import co.yap.yapcore.interfaces.OnItemClickListener
import pl.aprilapps.easyphotopicker.MediaFile

class TransactionDetailsActivity : BaseBindingImageActivity<ITransactionDetails.ViewModel>(),
    ITransactionDetails.View {

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_transaction_details
    override val viewModel: ITransactionDetails.ViewModel
        get() = ViewModelProviders.of(this).get(TransactionDetailsViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setObservers()
        setTransactionImage()
        setContentDataColor(viewModel.transaction.get())
    }

    override fun setObservers() {
        viewModel.clickEvent.observe(this, clickEvent)
        if (intent?.hasExtra(ExtraKeys.TRANSACTION_OBJECT_STRING.name) == true) {
            intent.getParcelableExtra<Transaction>(ExtraKeys.TRANSACTION_OBJECT_STRING.name)?.let {
                viewModel.transaction.set(it)
                viewModel.composeTransactionDetail(it)
                getBindings().ivMap.setImageResource(viewModel.state.coverImage.get())
            }
        }
        viewModel.responseReciept.observe(this, Observer {
            viewModel.setAdapterList(it)
        })
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
            R.id.tvTapToChange ->{
              updateCategory()
            }
            R.id.tvImproveLogo -> {
                startFragmentForResult<TransactionFeedbackFragment>(TransactionFeedbackFragment::class.java.name, bundleOf(
                    Constants.FEEDBACK_LOCATION to viewModel.state.transactionData.get()?.locationValue,
                    Constants.FEEDBACK_TITLE to viewModel.state.transactionData.get()?.transactionTitle,
                    Constants.TRANSACTION_DETAIL to viewModel.transaction.get()
                )){resultCode, _ ->
                    if (resultCode == Activity.RESULT_OK)
                        showFeedbackSuccessDialog()
                }
            }
        }
    }

    private fun updateCategory() {
        startFragmentForResult<TransactionCategoryFragment>(TransactionCategoryFragment::class.java.name,
        bundleOf(Constants.TRANSACTION_ID to viewModel.transaction.get()?.transactionId, Constants.PRE_SELECTED_CATEGORY to viewModel.state.updatedCategory.get()?.categoryName)
        ){resultCode, data ->
            if (resultCode == Activity.RESULT_OK){
                val category = data?.getValue(Constants.UPDATED_CATEGORY,"PARCEABLE") as TapixCategory
                viewModel.state.updatedCategory.set(category)
                viewModel.state.categoryDescription.set(viewModel.state.updatedCategory.get()?.description)
                makeToast(this,"category updated sucessfully", LENGTH_SHORT)
            }
        }
    }

    private fun showFeedbackSuccessDialog() {
        this.showReceiptSuccessDialog(
            description = getString(Strings.screen_transaction_details_feedback_success_label),
            addOtherVisibility= false
        )
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
                        viewModel.requestAllApis()
                    }
                    R.id.tvAddAnother -> {
                        viewModel.requestAllApis()
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
                    viewModel.state.transactionNoteDate = "Note added  ${
                    DateUtils.getCurrentDateWithFormat(
                        DateUtils.FORMAT_LONG_OUTPUT
                    )
                    }"
                }

                RequestCodes.REQUEST_DELETE_RECEIPT -> {
                    viewModel.requestAllApis()

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

    override fun removeObservers() {
        viewModel.clickEvent.removeObserver(clickEvent)
    }

    fun getBindings(): ActivityTransactionDetailsBinding =
        viewDataBinding as ActivityTransactionDetailsBinding

    override fun onBackPressed() {
        setResult()
    }

    override fun onDestroy() {
        removeObservers()
        super.onDestroy()
    }
}
