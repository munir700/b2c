package co.yap.billpayments.billerdetail

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.billpayments.billerdetail.adapter.BillerDetailAdapter
import co.yap.billpayments.billerdetail.adapter.BillerDetailInputFieldModel
import co.yap.billpayments.billerdetail.composer.BillerDetailInputComposer
import co.yap.networking.customers.responsedtos.billpayment.BillerDetailResponse
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.enums.BillCategory

interface IBillerDetail {

    interface View : IBase.View<ViewModel> {
        fun setObservers()
        fun removeObservers()
    }

    interface ViewModel : IBase.ViewModel<State> {
        var adapter: BillerDetailAdapter
        var billInputs: MutableLiveData<MutableList<BillerDetailInputFieldModel>>
        val billerDetailItemComposer: BillerDetailInputComposer
        var clickEvent: SingleClickEvent
        fun handlePressOnView(id: Int)
        fun getScreenTitle(billCategory: BillCategory?): String
        fun readBillerDetailsFromFile(): BillerDetailResponse
        fun getBillerDetails()
    }

    interface State : IBase.State {
        var screenTitle: ObservableField<String>
        var valid: ObservableBoolean
    }
}
