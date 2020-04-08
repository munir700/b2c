package co.yap.modules.subaccounts.paysalary.future

import androidx.databinding.ObservableField
import co.yap.yapcore.BaseState

class FuturePaymentState:BaseState(),IFuturePayment.State {
    override var date: ObservableField<String> = ObservableField("")
}