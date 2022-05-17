package co.yap.modules.dashboard.store.household.paymentconfirmation

import androidx.lifecycle.MutableLiveData
import co.yap.networking.customers.requestdtos.HouseholdOnboardRequest
import co.yap.networking.household.responsedtos.HouseHoldPlan
import co.yap.yapcore.BaseState
import co.yap.yapcore.managers.SessionManager
import javax.inject.Inject

class HouseHoldConfirmPaymentState @Inject constructor(): BaseState(), IHouseHoldConfirmPayment.State {
    override var onBoardRequest: MutableLiveData<HouseholdOnboardRequest>? = MutableLiveData()
    override var plansList: MutableLiveData<ArrayList<HouseHoldPlan>>? = MutableLiveData()
    override var selectedPlan: MutableLiveData<HouseHoldPlan>? = MutableLiveData()
    override var availableBalance: MutableLiveData<String>? =
        MutableLiveData(SessionManager.cardBalance.value?.availableBalance ?: "")
}
