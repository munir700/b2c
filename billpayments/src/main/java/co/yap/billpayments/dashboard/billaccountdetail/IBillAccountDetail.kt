package co.yap.billpayments.dashboard.billaccountdetail

import androidx.databinding.ObservableField
import co.yap.billpayments.dashboard.billaccountdetail.adapter.BillHistoryAdapter
import co.yap.billpayments.dashboard.billaccountdetail.adapter.BillHistoryModel
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IBillAccountDetail {
    interface View : IBase.View<ViewModel> {
        fun setObservers()
        fun removeObservers()
    }

    interface ViewModel : IBase.ViewModel<State> {
        var clickEvent: SingleClickEvent
        var adapter: BillHistoryAdapter
        var billAccountDetailModel: BillAccountDetailModel?
        var singleClickEvent: SingleClickEvent
        fun handlePressOnView(id: Int)
        fun getBillAccountDetail(): BillAccountDetailModel
        fun getBillHistory(): MutableList<BillHistoryModel>
    }

    interface State : IBase.State {
        var screenTitle: ObservableField<String>
        var dueAmount: CharSequence
        var billStatus: ObservableField<String>
    }
}
