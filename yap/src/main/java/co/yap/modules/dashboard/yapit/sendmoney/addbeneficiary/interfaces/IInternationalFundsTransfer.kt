package co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.interfaces

import android.text.SpannableStringBuilder
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IInternationalFundsTransfer {

    interface State : IBase.State {
        var beneficiaryName: String
        var beneficiaryPicture: String
        var nameInitialsVisibility: Int
        var senderCurrency: String
        var beneficiaryCurrency: String
        var beneficiaryCountry: String
        var senderAmount: String
        var beneficiaryAmount: String
        var transferFee: String
        var transferFeeSpannable: SpannableStringBuilder?
        var valid: Boolean

    }

    interface ViewModel : IBase.ViewModel<State> {
        var clickEvent: SingleClickEvent
        fun handlePressOnNext(id: Int)
    }

    interface View : IBase.View<ViewModel>
}