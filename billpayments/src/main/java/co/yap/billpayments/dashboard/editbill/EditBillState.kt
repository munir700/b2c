package co.yap.billpayments.dashboard.editbill

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import co.yap.yapcore.BaseState

class EditBillState : BaseState(), IEditBill.State {
    override var screenTitle: ObservableField<String> = ObservableField("")
    override var valid: ObservableBoolean = ObservableBoolean(false)
    override var billPosition: ObservableInt = ObservableInt(0)
}
