package co.yap.household.onboarding.onboardemail

import androidx.lifecycle.MutableLiveData
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IHHOnBoardingEmail {
    interface View : IBase.View<ViewModel> {

    }

    interface ViewModel : IBase.ViewModel<State> {
        val clickEvent: SingleClickEvent
        fun handlePressOnClick(id: Int)
        fun verifyHouseholdEmail(apiResponse: ((Boolean) -> Unit?)?)
    }

    interface State : IBase.State {
        var email: MutableLiveData<String>
    }
}
