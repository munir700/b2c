package co.yap.sendmoney.interfaces

import androidx.lifecycle.MutableLiveData
import co.yap.networking.customers.responsedtos.sendmoney.Beneficiary
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IBeneficiaryCashTransfer {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State> {
        fun handlePressOnView(id: Int)
        val clickEvent: SingleClickEvent
        var errorEvent: MutableLiveData<String>
    }

    interface State : IBase.State {
        var toolBarVisibility: Boolean?
        var leftButtonVisibility: Boolean?
        var rightButtonVisibility: Boolean?
        var rightButtonText: String?
        var toolBarTitle: String?
        var otpSuccess: Boolean?
        var beneficiary: Beneficiary?
        var position: Int
    }
}