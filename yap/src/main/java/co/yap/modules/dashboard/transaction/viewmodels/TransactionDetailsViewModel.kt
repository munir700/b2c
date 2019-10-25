package co.yap.modules.dashboard.transaction.viewmodels

import android.app.Application
import co.yap.modules.dashboard.transaction.interfaces.ITransactionDetails
import co.yap.modules.dashboard.transaction.states.TransactionDetailsState
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent


class TransactionDetailsViewModel(application: Application) :
    BaseViewModel<ITransactionDetails.State>(application), ITransactionDetails.ViewModel {
    override val state: TransactionDetailsState = TransactionDetailsState()
    override var clickEvent: SingleClickEvent = SingleClickEvent()

    override fun onCreate() {
        super.onCreate()
        state.toolBarTitle="Jan 29, 2019・10:35am"
    }

    override fun handlePressOnBackButton(id: Int) {
        clickEvent.postValue(id)
    }

    override fun handlePressOnShareButton(id: Int) {
        clickEvent.postValue(id)
    }
}