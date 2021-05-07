package co.yap.billpayments.paybill.paybillsuccess

import androidx.databinding.ObservableField
import co.yap.yapcore.BaseState

class PayBillSuccessState : BaseState(), IPayBillSuccess.State {
    override var paidAmount: ObservableField<String> = ObservableField("")
    override var inputFieldString: ObservableField<String> = ObservableField("")
}
