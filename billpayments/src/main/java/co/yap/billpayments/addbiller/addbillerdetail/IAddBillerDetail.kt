package co.yap.billpayments.addbiller.addbillerdetail

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.billpayments.addbiller.addbillerdetail.adapter.AddBillerDetailAdapter
import co.yap.billpayments.addbiller.addbillerdetail.composer.AddBillerDetailInputComposer
import co.yap.networking.customers.requestdtos.AddBillerInformationRequest
import co.yap.networking.customers.responsedtos.billpayment.BillerDetailResponse
import co.yap.networking.customers.responsedtos.billpayment.BillerInputDetails
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.enums.BillCategory

interface IAddBillerDetail {

    interface View : IBase.View<ViewModel> {
        fun setObservers()
        fun removeObservers()
    }

    interface ViewModel : IBase.ViewModel<State> {
        var adapterAdd: AddBillerDetailAdapter
        val addBillerDetailItemComposer: AddBillerDetailInputComposer
        var clickEvent: SingleClickEvent
        val billerDetailsResponse: MutableLiveData<BillerInputDetails>
        fun handlePressOnView(id: Int)
        fun getScreenTitle(billCategory: BillCategory?): String
        fun readBillerDetailsFromFile(): BillerDetailResponse
        fun getBillerDetails(billerId: String)
        fun addBiller(billerInformationRequest: AddBillerInformationRequest, success: () -> Unit)
        fun getBillerInformationRequest(billerInformation: BillerInputDetails?): AddBillerInformationRequest
    }

    interface State : IBase.State {
        var screenTitle: ObservableField<String>
        var valid: ObservableBoolean
    }
}
