package co.yap.billpayments.billerlist

import androidx.databinding.ObservableField
import co.yap.yapcore.BaseState

class BillersState : BaseState(), IBillers.State {
    override var screenTitle: ObservableField<String> = ObservableField()
}
