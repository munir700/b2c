package co.yap.modules.dashboard.store.household.onboarding.states

import androidx.databinding.ObservableField
import co.yap.modules.dashboard.store.household.onboarding.interfaces.IHouseHoldConfirmPayment
import co.yap.yapcore.BaseState

class HouseHoldConfirmPaymentState : BaseState(), IHouseHoldConfirmPayment.State {
    override var selectedCardPlan: ObservableField<String> = ObservableField("")
    override var selectedPlanSaving: ObservableField<String> = ObservableField("")
    override var selectedPlanFee: ObservableField<String> = ObservableField("")
    override var availableBalance: ObservableField<String> = ObservableField("")
}