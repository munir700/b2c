package co.yap.billpayments.paybill.main

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import co.yap.yapcore.BaseState

class PayBillMainState : BaseState(), IPayBillMain.State {
    override val toolbarTitleString: ObservableField<String> = ObservableField("")
    override val rightIconVisibility: ObservableBoolean = ObservableBoolean(false)
    override var selectedBillPosition: ObservableInt = ObservableInt(0)
    override var paidAmount: ObservableField<String> = ObservableField("")
}