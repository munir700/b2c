package co.yap.modules.dashboard.cards.home.viewmodels

import android.app.Application
import co.yap.modules.dashboard.cards.home.interfaces.IYapCards
import co.yap.modules.dashboard.states.YapCardsState
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent

class YapCardsViewModel(application: Application) : BaseViewModel<IYapCards.State>(application),
    IYapCards.ViewModel {

    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val state: YapCardsState = YapCardsState()

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }
}