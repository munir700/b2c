package co.yap.billpayments.paybills

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import co.yap.yapcore.BaseState

class PayBillsState : BaseState(), IPayBills.State {
    override var showBillCategory: ObservableBoolean = ObservableBoolean(true)
    override var totalDueAmount: ObservableField<String> = ObservableField("")
}
