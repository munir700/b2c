package co.yap.billpayments.mybills

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import co.yap.yapcore.BaseState

class MyBillsState : BaseState(), IMyBills.State {
    override var screenTitle: ObservableField<String> = ObservableField("")
    override var totalBillAmount: Double = 0.0
    override var valid: ObservableBoolean = ObservableBoolean(false)
    override var buttonText: ObservableField<String> = ObservableField("")
}
