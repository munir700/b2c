package co.yap.modules.dashboard.transaction.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.R
import co.yap.modules.dashboard.transaction.interfaces.ITransactionDetails
import co.yap.modules.dashboard.transaction.viewmodels.TransactionDetailsViewModel
import co.yap.modules.transaction_note.activities.TransactionNoteActivity
import co.yap.translation.Strings
import co.yap.yapcore.BR
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.constants.Constants

class TransactionDetailsActivity : BaseBindingActivity<ITransactionDetails.ViewModel>(),
    ITransactionDetails.View {

    companion object {
        fun newIntent(context: Context, transactionId: String): Intent {
            val intent = Intent(context, TransactionDetailsActivity::class.java)
            intent.putExtra(Constants.TRANSACTION_ID, transactionId)
            return intent
        }
    }

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_transaction_details

    override val viewModel: ITransactionDetails.ViewModel
        get() = ViewModelProviders.of(this).get(TransactionDetailsViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.clickEvent.observe(this, clickEvent)
        viewModel.transactionId = intent.getStringExtra(Constants.TRANSACTION_ID)
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
                            viewModel.transactionId
                        ), Constants.INTENT_ADD_NOTE_REQUEST
                    )
                } else {
                    startActivityForResult(
                        TransactionNoteActivity.newIntent(
                            this,
                            viewModel.state.noteValue,
                            viewModel.transactionId
                        ), Constants.INTENT_ADD_NOTE_REQUEST
                    )
                }
            R.id.clEditIcon ->
                if (viewModel.state.noteValue == getString(Strings.screen_transaction_details_display_text_note_description)) {
                    startActivityForResult(
                        TransactionNoteActivity.newIntent(
                            this,
                            "",
                            viewModel.transactionId
                        ), Constants.INTENT_ADD_NOTE_REQUEST
                    )
                } else {
                    startActivityForResult(
                        TransactionNoteActivity.newIntent(
                            this,
                            viewModel.state.noteValue,
                            viewModel.transactionId
                        ), Constants.INTENT_ADD_NOTE_REQUEST
                    )
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
}