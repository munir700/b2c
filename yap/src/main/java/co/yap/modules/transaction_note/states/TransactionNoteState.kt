package co.yap.modules.transaction_note.states

import androidx.databinding.ObservableField
import co.yap.modules.transaction_note.interfaces.ITransactionNote
import co.yap.yapcore.BaseState

class TransactionNoteState : BaseState(), ITransactionNote.State {
    override var tvEnableState: ObservableField<Boolean> = ObservableField()
    override var noteValue: ObservableField<String> = ObservableField("")
    override var addEditNote: ObservableField<String> = ObservableField()
}