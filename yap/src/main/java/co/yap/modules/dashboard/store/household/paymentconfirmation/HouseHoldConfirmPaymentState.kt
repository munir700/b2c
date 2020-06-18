package co.yap.modules.dashboard.store.household.paymentconfirmation

import android.text.SpannableStringBuilder
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.modules.dashboard.store.household.paymentconfirmation.IHouseHoldConfirmPayment
import co.yap.networking.customers.requestdtos.HouseholdOnboardRequest
import co.yap.networking.household.responsedtos.HouseHoldPlan
import co.yap.yapcore.BaseState

class HouseHoldConfirmPaymentState : BaseState(), IHouseHoldConfirmPayment.State {
    override var onBoardRequest: MutableLiveData<HouseholdOnboardRequest>? = MutableLiveData()
    override var plansList: MutableLiveData<ArrayList<HouseHoldPlan>>? = MutableLiveData()
    override var selectedPlan: MutableLiveData<HouseHoldPlan>? = MutableLiveData()
}
