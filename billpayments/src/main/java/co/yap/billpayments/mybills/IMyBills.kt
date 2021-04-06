package co.yap.billpayments.mybills

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.billpayments.mybills.adapter.MyBillsAdapter
import co.yap.networking.customers.responsedtos.billpayment.BillModel
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IMyBills {
    interface View : IBase.View<ViewModel> {
        fun setObservers()
        fun removeObservers()

    }

    interface ViewModel : IBase.ViewModel<State> {
        var adapter: MyBillsAdapter
        var myBills: MutableLiveData<MutableList<BillModel>>
        fun handlePressOnView(id: Int)
        val clickEvent: SingleClickEvent
        fun getMyBillsAPI()
        var selectedBills: MutableList<BillModel>
        fun onItemSelected(pos: Int, bill: BillModel)
        fun onItemUnselected(pos: Int, bill: BillModel)
        fun setButtonText()
        fun getScreenTitle()
    }

    interface State : IBase.State {
        var screenTitle: ObservableField<String>
        var totalBillAmount: Double
        var buttonText: ObservableField<String>
        var valid: ObservableBoolean
    }
}
