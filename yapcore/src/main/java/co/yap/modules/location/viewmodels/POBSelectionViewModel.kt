package co.yap.modules.location.viewmodels

import android.app.Application
import co.yap.modules.location.interfaces.IPOBSelection
import co.yap.modules.location.states.POBSelectionState
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent

class POBSelectionViewModel(application: Application) :
    BaseViewModel<IPOBSelection.State>(application),
    IPOBSelection.ViewModel {
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override val state: IPOBSelection.State = POBSelectionState()
    override fun handleOnPressView(id: Int) {
        clickEvent.setValue(id)
    }

}