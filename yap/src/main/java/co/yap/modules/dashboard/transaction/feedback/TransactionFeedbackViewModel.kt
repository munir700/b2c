package co.yap.modules.dashboard.transaction.feedback

import android.app.Application
import co.yap.yapcore.BaseViewModel

class TransactionFeedbackViewModel (application: Application) :
    BaseViewModel<ITransactionFeedback.State>(application),ITransactionFeedback.ViewModel{
    override val state: ITransactionFeedback.State = TransactionFeedbackState()
}