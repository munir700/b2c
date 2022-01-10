package co.yap.billpayments.billdetail

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import co.yap.yapcore.BaseState

class BillDetailState : BaseState(), IBillDetail.State {
    override var toolbarVisibility: ObservableBoolean = ObservableBoolean(true)
    override var rightIconVisibility: ObservableBoolean = ObservableBoolean(false)
    override var enableRightIcon: ObservableBoolean = ObservableBoolean(true)
    override var leftIconVisibility: ObservableBoolean = ObservableBoolean(true)
    override var toolbarTitleString: ObservableField<String> = ObservableField("")
}
