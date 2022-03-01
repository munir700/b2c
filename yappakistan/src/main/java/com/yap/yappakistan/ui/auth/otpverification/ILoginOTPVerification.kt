package com.yap.yappakistan.ui.auth.otpverification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yap.core.base.interfaces.IBase
import com.yap.core.utils.SharedPreferenceManager
import com.yap.core.utils.SingleEvent
import com.yap.yappakistan.SessionManager
import com.yap.yappakistan.networking.microservices.authentication.requestdtos.DemographicDataRequest
import com.yap.yappakistan.networking.microservices.customers.responsedtos.accountinfo.AccountInfo

interface ILoginOTPVerification {
    interface View : IBase.View<ViewModel>{
        fun onOTPVerifyClick()
        fun onResendOTPClick()
    }

    interface ViewModel : IBase.ViewModel<State> {
        var otp: String
        val otpVerifiedEvent: LiveData<Boolean>
        val resetOTPEvent: MutableLiveData<Boolean>
        var optToken: String?
        var mobileNumber: String
        var countryCode: String
        val sharedPreferenceManager: SharedPreferenceManager
        val sessionManager: SessionManager
        val openPermissionSettings: LiveData<SingleEvent<Int>>
        val openWaitingList: LiveData<SingleEvent<Int>>
        val openAllowedUser: LiveData<SingleEvent<Int>>
        val openDashboard: LiveData<SingleEvent<AccountInfo?>>

        fun openWaitingListScreen()
        fun openDashboardScreen()
        fun verifyOtp(otpRequest: DemographicDataRequest, demographicRequest: DemographicDataRequest)
        fun resendOtp(otpCreationRequest: DemographicDataRequest)
        fun resetOtp()
    }

    interface State : IBase.State {
        var isValid: MutableLiveData<Boolean>
        var timer: MutableLiveData<String>
        var validResend: MutableLiveData<Boolean>
        var formattedNumber: MutableLiveData<String>
        var color: MutableLiveData<Int>
    }
}