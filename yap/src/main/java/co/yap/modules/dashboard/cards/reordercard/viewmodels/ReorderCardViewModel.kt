package co.yap.modules.dashboard.cards.reordercard.viewmodels

import android.app.Application
import co.yap.modules.dashboard.cards.reordercard.interfaces.IReorderCard
import co.yap.modules.dashboard.cards.reordercard.states.ReorderCardState
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent

class ReorderCardViewModel(application: Application) :
    BaseViewModel<IReorderCard.State>(application),
    IReorderCard.ViewModel {
    override val state: IReorderCard.State = ReorderCardState()
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

    override fun onResume() {
        super.onResume()
        state.toolbarVisibility.set(true)
    }
}
