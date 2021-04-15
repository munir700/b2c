package co.yap.billpayments.dashboard.billaccountdetail

import androidx.databinding.ObservableField
import co.yap.yapcore.BaseState

class BillAccountDetailState : BaseState(), IBillAccountDetail.State {
    override var screenTitle: ObservableField<String> = ObservableField("")
    override var dueAmount: CharSequence = toString()
}
