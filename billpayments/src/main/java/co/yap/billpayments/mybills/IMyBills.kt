package co.yap.billpayments.mybills

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.billpayments.mybills.adapter.MyBillsAdapter
import co.yap.networking.customers.responsedtos.billpayment.BillModel
import co.yap.widgets.bottomsheet.CoreBottomSheetData
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
        var myBills: MutableLiveData<MutableList<BillModel>>
        var selectedBills: MutableList<BillModel>
        var lastSelectionSorting: Int
        fun handlePressOnView(id: Int)
        fun getMyBillsAPI()
        fun onItemSelected(pos: Int, bill: BillModel)
        fun onItemUnselected(pos: Int, bill: BillModel)
        fun setButtonText()
        fun getScreenTitle()
        fun getFiltersList(): MutableList<CoreBottomSheetData>
    }

    interface State : IBase.State {
        var screenTitle: ObservableField<String>
        var totalBillAmount: Double
        var buttonText: ObservableField<String>
        var valid: ObservableBoolean
    }
}
