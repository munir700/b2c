package co.yap.billpayments.dashboard.analytics

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.billpayments.dashboard.analytics.adapter.BPAnalyticsAdapter
import co.yap.billpayments.databinding.FragmentBillPaymentsAnalyticsBinding
import co.yap.networking.transactions.responsedtos.billpayments.BPAnalyticsModel
import co.yap.widgets.pieview.PieEntry
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent
import java.util.*

interface IBillPaymentAnalytics {
    interface View : IBase.View<ViewModel> {
        fun setObservers()
        fun removeObservers()
        fun getBinding(): FragmentBillPaymentsAnalyticsBinding
    }

    interface ViewModel : IBase.ViewModel<State> {
        var clickEvent: SingleClickEvent
        val analyticsAdapter: BPAnalyticsAdapter
        val analyticsData: MutableLiveData<List<BPAnalyticsModel>>
        fun handlePressOnView(id: Int)
        fun getEntries(it: List<BPAnalyticsModel>?): ArrayList<PieEntry>
        fun setSelectedItemState(
            model: BPAnalyticsModel,
            currentPosition: Int
        )
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
