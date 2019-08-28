package co.yap.modules.dashboard.viewmodels

import android.app.Application
import co.yap.modules.dashboard.interfaces.IYapCards
import co.yap.modules.dashboard.states.YapCardsState
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent

class YapCardsViewModel(application: Application) : BaseViewModel<IYapCards.State>(application),
    IYapCards.ViewModel {

    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val state: YapCardsState = YapCardsState()

}