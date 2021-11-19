package co.yap.billpayments.payall.singledecline

import androidx.databinding.ObservableField
import co.yap.yapcore.BaseState

class SingleDeclineState : BaseState(), ISingleDecline.State {
    override var paidAmount: ObservableField<String> = ObservableField("")
}
