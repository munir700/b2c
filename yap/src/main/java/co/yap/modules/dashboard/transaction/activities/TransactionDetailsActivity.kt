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
import co.yap.networking.transactions.responsedtos.transaction.Content
import co.yap.yapcore.BR
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.enums.TransactionLabelsCode
import co.yap.yapcore.enums.TransactionProductCode
import co.yap.yapcore.enums.TxnType
import co.yap.yapcore.helpers.extentions.getLabelValues
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
        getBindings().ivMap.setImageResource(getMapImage())
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


    private fun getMapImage(): Int {
        viewModel.transaction.get()?.let { transaction ->
            if (transaction.getLabelValues() == TransactionLabelsCode.IS_TRANSACTION_FEE) {
                return R.drawable.ic_image_light_red_background
            }
            return (when (transaction.productCode) {
                TransactionProductCode.Y2Y_TRANSFER.pCode -> R.drawable.ic_image_blue_background
                TransactionProductCode.TOP_UP_SUPPLEMENTARY_CARD.pCode, TransactionProductCode.WITHDRAW_SUPPLEMENTARY_CARD.pCode -> R.drawable.ic_image_blue_background
                TransactionProductCode.UAEFTS.pCode, TransactionProductCode.DOMESTIC.pCode, TransactionProductCode.RMT.pCode, TransactionProductCode.SWIFT.pCode, TransactionProductCode.CASH_PAYOUT.pCode, TransactionProductCode.TOP_UP_VIA_CARD.pCode, TransactionProductCode.INWARD_REMITTANCE.pCode, TransactionProductCode.LOCAL_INWARD_TRANSFER.pCode -> R.drawable.ic_image_light_blue_background
                TransactionProductCode.CARD_REORDER.pCode -> R.drawable.ic_image_light_red_background
                TransactionProductCode.ATM_WITHDRAWL.pCode, TransactionProductCode.POS_PURCHASE.pCode, TransactionProductCode.CASH_DEPOSIT_AT_RAK.pCode, TransactionProductCode.MASTER_CARD_ATM_WITHDRAWAL.pCode, TransactionProductCode.CHEQUE_DEPOSIT_AT_RAK.pCode -> R.drawable.ic_map
                else -> 0
            })
        } ?: return 0
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