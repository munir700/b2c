package co.yap.billpayments.billdetail.editbill

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.billpayments.addbiller.addbillerdetail.adapter.AddBillerDetailAdapter
import co.yap.billpayments.addbiller.addbillerdetail.composer.AddBillerDetailInputComposer
import co.yap.billpayments.databinding.FragmentEditBillBinding
import co.yap.billpayments.utils.enums.PaymentScheduleType
import co.yap.networking.coreitems.CoreBottomSheetData
import co.yap.networking.transactions.requestdtos.EditBillerRequest
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

class IEditBill {
    interface ViewModel : IBase.ViewModel<State> {
        var adapter: AddBillerDetailAdapter
        val addBillerDetailItemComposer: AddBillerDetailInputComposer
        var clickEvent: SingleClickEvent
        fun handlePressOnView(id: Int)
        fun setEditBillDetails()
        fun updateAutoPaySelection(
            isWeek: Boolean,
            isMonth: Boolean,
            paymentScheduleType: PaymentScheduleType
        )

        fun updateReminderSelection(
            isThreedays: Boolean,
            isOneWeek: Boolean,
            isThreeWeek: Boolean,
            totalDays: Int
        )

        fun composeWeekDaysList(listData: List<String>): MutableList<CoreBottomSheetData>
        fun deleteBill(success: () -> Unit)
        fun lengthValidation(): Boolean
        fun textChangedValidation(): Boolean
        fun editBill(editBillerRequest: EditBillerRequest, success: () -> Unit)
        fun getEditBillerRequest(): EditBillerRequest
        fun validation()
        val editBillerError: MutableLiveData<Int?>

    }

    interface State : IBase.State {
        var screenTitle: ObservableField<String>
        var valid: ObservableBoolean
        var totalDays: ObservableField<Int>
        var isAutoPaymentOn: ObservableBoolean
        var isBillReminderOn: ObservableBoolean
        var selectedWeekDay: ObservableField<String>
        var selectedMonthDay: ObservableField<String>
        var autoPaymentScheduleType: ObservableField<String>
        var autoPaymentScheduleTypeWeek: ObservableBoolean
        var autoPaymentScheduleTypeMonth: ObservableBoolean
        var billReminderThreeDays: ObservableBoolean
        var billReminderOneWeek: ObservableBoolean
        var billReminderThreeWeeks: ObservableBoolean
        var nickNameValue: ObservableField<String>
        val EVENT_BILLER_NOTAVAILABLE: Int
            get() = 1101
        val EVENT_WORNG_INPUT: Int
            get() = 1102
    }

    interface View : IBase.View<ViewModel> {
        fun setObservers()
        fun removeObservers()
        fun showPopUp()
        val day: Int get() = 0
        val week: Int get() = 1
        val month: Int get() = 2
        val threeDays: Int get() = 0
        val oneWeek: Int get() = 1
        val threeWeeks: Int get() = 2
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
