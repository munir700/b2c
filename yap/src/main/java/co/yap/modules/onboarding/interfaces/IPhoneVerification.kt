package co.yap.modules.onboarding.interfaces

import android.content.Context
import androidx.databinding.ObservableField
import co.yap.networking.customers.CustomersRepository
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IPhoneVerification {

    interface View : IBase.View<ViewModel>{
        fun setObservers()
    }

    interface ViewModel : IBase.ViewModel<State> {
        // val nextButtonPressEvent: SingleLiveEvent<Boolean>
        val nextButtonPressEvent: SingleClickEvent

        //  fun handlePressOnSend()
        fun handlePressOnSendButton(id: Int)

        fun handlePressOnResendOTP(context: Context)
        fun setPasscode(passcode: String)
        fun checkMobileNumberForWaitingList(success:() ->Unit)
        val customersRepository: CustomersRepository
    }

    interface State : IBase.State {
        //views
        var verificationTitle: String
        var verificationDescription: String

        //properties
        var otp: String
        var valid: Boolean
        var timer: String
        var validResend: Boolean
        fun reverseTimer(Seconds: Int, context: Context)
        var color: Int
        var isOtpBlocked: ObservableField<Boolean>
    }
}