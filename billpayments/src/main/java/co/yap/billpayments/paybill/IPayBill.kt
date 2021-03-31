package co.yap.billpayments.paybill

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import co.yap.billpayments.databinding.FragmentPayBillBinding
import co.yap.widgets.bottomsheet.CoreBottomSheetData
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IPayBill {
    interface State : IBase.State {
        var availableBalanceString: ObservableField<CharSequence>
        var noteValue: ObservableField<String>
        var isAutoPaymentOn: ObservableBoolean?
        var isBillReminderOn: Boolean
        var selectedWeekDay: ObservableField<String>
        var selectedMonthDay: ObservableField<String>
        var autoPaymentScheduleType: ObservableField<String>
        var autoPaymentScheduleTypeWeek: ObservableBoolean
        var autoPaymentScheduleTypeMonth: ObservableBoolean
    }

    interface ViewModel : IBase.ViewModel<State> {
        var clickEvent: SingleClickEvent
        fun handlePressView(id: Int)
    }

    interface View : IBase.View<ViewModel> {
        fun setObservers()
        fun removeObservers()
        fun getViewBinding(): FragmentPayBillBinding
        fun composeWeekDaysList(): MutableList<CoreBottomSheetData>
        val weekDaysList: List<String>
            get() = listOf<String>("Monday",
                "Tuesday",
                "Wednesday",
                "Thursday",
                "Friday",
                "Saturday",
                "Sunday")
    }
}