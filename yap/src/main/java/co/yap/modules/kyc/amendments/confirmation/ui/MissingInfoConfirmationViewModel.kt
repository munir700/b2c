package co.yap.modules.kyc.amendments.confirmation.ui

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.modules.kyc.amendments.confirmation.interfaces.IMissingInfoConfirmation
import co.yap.modules.kyc.amendments.confirmation.states.MissingInfoConfirmationState
import co.yap.yapcore.BaseViewModel

class MissingInfoConfirmationViewModel(application: Application) :
    BaseViewModel<IMissingInfoConfirmation.State>(application), IMissingInfoConfirmation.ViewModel {

    override val state: IMissingInfoConfirmation.State = MissingInfoConfirmationState()

    override val onClickEvent: MutableLiveData<Int> = MutableLiveData()

    override fun handlePressView(id: Int) {
        onClickEvent.value = id
    }
}