package co.yap.billpayments.paybill

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import co.yap.yapcore.BaseState

class PayBillState : BaseState(), IPayBill.State {
    override var availableBalanceString: ObservableField<CharSequence> = ObservableField("")
    override var noteValue: ObservableField<String> = ObservableField("")
    override var isAutoPaymentOn: ObservableBoolean? = ObservableBoolean(false)
    override var isBillReminderOn: Boolean? = false
}