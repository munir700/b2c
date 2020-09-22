package co.yap.household.onboarding.onboardemail

import androidx.lifecycle.MutableLiveData
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IHHOnBoardingEmail {
    interface View : IBase.View<ViewModel> {

    }

    interface ViewModel : IBase.ViewModel<State> {
        fun verifyHouseholdEmail(apiResponse: ((Boolean) -> Unit?)?)
    }

    interface State : IBase.State {
        var email: MutableLiveData<String>
    }
}
