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
import co.yap.networking.transactions.responsedtos.transaction.Content
import co.yap.yapcore.BR
import co.yap.yapcore.helpers.ImageBinding
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.enums.TransactionProductCode
import co.yap.yapcore.enums.TxnType
import co.yap.yapcore.helpers.extentions.getMapImage
import co.yap.yapcore.helpers.extentions.getTransactionIcon
import co.yap.yapcore.helpers.extentions.getTransactionTitle

class TransactionDetailsActivity : BaseBindingActivity<ITransactionDetails.ViewModel>(),
    ITransactionDetails.View {

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_transaction_details

    override val viewModel: ITransactionDetails.ViewModel
        get() = ViewModelProviders.of(this).get(TransactionDetailsViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.clickEvent.observe(this, clickEvent)
        viewModel.transaction.set(intent?.getParcelableExtra("transaction") as Content)
        setMapImageView()
        setTransactionImage()
        setTransactionTitle()
    }

    var clickEvent = Observer<Int> {
        when (it) {
            R.id.ivClose -> finish()
            R.id.clNote, R.id.clEditIcon ->
                if (viewModel.state.txnNoteValue.get().isNullOrBlank()) {
                    openNoteScreen()
                } else
                    openNoteScreen(noteValue = viewModel.state.txnNoteValue.get() ?: "")
        }
    }

    private fun setTransactionTitle() {
        viewModel.state.transactionTitle.set(viewModel.transaction.get().getTransactionTitle())
    }

    private fun setMapImageView() {
        getBindings().ivMap.setImageResource(viewModel.transaction.get().getMapImage())
    }

    private fun setTransactionImage() {
        viewModel.transaction.get()?.let { transaction ->
            when {
                TransactionProductCode.Y2Y_TRANSFER.pCode == transaction.productCode ?: "" || TransactionProductCode.POS_PURCHASE.pCode == transaction.productCode ?: "" -> {
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
                    getBindings().ivPicture.setImageResource(transaction.getTransactionIcon())
                }
            }
        }
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constants.INTENT_ADD_NOTE_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                viewModel.state.txnNoteValue.set(
                    data?.getStringExtra(Constants.KEY_NOTE_VALUE).toString()
                )
            }
        }

    }

    fun getBindings(): ActivityTransactionDetailsBinding {
        return viewDataBinding as ActivityTransactionDetailsBinding
    }
}