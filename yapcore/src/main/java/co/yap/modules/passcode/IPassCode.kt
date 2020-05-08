package co.yap.modules.passcode

import android.text.TextWatcher
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IPassCode {

    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State> {
        val clickEvent: SingleClickEvent
        var mobileNumber: String
        fun handlePressView(id: Int)
        fun validatePassCode(success: (isSuccess: Boolean) -> Unit)
        fun isValidPassCode(): Boolean
        fun updatePassCodeRequest(success: () -> Unit)
        fun forgotPassCodeOtpRequest(success: () -> Unit, username: String?)
        fun forgotPassCodeRequest(success: () -> Unit)
        fun isUserLoggedIn(): Boolean
        fun setTitles(title: String, buttonTitle: String)
    }

    interface State : IBase.State {
        var passCode: String
        var dialerError: String
        var valid: Boolean
        fun getTextWatcher(): TextWatcher
        var title: String
        var buttonTitle: String
        var forgotTextVisibility: Boolean
    }
}