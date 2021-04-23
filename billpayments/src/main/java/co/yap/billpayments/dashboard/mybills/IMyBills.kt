package co.yap.billpayments.dashboard.mybills

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import co.yap.billpayments.dashboard.mybills.adapter.MyBillsAdapter
import co.yap.networking.coreitems.CoreBottomSheetData
import co.yap.yapcore.IBase
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
        val onBottomSheetItemClickListener: OnItemClickListener
        var adapter: MyBillsAdapter
        var lastSelectionSorting: Int
        fun setBillList()
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
