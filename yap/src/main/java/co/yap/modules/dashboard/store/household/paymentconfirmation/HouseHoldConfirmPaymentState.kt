package co.yap.modules.dashboard.store.household.paymentconfirmation

import android.text.SpannableStringBuilder
import androidx.databinding.ObservableField
import co.yap.modules.dashboard.store.household.paymentconfirmation.IHouseHoldConfirmPayment
import co.yap.yapcore.BaseState

class HouseHoldConfirmPaymentState : BaseState(), IHouseHoldConfirmPayment.State {
    override var selectedCardPlan: ObservableField<String> = ObservableField("")
    override var selectedPlanSaving: ObservableField<String> = ObservableField("")
    override var selectedPlanFee: ObservableField<String> = ObservableField("")
    override var availableBalance: ObservableField<SpannableStringBuilder> = ObservableField(
        SpannableStringBuilder.valueOf("")
    )
    override var currencyType: ObservableField<String> = ObservableField("AED")
}
