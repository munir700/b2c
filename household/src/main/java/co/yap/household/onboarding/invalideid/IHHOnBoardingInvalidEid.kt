package co.yap.household.onboarding.invalideid

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IHHOnBoardingInvalidEid {
    interface View : IBase.View<ViewModel> {
    }

    interface ViewModel : IBase.ViewModel<State> {
        val clickEvent: SingleClickEvent
        fun handlePressOnClick(id: Int)
        fun getHelpDeskPhone()
    }

    interface State : IBase.State {
        var contactPhone: MutableLiveData<String>
    }
}
