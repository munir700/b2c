package co.yap.billpayments.dashboard.editbill

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import co.yap.billpayments.addbiller.addbillerdetail.adapter.AddBillerDetailAdapter
import co.yap.billpayments.addbiller.addbillerdetail.composer.AddBillerDetailInputComposer
import co.yap.networking.customers.responsedtos.billpayment.BillerInputDetails
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

class IEditBill {

    interface View : IBase.View<ViewModel> {
        fun setObservers()
        fun removeObservers()
    }

    interface ViewModel : IBase.ViewModel<State> {
        var adapter: AddBillerDetailAdapter
        val addBillerDetailItemComposer: AddBillerDetailInputComposer
        var clickEvent: SingleClickEvent
        var billerDetailsResponse: BillerInputDetails?
        fun handlePressOnView(id: Int)
        fun getEditBillDetails(billerId: String)
    }

    interface State : IBase.State {
        var screenTitle: ObservableField<String>
        var valid: ObservableBoolean
    }
}
