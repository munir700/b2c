package co.yap.billpayments.billerdetail

import androidx.databinding.ObservableField
import co.yap.yapcore.BaseState

class BillerDetailState : BaseState(), IBillerDetail.State {
    override var screenTitle: ObservableField<String> = ObservableField("")
}
