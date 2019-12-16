package co.yap.modules.dashboard.store.household.onboarding.interfaces

import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.SingleLiveEvent

interface IHouseHoldUserContact {

    interface State : IBase.State {
        var mobileNumber: String
        var confirmMobileNumber: String
         var valid: Boolean


    }

    interface ViewModel : IBase.ViewModel<State> {

        val clickEvent: SingleClickEvent
        fun handlePressOnAdd(id: Int)
        fun handlePressOnBackButton()
//        val backButtonPressEvent: SingleLiveEvent<Boolean>
    }

    interface View : IBase.View<ViewModel>

}