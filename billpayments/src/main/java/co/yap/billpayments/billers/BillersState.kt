package co.yap.billpayments.billers

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import co.yap.yapcore.BaseState

class BillersState : BaseState(), IBillers.State {
    override var screenTitle: ObservableField<String> = ObservableField()
    override var searchEnabled: ObservableBoolean = ObservableBoolean(false)
    override var nextButtonEnabled: ObservableBoolean = ObservableBoolean(false)
}
