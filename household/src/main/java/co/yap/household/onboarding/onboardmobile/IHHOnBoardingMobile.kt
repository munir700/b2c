package co.yap.household.onboarding.onboardmobile

import androidx.lifecycle.MutableLiveData
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IHHOnBoardingMobile {
    interface View : IBase.View<ViewModel> {
    }
    interface ViewModel : IBase.ViewModel<State> {
        val clickEvent: SingleClickEvent
        fun handlePressOnClick(id: Int)
    }
    interface State : IBase.State {
        var phone: MutableLiveData<String>
    }
}
