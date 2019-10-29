package co.yap.modules.transaction_note.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.modules.transaction_note.interfaces.ITransactionNote
import co.yap.modules.transaction_note.states.TransactionNoteState
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.transactions.TransactionsRepository
import co.yap.networking.transactions.requestdtos.AddEditNoteRequest
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent

class TransactionNoteViewModel(application: Application) :
    BaseViewModel<ITransactionNote.State>(application), ITransactionNote.ViewModel,
    IRepositoryHolder<TransactionsRepository> {

    override val addEditNoteSuccess: MutableLiveData<Boolean> = MutableLiveData()
    override val repository: TransactionsRepository = TransactionsRepository

    override val state: ITransactionNote.State = TransactionNoteState()

    override val clickEvent: SingleClickEvent = SingleClickEvent()

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

    override fun handlePressOnCrossButton(id: Int) {
        clickEvent.setValue(id)
    }

    override fun addEditNote(transactionId: String, transactionDetail: String) {
        launch {
            state.loading = true
            when (val response =
                repository.addEditNote(AddEditNoteRequest(transactionId, transactionDetail))) {
                is RetroApiResponse.Success -> addEditNoteSuccess.value = true
                is RetroApiResponse.Error -> state.toast = response.error.message
            }
            state.loading = false
        }
    }
}