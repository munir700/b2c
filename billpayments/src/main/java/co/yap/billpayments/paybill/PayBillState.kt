package co.yap.billpayments.paybill

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import co.yap.yapcore.BaseState

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
}