package co.yap.modules.dashboard.transaction.feedback

import android.app.Application
import co.yap.modules.dashboard.transaction.feedback.adaptor.TransactionFeedbackAdapter
import co.yap.modules.dashboard.transaction.feedback.models.ItemTransactionFeedback
import co.yap.yapcore.BaseViewModel
import com.facebook.internal.Mutable

class TransactionFeedbackViewModel(application: Application) :
    BaseViewModel<ITransactionFeedback.State>(application), ITransactionFeedback.ViewModel {

    override val adapter: TransactionFeedbackAdapter =
        TransactionFeedbackAdapter(arrayListOf())

    override val state: TransactionFeedbackState = TransactionFeedbackState()

    override fun onCreate() {
        super.onCreate()
        adapter.setList(getImprovementComponent())
    }

    private fun getImprovementComponent(): MutableList<ItemTransactionFeedback> {
        val list = mutableListOf<ItemTransactionFeedback>()
        list.add(ItemTransactionFeedback("Logo",false))
        list.add(ItemTransactionFeedback("Location",true))
        list.add(ItemTransactionFeedback("Name of merchant",false))
        list.add(ItemTransactionFeedback("Category",false))
        return list
    }
}