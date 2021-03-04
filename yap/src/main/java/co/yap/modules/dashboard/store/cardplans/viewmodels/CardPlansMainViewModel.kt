package co.yap.modules.dashboard.store.cardplans.viewmodels

import android.app.Application
import co.yap.modules.dashboard.store.cardplans.interfaces.IMainCardPlans
import co.yap.modules.dashboard.store.cardplans.states.CardPlansMainState
import co.yap.yapcore.BaseViewModel

class CardPlansMainViewModel(application: Application) :
    BaseViewModel<IMainCardPlans.State>(application), IMainCardPlans.ViewModel {
    override val state: IMainCardPlans.State = CardPlansMainState()
}