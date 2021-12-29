package co.yap.billpayments.payall.main

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import co.yap.yapcore.BaseState

class PayAllMainState : BaseState(), IPayAllMain.State {
    override val toolbarTitleString: ObservableField<String> = ObservableField("")
    override val rightIconVisibility: ObservableBoolean = ObservableBoolean(false)
}
