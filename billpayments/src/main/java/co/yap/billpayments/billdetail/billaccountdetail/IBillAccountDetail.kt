package co.yap.billpayments.billdetail.billaccountdetail

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.billpayments.billdetail.billaccountdetail.adapter.BillHistoryAdapter
import co.yap.billpayments.billdetail.billaccountdetail.adapter.BillHistoryModel
import co.yap.networking.transactions.responsedtos.billpayment.BillHistory
import co.yap.networking.transactions.responsedtos.billpayment.BillLineChartHistory
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.enums.BillStatus

interface IBillAccountDetail {
    interface View : IBase.View<ViewModel> {
        fun setObservers()
        fun removeObservers()
    }

    interface ViewModel : IBase.ViewModel<State> {
        var clickEvent: SingleClickEvent
        var adapter: BillHistoryAdapter
        var billHistory: BillHistory?
        var singleClickEvent: SingleClickEvent
        val lineChartHistoryResponse: MutableLiveData<MutableList<BillLineChartHistory>>
        fun handlePressOnView(id: Int)
        fun getBillAccountHistory(uuid: String)
        fun getBillHistory(): MutableList<BillHistoryModel>
        fun getBillStatusString(billStatus: BillStatus): String
        fun getBillAccountLineChartHistory(uuid: String)
    }

    interface State : IBase.State {
        var screenTitle: ObservableField<String>
        var dueAmount: CharSequence
        var billStatus: ObservableField<String>
    }
}
