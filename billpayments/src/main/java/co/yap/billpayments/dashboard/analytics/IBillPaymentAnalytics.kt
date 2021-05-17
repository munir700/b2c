package co.yap.billpayments.dashboard.analytics

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import co.yap.billpayments.databinding.FragmentBillPaymentsAnalyticsBinding
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IBillPaymentAnalytics {
    interface View : IBase.View<ViewModel> {
        fun setObservers()
        fun removeObservers()
        fun getBinding(): FragmentBillPaymentsAnalyticsBinding
    }

    interface ViewModel : IBase.ViewModel<State> {
        var clickEvent: SingleClickEvent
        fun handlePressOnView(id: Int)
    }

    interface State : IBase.State {
        var selectedItemSpentValue: String
        var selectedItemName: String?
        var selectedItemPosition: Int
        var totalSpent: String?
        var selectedMonth: String?
        var monthCount: Int
        var nextMonth: ObservableBoolean
        var previousMonth: ObservableBoolean
        var displayMonth: ObservableField<String>
    }
}
