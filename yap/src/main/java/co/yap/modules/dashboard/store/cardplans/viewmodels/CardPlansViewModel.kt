package co.yap.modules.dashboard.store.cardplans.viewmodels

import android.app.Application
import co.yap.modules.dashboard.store.cardplans.interfaces.ICardPlans

class CardPlansViewModel(application: Application) :
    CardPlansBaseViewModel<ICardPlans.State>(application), ICardPlans.ViewModel {
    override val state: ICardPlans.State
        get() = TODO("Not yet implemented")
}