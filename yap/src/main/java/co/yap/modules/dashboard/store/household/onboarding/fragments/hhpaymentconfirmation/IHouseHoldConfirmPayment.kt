package co.yap.modules.dashboard.store.household.onboarding.fragments.hhpaymentconfirmation

import android.text.SpannableStringBuilder
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.networking.customers.CustomersApi
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.household.responsedtos.HouseHoldPlan
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IHouseHoldConfirmPayment {

    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State> {
        var clickEvent: SingleClickEvent
        var repository: CustomersApi
        var onBoardUserSuccess: MutableLiveData<Boolean>
        var plansList: MutableLiveData<ArrayList<HouseHoldPlan>>
        fun handlePressOnView(id: Int)
        var selectedPlanType: HouseHoldPlan?
        fun addHouseholdUser()
        var firstName:String
        var lastName:String
        var username: String
        var userMobileNo: String
        var countryCode: String
        var tempPasscode: String
    }

    interface State : IBase.State {
        var currencyType: ObservableField<String>
        var selectedCardPlan: ObservableField<String>
        var selectedPlanSaving: ObservableField<String>
        var selectedPlanFee: ObservableField<String>
        var availableBalance: ObservableField<SpannableStringBuilder>
    }
}
