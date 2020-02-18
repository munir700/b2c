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
import co.yap.modules.others.note.activities.TransactionNoteActivity
import co.yap.translation.Strings
import co.yap.yapcore.BR
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.constants.Constants

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

    fun setTransactionImage() {
        val productCode = intent?.getStringExtra("productCode")
        val txnType = intent?.getStringExtra("txnType")
        if (productCode == Constants.Y_TO_Y_TRANSFER) {
            getBindings().ivPicture.setImageDrawable(this.getDrawable(R.drawable.ic_yap_to_yap))
        } else {
            if (productCode == Constants.TOP_UP_VIA_CARD) {
                getBindings().ivPicture.setImageDrawable(this.getDrawable(R.drawable.ic_top_up))
            } else {
                if (productCode == Constants.SUPP_WITHDRAW || txnType == Constants.SUPP_CARD_TOP_UP) {
                    if (txnType == Constants.MANUAL_DEBIT) {
                        getBindings().ivPicture.setImageDrawable(
                            this.getDrawable(
                                R.drawable.ic_minus_transactions
                            )
                        )
                        getBindings().ivPicture.setPadding(0, 0, 0, 0)
                    } else if (txnType == Constants.MANUAL_CREDIT) {
                        getBindings().ivPicture.setImageDrawable(
                            this.getDrawable(
                                R.drawable.ic_plus_transactions
                            )
                        )
                        getBindings().ivPicture.setPadding(0, 0, 0, 0)
                    }
                } else if (txnType == Constants.MANUAL_DEBIT) {
                    getBindings().ivPicture.setImageDrawable(
                        this.getDrawable(
                            R.drawable.ic_outgoing
                        )
                    )
                } else if (txnType == Constants.MANUAL_CREDIT) {
                    getBindings().ivPicture.setImageDrawable(
                        this.getDrawable(
                            R.drawable.ic_incoming
                        )
                    )
                }
            }

        }
    }

    fun getBindings(): ActivityTransactionDetailsBinding {
        return viewDataBinding as ActivityTransactionDetailsBinding
    }
}