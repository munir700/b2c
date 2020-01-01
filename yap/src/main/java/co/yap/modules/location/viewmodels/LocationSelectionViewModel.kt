package co.yap.modules.location.viewmodels

import android.app.Application
import co.yap.modules.location.interfaces.ILocationSelection
import co.yap.modules.location.states.LocationSelectionState
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent

class LocationSelectionViewModel(application: Application) :
    BaseViewModel<ILocationSelection.State>(application),
    ILocationSelection.ViewModel {
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override val state: ILocationSelection.State = LocationSelectionState()

    override fun handleOnPressView(id: Int) {
        clickEvent.setValue(id)
    }
}