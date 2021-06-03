package co.yap.billpayments.payall.home

import androidx.databinding.ObservableField
import co.yap.yapcore.BaseState

class PayAllState : BaseState(), IPayAll.State {
    override var totalAmount: ObservableField<String> = ObservableField("")
    override var availableBalanceString: ObservableField<CharSequence> = ObservableField("")
}
