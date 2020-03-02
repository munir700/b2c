package co.yap.modules.otp

import android.content.Context
import android.text.SpannableStringBuilder
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IGenericOtp {
    interface View : IBase.View<ViewModel> {
        fun setObservers()
        fun setResultData()
    }

    interface ViewModel : IBase.ViewModel<State> {
        val clickEvent: SingleClickEvent
        val errorEvent: SingleClickEvent
        fun handlePressOnButtonClick(id: Int)
        fun handlePressOnResendClick(context: Context)
        var destination: String?
        var emailOtp: Boolean?
        fun createOtp(resend: Boolean = false,context: Context)
        fun initializeData(context: Context)
    }

    interface State : IBase.State {
        var otpDataModel: OtpDataModel?
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
        var errorMessage: String?
    }
}