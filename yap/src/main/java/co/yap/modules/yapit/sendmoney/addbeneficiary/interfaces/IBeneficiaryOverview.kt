package co.yap.modules.yapit.sendmoney.addbeneficiary.interfaces

import android.graphics.drawable.Drawable
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.SingleLiveEvent

interface IBeneficiaryOverview {

    interface State : IBase.State {
        var country: String
        var transferType: String
        var currency: String
        var nickName: String
        var firstName: String
        var lastName: String
        var phoneNumber: String
        var flagDrawableResId: Int
        var mobile: String
        var drawbleRight: Drawable?
        var mobileNoLength: Int

        var accountIban: String
        var countryBankRequirementFieldCode: String
        var swiftCode: String

        var valid: Boolean

    }

    interface ViewModel : IBase.ViewModel<State> {
        val backButtonPressEvent: SingleLiveEvent<Boolean>
        var clickEvent: SingleClickEvent

        fun handlePressOnAddNow(id: Int)
        fun handlePressOnConfirm(id: Int)
    }

    interface View : IBase.View<ViewModel>
}