package co.yap.app.modules.login.interfaces

import android.content.Context
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.networking.customers.responsedtos.AccountInfo
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleLiveEvent

interface IPhoneVerificationSignIn {

    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State> {
        val nextButtonPressEvent: SingleLiveEvent<Boolean>
        val verifyOtpResult: SingleLiveEvent<Boolean>
        val postDemographicDataResult: SingleLiveEvent<Boolean>
        val accountInfo: MutableLiveData<AccountInfo>
        fun postDemographicData()
        fun handlePressOnResend(context: Context)
        fun handlePressOnSendButton()
        fun verifyOtp()
        fun getAccountInfo()
    }

    interface State : IBase.State {
        var otp: String
        var passcode: String
        var username: String
        var timer: String
        var valid: Boolean
        var validateBtn: Boolean
        fun reverseTimer(Seconds: Int,context: Context)
        var color: Int
        var isOtpBlocked: ObservableField<Boolean>
    }
}