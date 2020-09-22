package co.yap.household.onboarding.onboardmobile

import androidx.lifecycle.MutableLiveData
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IHHOnBoardingMobile {
    interface View : IBase.View<ViewModel> {
    }
    interface ViewModel : IBase.ViewModel<State> {
        fun verifyHouseholdParentMobile(apiResponse: ((String?) -> Unit?)?)
    }
    interface State : IBase.State {
        var phone: MutableLiveData<String>?
        var countryCode: MutableLiveData<String>?
    }
}
