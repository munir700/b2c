package co.yap.billpayments.payall.payallsuccess

import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import co.yap.yapcore.BaseState

class PayAllSuccessState : BaseState(), IPayAllSuccess.State {
    override var paidAmount: ObservableField<String> = ObservableField("")
    override var screenTitle: ObservableField<String> = ObservableField("")
    override var billsPaid: ObservableInt = ObservableInt(-1)
}
