package co.yap.billpayments.paybill

import androidx.databinding.ObservableField
import co.yap.yapcore.BaseState

class PayBillState : BaseState(), IPayBill.State {
    override var availableBalanceString: ObservableField<CharSequence> = ObservableField("")
    override var noteValue: ObservableField<String> = ObservableField("")
    override var isAutoPaymentOn: Boolean? = false
    override var isBillReminderOn: Boolean? = false
}