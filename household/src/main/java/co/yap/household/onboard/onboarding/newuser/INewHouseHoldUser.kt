package co.yap.household.onboard.onboarding.newuser

import androidx.lifecycle.MutableLiveData
import co.yap.networking.customers.responsedtos.AccountInfo
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface INewHouseHoldUser {

    interface View: IBase.View<ViewModel> {

    }

    interface ViewModel: IBase.ViewModel<State> {
        var clickEvent: SingleClickEvent
        fun handlePressOnView(id: Int)
    }

    interface State: IBase.State {
        var firstName: MutableLiveData<String>
        var lastName: MutableLiveData<String>
        var accountInfo: MutableLiveData<AccountInfo>
    }
}