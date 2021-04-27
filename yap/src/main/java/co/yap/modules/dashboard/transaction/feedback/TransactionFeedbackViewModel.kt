package co.yap.modules.dashboard.transaction.feedback

import android.app.Application
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.modules.dashboard.transaction.feedback.adaptor.TransactionFeedbackAdapter
import co.yap.modules.dashboard.transaction.feedback.models.ItemFeedback
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent

class TransactionFeedbackViewModel(application: Application) :
    BaseViewModel<ITransactionFeedback.State>(application), ITransactionFeedback.ViewModel {

    override var clickEvent: SingleClickEvent = SingleClickEvent()

    override var feedbackSelected: ObservableBoolean = ObservableBoolean(false)
    override val adapter: TransactionFeedbackAdapter = TransactionFeedbackAdapter(mutableListOf())

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

    override val state: TransactionFeedbackState = TransactionFeedbackState()

    override val location: ObservableField<String>  = ObservableField()
    override val title: ObservableField<String> = ObservableField()

    override fun onCreate() {
        super.onCreate()
        adapter.setList(getImprovementComponent())
    }

    private fun getImprovementComponent(): MutableList<ItemFeedback> {
        val list = mutableListOf<ItemFeedback>()
        list.add(ItemFeedback(label = "Logo"))
        list.add(ItemFeedback(label = "Location"))
        list.add(ItemFeedback(label = "Name of merchant"))
        list.add(ItemFeedback(label = "Category"))
        return list
    }

    override fun selectFeedback(pos: Int) {
        adapter.getDataList()[pos].isCheck =
            !adapter.getDataList()[pos].isCheck
        adapter.notifyItemChanged(pos)
        val list = adapter.getDataList().filter {
            it.isCheck
        }
        feedbackSelected.set(!list.isNullOrEmpty())
    }
}
