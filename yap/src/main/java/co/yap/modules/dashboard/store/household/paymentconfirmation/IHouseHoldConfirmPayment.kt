package co.yap.modules.dashboard.store.household.paymentconfirmation

import android.text.SpannableStringBuilder
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.networking.customers.CustomersApi
import co.yap.networking.customers.requestdtos.HouseholdOnboardRequest
import co.yap.networking.household.responsedtos.HouseHoldPlan
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IHouseHoldConfirmPayment {

    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State> {
        var repository: CustomersApi
        fun addHouseholdUser(apiResponse: ((Boolean?) -> Unit?)?)
    }

    interface State : IBase.State {
        var onBoardRequest: MutableLiveData<HouseholdOnboardRequest>?
        var plansList: MutableLiveData<ArrayList<HouseHoldPlan>>?
        var selectedPlan: MutableLiveData<HouseHoldPlan>?
        var availableBalance: MutableLiveData<String>?
    }
}
