package co.yap.modules.dashboard.store.household.contact

import androidx.lifecycle.MutableLiveData
import co.yap.networking.customers.requestdtos.HouseholdOnboardRequest
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IHHAddUserContact {
    interface State : IBase.State {
        var phone: MutableLiveData<String>
        var confirmPhone: MutableLiveData<String>
        var countryCode: MutableLiveData<String>
        var isMobileVerified: MutableLiveData<Boolean>?
        var request: MutableLiveData<HouseholdOnboardRequest>?
    }

    interface ViewModel : IBase.ViewModel<State> {
        fun verifyMobileNumber(apiResponse: ((Boolean?) -> Unit?)?)
    }

    interface View : IBase.View<ViewModel>
}
