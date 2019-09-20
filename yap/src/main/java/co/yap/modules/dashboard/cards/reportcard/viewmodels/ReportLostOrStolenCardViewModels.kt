package co.yap.modules.dashboard.cards.reportcard.viewmodels

import android.app.Application
import co.yap.modules.dashboard.cards.reportcard.interfaces.IRepostOrStolenCard
import co.yap.modules.dashboard.cards.reportcard.states.ReportOrStolenCardState
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleLiveEvent

class ReportLostOrStolenCardViewModels(application: Application) :
    BaseViewModel<IRepostOrStolenCard.State>(application),
    IRepostOrStolenCard.ViewModel {

    override val backButtonPressEvent: SingleLiveEvent<Boolean> = SingleLiveEvent()
    override val state: ReportOrStolenCardState =
        ReportOrStolenCardState()

    override fun handlePressOnBackButton() {
        backButtonPressEvent.value = true
    }
}