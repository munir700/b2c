package co.yap.billpayments.addBill.main

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import co.yap.yapcore.BaseState

class AddBillState : BaseState(), IAddBill.State {
    override var toolbarVisibility: ObservableBoolean = ObservableBoolean(true)
    override var rightIconVisibility: ObservableBoolean = ObservableBoolean(false)
    override var toolbarTitleString: ObservableField<String> = ObservableField("")
}
