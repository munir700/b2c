package co.yap.billpayments.payall.home

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import co.yap.billpayments.payall.payallsuccess.adapter.PaidBill
import co.yap.yapcore.BaseState

class PayAllState : BaseState(), IPayAll.State {
    override var totalAmount: ObservableField<String> = ObservableField("")
    override var availableBalanceString: ObservableField<CharSequence> = ObservableField("")
    override var allBillsToBePaid: List<PaidBill>? = null
    override val valid: ObservableBoolean = ObservableBoolean()
}
