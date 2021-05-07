package co.yap.billpayments.payall.payallsuccess

import androidx.databinding.ObservableField
import co.yap.yapcore.BaseState

class PayAllSuccessState : BaseState(), IPayAllSuccess.State {
    override var paidAmount: ObservableField<String> = ObservableField("")
    override var inputFieldString: ObservableField<String> = ObservableField("")
}
