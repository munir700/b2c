package co.yap.billpayments.dashboard.mybills

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.billpayments.dashboard.mybills.adapter.BillModel
import co.yap.billpayments.dashboard.mybills.adapter.MyBillsAdapter
import co.yap.networking.coreitems.CoreBottomSheetData
import co.yap.networking.customers.responsedtos.billpayment.ViewBillModel
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.interfaces.OnItemClickListener

interface IMyBills {
    interface View : IBase.View<ViewModel> {
        fun setObservers()
        fun removeObservers()
        fun openSortBottomSheet()
    }

    interface ViewModel : IBase.ViewModel<State> {
        val sortByDueDate: Int get() = 0
        val sortByRecentlyAdded: Int get() = 1
        val sortByAToZAscending: Int get() = 2
        val sortByZToADescending: Int get() = 3
        val clickEvent: SingleClickEvent
        val onBottomSheetItemClickListener: OnItemClickListener
        var adapter: MyBillsAdapter
        var bills: MutableLiveData<MutableList<BillModel>>
        var billsList: MutableList<ViewBillModel>
        var lastSelectionSorting: Int
        fun handlePressOnView(id: Int)
        fun getMyBillsAPI()
        fun setButtonText()
        fun setScreenTitle()
        fun getFiltersList(): MutableList<CoreBottomSheetData>
    }

    interface State : IBase.State {
        var screenTitle: ObservableField<String>
        var totalBillAmount: Double
        var buttonText: ObservableField<String>
        var valid: ObservableBoolean
    }
}
