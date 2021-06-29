package co.yap.billpayments.paybill.prepaid

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import co.yap.billpayments.databinding.FragmentPrepaidPayBillBinding
import co.yap.billpayments.paybill.enum.PaymentScheduleType
import co.yap.billpayments.paybill.prepaid.skuadapter.SkuAdapter
import co.yap.networking.coreitems.CoreBottomSheetData
import co.yap.networking.customers.responsedtos.billpayment.SkuCatalogs
import co.yap.networking.customers.responsedtos.billpayment.ViewBillModel
import co.yap.networking.transactions.requestdtos.PayBillRequest
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IPrepaidPayBill {
    interface State : IBase.State {
        var availableBalanceString: ObservableField<CharSequence>
        var noteValue: ObservableField<String>
        var isAutoPaymentOn: ObservableBoolean
        var isBillReminderOn: ObservableBoolean
        var selectedWeekDay: ObservableField<String>
        var selectedMonthDay: ObservableField<String>
        var autoPaymentScheduleType: ObservableField<String>
        var autoPaymentScheduleTypeWeek: ObservableBoolean
        var autoPaymentScheduleTypeMonth: ObservableBoolean
        val valid: ObservableBoolean
        var amount: String
        val minLimit: ObservableField<Double>
        val maxLimit: ObservableField<Double>
        val billReferences: ObservableField<String>
    }

    interface ViewModel : IBase.ViewModel<State> {
        var clickEvent: SingleClickEvent
        var adapter: SkuAdapter
        var selectedSku: SkuCatalogs?
        fun handlePressView(id: Int)
        fun updateAutoPaySelection(
            isWeek: Boolean,
            isMonth: Boolean,
            paymentScheduleType: PaymentScheduleType
        )

        fun composeWeekDaysList(listData: List<String>): MutableList<CoreBottomSheetData>
        fun setMinMaxLimitForPrepaid(viewBillModel: ViewBillModel)
        fun payBill(payBillRequest: PayBillRequest, success: () -> Unit)
        fun getPayBillRequest(billModel: ViewBillModel?, billAmount: String): PayBillRequest
    }

    interface View : IBase.View<ViewModel> {
        fun setObservers()
        fun removeObservers()
        fun getViewBinding(): FragmentPrepaidPayBillBinding
        val day: Int get() = 0
        val week: Int get() = 1
        val month: Int get() = 2
        val weekDaysList: List<String>
            get() = listOf(
                "Monday",
                "Tuesday",
                "Wednesday",
                "Thursday",
                "Friday",
                "Saturday",
                "Sunday"
            )
        val monthDaysList: List<String>
            get() = listOf(
                "1",
                "2",
                "3",
                "4",
                "5",
                "6",
                "7",
                "8",
                "9",
                "10",
                "11",
                "12",
                "13",
                "14",
                "15",
                "16",
                "17",
                "18",
                "19",
                "20",
                "21",
                "22",
                "23",
                "24",
                "25",
                "26",
                "27",
                "28",
                "29",
                "30",
                "31"
            )
    }
}
