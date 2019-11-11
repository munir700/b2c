package co.yap.modules.others.note.states

import androidx.databinding.ObservableField
import co.yap.modules.others.note.interfaces.ITransactionNote
import co.yap.yapcore.BaseState

class TransactionNoteState : BaseState(), ITransactionNote.State {
    override var tvEnableState: ObservableField<Boolean> = ObservableField()
    override var noteValue: ObservableField<String> = ObservableField("")
    override var addEditNote: ObservableField<String> = ObservableField()
}