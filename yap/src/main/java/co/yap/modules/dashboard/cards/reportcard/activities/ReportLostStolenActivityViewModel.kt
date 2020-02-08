package co.yap.modules.dashboard.cards.reportcard.activities

import android.app.Application
import co.yap.modules.dashboard.cards.reportcard.interfaces.IReportStolenActivity
import co.yap.modules.dashboard.cards.reportcard.states.ReportStolenActivityState
import co.yap.networking.cards.responsedtos.Card
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.transactions.TransactionsRepository
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleLiveEvent

class ReportLostStolenActivityViewModel(application: Application) :
    BaseViewModel<IReportStolenActivity.State>(application),
    IReportStolenActivity.ViewModel, IRepositoryHolder<TransactionsRepository> {

    override val repository: TransactionsRepository = TransactionsRepository
    override val backButtonPressEvent: SingleLiveEvent<Boolean> = SingleLiveEvent()
    override val state: ReportStolenActivityState = ReportStolenActivityState()
    override var card: Card? = null

    override fun handlePressOnBackButton() {
        backButtonPressEvent.value = true
    }

    override fun handlePressOnTickButton() {

    }
}