package co.yap.modules.dashboard.transaction.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.R
import co.yap.databinding.ActivityTransactionDetailsBinding
import co.yap.modules.dashboard.transaction.interfaces.ITransactionDetails
import co.yap.modules.dashboard.transaction.viewmodels.TransactionDetailsViewModel
import co.yap.modules.others.helper.ImageBinding
import co.yap.modules.others.note.activities.TransactionNoteActivity
import co.yap.translation.Strings
import co.yap.yapcore.BR
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.enums.Transaction
import co.yap.yapcore.enums.TransactionProductCode
import co.yap.yapcore.enums.TransactionStatus
import co.yap.yapcore.enums.TxnType

class TransactionDetailsActivity : BaseBindingActivity<ITransactionDetails.ViewModel>(),
    ITransactionDetails.View {


    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_transaction_details

    override val viewModel: ITransactionDetails.ViewModel
        get() = ViewModelProviders.of(this).get(TransactionDetailsViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.clickEvent.observe(this, clickEvent)
        viewModel.transactionId = intent?.getStringExtra("transactionId")
        viewModel.state.categoryName.set(intent?.getStringExtra("categoryName"))
        viewModel.state.transactionAddress.set(intent?.getStringExtra("merchantAddress"))
        setTransactionImage()
    }

    var clickEvent = Observer<Int> {
        when (it) {
            R.id.ivClose -> finish()

            R.id.clNote ->
                if (viewModel.state.noteValue == getString(Strings.screen_transaction_details_display_text_note_description)) {
                    startActivityForResult(
                        TransactionNoteActivity.newIntent(
                            this,
                            "",
                            viewModel.transactionId ?: ""
                        ), Constants.INTENT_ADD_NOTE_REQUEST
                    )
                } else {
                    startActivityForResult(
                        TransactionNoteActivity.newIntent(
                            this,
                            viewModel.state.noteValue,
                            viewModel.transactionId ?: ""
                        ), Constants.INTENT_ADD_NOTE_REQUEST
                    )
                }
            R.id.clEditIcon ->
                if (viewModel.state.noteValue == getString(Strings.screen_transaction_details_display_text_note_description)) {
                    startActivityForResult(
                        TransactionNoteActivity.newIntent(
                            this,
                            "",
                            viewModel.transactionId ?: ""
                        ), Constants.INTENT_ADD_NOTE_REQUEST
                    )
                } else {
                    startActivityForResult(
                        TransactionNoteActivity.newIntent(
                            this,
                            viewModel.state.noteValue,
                            viewModel.transactionId ?: ""
                        ), Constants.INTENT_ADD_NOTE_REQUEST
                    )
                }
            R.id.ivShareButton -> {

            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constants.INTENT_ADD_NOTE_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                viewModel.state.addNoteTitle =
                    getString(Strings.screen_transaction_details_display_text_edit_note)
                viewModel.state.noteValue =
                    data?.getStringExtra(Constants.KEY_NOTE_VALUE).toString()
            }
        }

    }

    //This comment is intentional
    /* private fun shareInfo() {
         val sharingIntent = Intent(Intent.ACTION_SEND)
         sharingIntent.type = "text/plain"
         // not set because ios team is not doing this.
         //sharingIntent.putExtra(Intent.EXTRA_SUBJECT, viewModel.state.title.get())
         sharingIntent.putExtra(Intent.EXTRA_TEXT, getBody())
         startActivity(Intent.createChooser(sharingIntent, "Share"))
     }

     private fun getBody(): String {
         if (viewModel.state.isYtoYTransfer.get() == true) {
             return "Sender: ${viewModel.state.transactionSender}\n" +
                     "Receiver: ${viewModel.state.transactionReceiver}\n" +
                     "${viewModel.state.spentTitle}: ${viewModel.state.spentAmount}\n" +
                     "${viewModel.state.feeTitle}: ${viewModel.state.feeAmount}\n" +
                     "${viewModel.state.totalTitle}: ${viewModel.state.totalAmount}\n" +
                     "Transaction note: ${viewModel.state.noteValue}\n"
         } else {
             return "${viewModel.state.spentTitle}: ${viewModel.state.spentAmount}\n" +
                     "${viewModel.state.feeTitle}: ${viewModel.state.feeAmount}\n" +
                     "${viewModel.state.totalTitle}: ${viewModel.state.totalAmount}\n" +
                     "Transaction note: ${viewModel.state.noteValue}\n"
         }

     }*/

    private fun setTransactionImage() {
        val productCode = intent?.getStringExtra("productCode")
        val txnType = intent?.getStringExtra("txnType")
        val status = intent?.getStringExtra("status")
        val title = intent?.getStringExtra("title")
        viewModel.state.transactionTitle = title ?: "Unknown"

        when {
            TransactionProductCode.Y2Y_TRANSFER.pCode == productCode ?: "" -> {
                ImageBinding.loadAvatar(
                    getBindings().ivPicture,
                    "",
                    viewModel.state.transactionTitle,
                    android.R.color.transparent,
                    R.dimen.text_size_h2
                )
            }
            TransactionProductCode.POS_PURCHASE.pCode == productCode ?: "" -> {
                ImageBinding.loadAvatar(
                    getBindings().ivPicture,
                    "",
                    viewModel.state.transactionTitle,
                    android.R.color.transparent,
                    R.dimen.text_size_h2
                )
            }
            else -> {
                val resId = getTransactionIcon(productCode ?: "", txnType ?: "", status ?: "")
                getBindings().ivPicture.setImageResource(resId)
            }
        }
    }

    private fun getTransactionIcon(
        productCode: String,
        txnType: String = "",
        transactionStatus: String
    ): Int {
        if (productCode.isBlank() || txnType.isBlank() || transactionStatus.isBlank()) return 0

        return if (transactionStatus == TransactionStatus.FAILED.name) {
            R.drawable.ic_reverted
        } else
            when {
                Transaction.isCash(productCode) -> R.drawable.ic_transaction_cash
                Transaction.isBank(productCode) -> R.drawable.ic_transaction_bank
                Transaction.isFee(productCode) -> R.drawable.ic_package_standered
                Transaction.isRefund(productCode) -> R.drawable.ic_refund
                TransactionProductCode.TOP_UP_SUPPLEMENTARY_CARD.pCode == productCode || TransactionProductCode.WITHDRAW_SUPPLEMENTARY_CARD.pCode == productCode -> {
                    if (txnType == TxnType.DEBIT.type) R.drawable.ic_minus_transactions else R.drawable.ic_plus_transactions
                }
                else -> 0
            }
    }


    fun getBindings(): ActivityTransactionDetailsBinding {
        return viewDataBinding as ActivityTransactionDetailsBinding
    }
}