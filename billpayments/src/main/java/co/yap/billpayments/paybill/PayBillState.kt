package co.yap.billpayments.paybill

import androidx.databinding.Bindable
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import co.yap.billpayments.BR
import co.yap.yapcore.BaseState
import co.yap.yapcore.helpers.extentions.getValueWithoutComa

class PayBillState : BaseState(), IPayBill.State {
    override var availableBalanceString: ObservableField<CharSequence> = ObservableField("")
    override var noteValue: ObservableField<String> = ObservableField("")
    override var isAutoPaymentOn: ObservableBoolean = ObservableBoolean(false)
    override var isBillReminderOn: ObservableBoolean = ObservableBoolean(false)
    override var autoPaymentScheduleTypeWeek: ObservableBoolean = ObservableBoolean(false)
    override var autoPaymentScheduleTypeMonth: ObservableBoolean = ObservableBoolean(false)
    override var selectedWeekDay: ObservableField<String> = ObservableField("Monday")
    override var selectedMonthDay: ObservableField<String> = ObservableField("1")
    override var autoPaymentScheduleType: ObservableField<String> = ObservableField("")
    override val valid: ObservableBoolean = ObservableBoolean(false)
    override val minLimit: ObservableField<Double> = ObservableField()
    override val maxLimit: ObservableField<Double> = ObservableField()
    override val billReferences: ObservableField<String> = ObservableField()

    @get:Bindable
    override var amount: String = ""
        set(value) {
            field = value.getValueWithoutComa()
            notifyPropertyChanged(BR.amount)
        }
}