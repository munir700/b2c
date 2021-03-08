package co.yap.modules.dashboard.store.cardplans.viewmodels

import android.app.Application
import co.yap.modules.dashboard.store.cardplans.interfaces.ICardViewer

class CardPlanViewerViewModel(application: Application) :
    CardPlansBaseViewModel<ICardViewer.State>(application), ICardViewer.ViewModel {
    override val state: ICardViewer.State
        get() = TODO("Not yet implemented")
}