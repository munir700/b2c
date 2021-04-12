package co.yap.billpayments.addBill.billers

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import co.yap.yapcore.BaseState

class BillersState : BaseState(), IBillers.State {
    override var screenTitle: ObservableField<String> = ObservableField()
    override var showSearchView: ObservableBoolean = ObservableBoolean(false)
    override var valid: ObservableBoolean = ObservableBoolean(false)
}
