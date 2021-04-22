package co.yap.modules.dashboard.transaction.feedback

import android.app.Application
import co.yap.modules.dashboard.transaction.feedback.adaptor.TransactionFeedbackAdapter
import co.yap.modules.dashboard.transaction.feedback.models.ItemFeedback
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent

class TransactionFeedbackViewModel(application: Application) :
    BaseViewModel<ITransactionFeedback.State>(application), ITransactionFeedback.ViewModel {

    override val adapter: TransactionFeedbackAdapter = TransactionFeedbackAdapter(mutableListOf())

    override var clickEvent: SingleClickEvent = SingleClickEvent()

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

    override val state: TransactionFeedbackState = TransactionFeedbackState()

    override fun onCreate() {
        super.onCreate()
        adapter.setList(getImprovementComponent())
    }

    private fun getImprovementComponent(): MutableList<ItemFeedback> {
        val list = mutableListOf<ItemFeedback>()
        list.add(ItemFeedback(label = "Logo"))
        list.add(ItemFeedback(label = "Location",isCheck = true))
        list.add(ItemFeedback(label = "Name of merchant"))
        list.add(ItemFeedback(label = "Category"))
        return list
    }
}