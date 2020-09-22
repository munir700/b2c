package co.yap.household.onboarding.passcode

import androidx.lifecycle.MutableLiveData
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IHHOnBoardingPassCode {
    interface View : IBase.View<ViewModel> {
    }

    interface ViewModel : IBase.ViewModel<State> {
        fun createPassCodeRequest(apiResponse: ((Boolean?) -> Unit?)?)
    }

    interface State : IBase.State {
        var passCode: MutableLiveData<String>
    }
}