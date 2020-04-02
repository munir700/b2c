package co.yap.modules.subaccounts.paysalary.recurringpayment

import androidx.databinding.ObservableField
import co.yap.yapcore.BaseState

class RecurringPaymentState : BaseState(), IRecurringPayment.State {
    override var date: ObservableField<String> = ObservableField("")
}