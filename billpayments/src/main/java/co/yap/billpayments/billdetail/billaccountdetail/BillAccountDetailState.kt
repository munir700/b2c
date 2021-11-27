package co.yap.billpayments.billdetail.billaccountdetail

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import co.yap.yapcore.BaseState

class BillAccountDetailState : BaseState(), IBillAccountDetail.State {
    override var screenTitle: ObservableField<String> = ObservableField("")
    override var dueAmount: CharSequence = toString()
    override var billStatus: ObservableField<String> = ObservableField("")
    override var isBillsPaidYet: ObservableBoolean = ObservableBoolean(false)
}
