package co.yap.billpayments.accountdetails

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import co.yap.yapcore.BaseState

class BillAccountDetailsState : BaseState(), IBillAccountDetails.State {
    override var screenTitle: ObservableField<String> = ObservableField("")
    override var valid: ObservableBoolean = ObservableBoolean()
}
