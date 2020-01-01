package co.yap.modules.dashboard.store.household.onboarding.interfaces

import android.text.SpannableStringBuilder
import androidx.databinding.ObservableField
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IHouseHoldConfirmPayment {

    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State> {
        var clickEvent: SingleClickEvent
        fun handlePressOnView(id: Int)
    }

    interface State : IBase.State {
        var currencyType:ObservableField<String>
        var selectedCardPlan: ObservableField<String>
        var selectedPlanSaving: ObservableField<String>
        var selectedPlanFee: ObservableField<String>
        var availableBalance: ObservableField<SpannableStringBuilder>
    }
}