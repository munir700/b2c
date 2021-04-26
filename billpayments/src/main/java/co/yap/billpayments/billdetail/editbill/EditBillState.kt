package co.yap.billpayments.billdetail.editbill

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import co.yap.yapcore.BaseState

class EditBillState : BaseState(), IEditBill.State {
    override var screenTitle: ObservableField<String> = ObservableField("")
    override var valid: ObservableBoolean = ObservableBoolean(false)
    override var isAutoPaymentOn: ObservableBoolean = ObservableBoolean(false)
    override var isBillReminderOn: ObservableBoolean = ObservableBoolean(false)
    override var autoPaymentScheduleTypeWeek: ObservableBoolean = ObservableBoolean(false)
    override var autoPaymentScheduleTypeMonth: ObservableBoolean = ObservableBoolean(false)
    override var selectedWeekDay: ObservableField<String> = ObservableField("Monday")
    override var selectedMonthDay: ObservableField<String> = ObservableField("1")
    override var autoPaymentScheduleType: ObservableField<String> = ObservableField("")
}
