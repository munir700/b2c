package co.yap.billpayments.mybills

import androidx.databinding.ObservableField
import co.yap.yapcore.BaseState

class MyBillsState : BaseState(), IMyBills.State {
    override var screenTitle: ObservableField<String> = ObservableField("")
}
