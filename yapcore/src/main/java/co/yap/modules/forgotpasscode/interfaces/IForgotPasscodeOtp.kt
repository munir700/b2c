package co.yap.modules.forgotpasscode.interfaces

import android.content.Context
import android.text.SpannableStringBuilder
import androidx.databinding.ObservableField
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IForgotPasscodeOtp {
    interface View : IBase.View<ViewModel> {
        fun setObservers()
        fun loadData()
    }

    interface ViewModel : IBase.ViewModel<State> {
        val nextButtonPressEvent: SingleClickEvent
        fun handlePressOnSendButton(id: Int)
        fun handlePressOnResendOTP(context: Context)
        fun setPasscode(passcode: String)
        // var mobileNumber: String
        var destination: String?
        var emailOtp: Boolean?
        var action: String?
        var token: String?
    }

    interface State : IBase.State {
        //views
        var verificationTitle: String
        var verificationDescription: String
        var mobileNumber: Array<String?>
        //properties
        var otp: String
        var valid: Boolean
        var timer: String
        var validResend: Boolean
        fun reverseTimer(Seconds: Int,context: Context)
        var color: Int
        // Generic otp logo variables
        var verificationDescriptionForLogo: SpannableStringBuilder?
        var imageUrl: String?
        var fullName: String?
        var currencyType: String?
        var amount: String?
        var position: Int?
        var flagLayoutVisibility: Boolean?
        var beneficiaryCountry: String?
        val isOtpBlocked:ObservableField<Boolean>
    }
}