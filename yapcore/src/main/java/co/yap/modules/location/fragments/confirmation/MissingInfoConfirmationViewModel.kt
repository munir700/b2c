package co.yap.modules.location.fragments.confirmation

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.yapcore.BaseViewModel

class MissingInfoConfirmationViewModel(application: Application) :
    BaseViewModel<IMissingInfoConfirmation.State>(application), IMissingInfoConfirmation.ViewModel {

    override val state: IMissingInfoConfirmation.State = MissingInfoConfirmationState()

    override val onClickEvent: MutableLiveData<Int> = MutableLiveData()

    override fun handlePressView(id: Int) {
        onClickEvent.value = id
    }
}