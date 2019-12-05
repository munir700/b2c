package co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.interfaces

import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.SingleLiveEvent

interface IBankDetails {

    interface State : IBase.State {
        var bankName: String
        var bankBranch: String
        var bankCity: String
        var swiftCode: String
        var valid: Boolean
    }

    interface ViewModel : IBase.ViewModel<State> {
        val backButtonPressEvent: SingleLiveEvent<Boolean>
        var clickEvent: SingleClickEvent

        fun handlePressOnAddBank(id: Int)
    }

    interface View : IBase.View<ViewModel>
}