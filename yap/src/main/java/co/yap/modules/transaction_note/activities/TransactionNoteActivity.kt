package co.yap.modules.transaction_note.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.R
import co.yap.modules.transaction_note.interfaces.ITransactionNote
import co.yap.modules.transaction_note.viewmodels.TransactionNoteViewModel
import co.yap.yapcore.BR
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.helpers.Utils
import kotlinx.android.synthetic.main.activity_transaction_note.*

class TransactionNoteActivity : BaseBindingActivity<ITransactionNote.ViewModel>() {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_transaction_note

    override val viewModel: ITransactionNote.ViewModel
        get() = ViewModelProviders.of(this).get(TransactionNoteViewModel::class.java)

    companion object {
        const val INTENT_ADD_NOTE_REQUEST = 2222
        const val KEY_NOTE_VALUE = "noteValue"
        fun newIntent(context: Context, noteValue: String = ""): Intent {
            val intent = Intent(context, TransactionNoteActivity::class.java)
            intent.putExtra(KEY_NOTE_VALUE, noteValue)
            return intent
        }
    }

    private fun getNoteValue(): String {
        return intent.getStringExtra(KEY_NOTE_VALUE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setObservers()
        Utils.requestKeyboard(etNote, request = true, forced = true)
        if (intent.hasExtra(KEY_NOTE_VALUE)) {
            viewModel.state.noteValue.set(getNoteValue())
            etNote.append(viewModel.state.noteValue.get())
        }
    }

    private fun setObservers() {
        viewModel.clickEvent.observe(this, clickEventObserver)
        viewModel.addEditNoteSuccess.observe(this, Observer {
            when (it) {
                true -> onAddEditNoteSuccess()
            }
        })
    }

    private val clickEventObserver = Observer<Int> {
        when (it) {
            R.id.ivClose -> {
                hideKeyboard()
                finish()
            }
            R.id.tvSave -> {
                viewModel.addEditNote("123456", viewModel.state.noteValue.get()!!)
            }
        }
    }

    private fun onAddEditNoteSuccess() {
        val data = Intent()
        data.putExtra(KEY_NOTE_VALUE, viewModel.state.noteValue.get())
        setResult(INTENT_ADD_NOTE_REQUEST, data)
        finish()
    }
}