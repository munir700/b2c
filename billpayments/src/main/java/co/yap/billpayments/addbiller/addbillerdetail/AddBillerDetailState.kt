package co.yap.billpayments.addbiller.addbillerdetail

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import co.yap.yapcore.BaseState

class AddBillerDetailState : BaseState(), IAddBillerDetail.State {
    override var screenTitle: ObservableField<String> = ObservableField("")
    override var valid: ObservableBoolean = ObservableBoolean()
    override var nickNameValue: ObservableField<String> = ObservableField("")
}
