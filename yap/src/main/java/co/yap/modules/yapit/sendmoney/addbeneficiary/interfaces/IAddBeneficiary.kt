package co.yap.modules.yapit.sendmoney.addbeneficiary.interfaces

import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.SingleLiveEvent

interface IAddBeneficiary {

    interface State : IBase.State {
        var country: String
        var transferType: String
        var currency: String
        var nickName: String
        var firstName: String
        var lastName: String
        var phoneNumber: String
        var flagDrawableResId: Int

        var valid: Boolean

    }

    interface ViewModel : IBase.ViewModel<State> {
        val backButtonPressEvent: SingleLiveEvent<Boolean>
        var clickEvent: SingleClickEvent

        fun handlePressOnAddNow()
    }

    interface View : IBase.View<ViewModel>
}