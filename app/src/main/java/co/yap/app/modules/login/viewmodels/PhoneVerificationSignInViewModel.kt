package co.yap.app.modules.login.viewmodels

import android.app.Application
import android.os.Build
import androidx.lifecycle.MutableLiveData
import co.yap.yapcore.helpers.encryption.EncryptionUtils
import co.yap.app.modules.login.interfaces.IPhoneVerificationSignIn
import co.yap.modules.onboarding.constants.Constants
import co.yap.networking.authentication.AuthRepository
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.requestdtos.DemographicDataRequest
import co.yap.networking.customers.responsedtos.AccountInfo
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.messages.MessagesRepository
import co.yap.networking.messages.requestdtos.CreateOtpGenericRequest
import co.yap.networking.messages.requestdtos.VerifyOtpGenericRequest
import co.yap.networking.models.RetroApiResponse
import co.yap.translation.Strings
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleLiveEvent
import co.yap.yapcore.helpers.SharedPreferenceManager
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.extentions.trackEventWithAttributes
import co.yap.yapcore.leanplum.UserAttributes
import co.yap.yapcore.managers.MyUserManager

class PhoneVerificationSignInViewModel(application: Application) :
    BaseViewModel<IPhoneVerificationSignIn.State>(application), IPhoneVerificationSignIn.ViewModel,
    IRepositoryHolder<AuthRepository> {

    override val repository: AuthRepository = AuthRepository
    override val state: co.yap.app.modules.login.states.PhoneVerificationSignInState =
        co.yap.app.modules.login.states.PhoneVerificationSignInState(application)
    override val nextButtonPressEvent: SingleLiveEvent<Boolean> = SingleLiveEvent()
    override val postDemographicDataResult: SingleLiveEvent<Boolean> = SingleLiveEvent()
    override val verifyOtpResult: SingleLiveEvent<Boolean> = SingleLiveEvent()
    private val customersRepository: CustomersRepository = CustomersRepository;
    private val messagesRepository: MessagesRepository = MessagesRepository
    override val accountInfo: MutableLiveData<AccountInfo> = MutableLiveData()

    override fun handlePressOnSendButton() {
        nextButtonPressEvent.postValue(true)
    }

    override fun onCreate() {
        super.onCreate()
        state.reverseTimer(10)
        state.valid = false
    }

    override fun verifyOtp() {
        launch {
            state.loading = true
            when (val response =
                messagesRepository.verifyOtpGeneric(
                    VerifyOtpGenericRequest(
                        Constants.ACTION_DEVICE_VERIFICATION,
                        state.otp
                    )
                )) {
                is RetroApiResponse.Success -> {
                    val sharedPreferenceManager = SharedPreferenceManager(context)

                    sharedPreferenceManager.save(
                        SharedPreferenceManager.KEY_IS_USER_LOGGED_IN,
                        true
                    )
                    EncryptionUtils.encrypt(context, state.passcode)?.let {
                        sharedPreferenceManager.save(SharedPreferenceManager.KEY_PASSCODE, it)
                    }
                    EncryptionUtils.encrypt(context, state.username)?.let {
                        sharedPreferenceManager.save(SharedPreferenceManager.KEY_USERNAME, it)
                    }
                    verifyOtpResult.postValue(true)
                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                    state.loading = false
                }
            }

        }
    }

    override fun handlePressOnResend() {
        launch {
            state.loading = true
            when (val response =
                messagesRepository.createOtpGeneric(CreateOtpGenericRequest(Constants.ACTION_DEVICE_VERIFICATION))) {
                is RetroApiResponse.Success -> {
                    state.toast =
                        getString(Strings.screen_verify_phone_number_display_text_resend_otp_success)
                    state.reverseTimer(10)
                    state.valid = false
                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                }
            }
            state.loading = false
        }
    }

    override fun postDemographicData() {
        val sharedPreferenceManager = SharedPreferenceManager(context)
        val deviceId: String? =
            sharedPreferenceManager.getValueString(SharedPreferenceManager.KEY_APP_UUID)
        launch {
            state.loading = true
            when (val response =
                customersRepository.postDemographicData(
                    DemographicDataRequest(
                        "LOGIN",
                        Build.VERSION.RELEASE,
                        deviceId.toString(),
                        Build.BRAND,
                        if (Utils.isEmulator()) "generic" else Build.MODEL,
                        "Android"
                    )
                )) {
                is RetroApiResponse.Success -> {
                    postDemographicDataResult.value = true
                }
                is RetroApiResponse.Error -> {
                    state.loading = false
                    state.toast = response.error.message
                }
            }

        }
    }

    override fun getAccountInfo() {
        launch {
            //state.loading = true
            when (val response = customersRepository.getAccountInfo()) {
                is RetroApiResponse.Success -> {
                    if (response.data.data.isNotEmpty()) {
                        MyUserManager.user = response.data.data[0]
                        accountInfo.postValue(response.data.data[0])
                        setUserAttributes()
                    }
                }
                is RetroApiResponse.Error -> state.toast = response.error.message
            }
            state.loading = false
        }
    }

    private fun setUserAttributes() {
        MyUserManager.user?.let {
            val info: HashMap<String, Any> = HashMap()
            info[UserAttributes().accountType] = it.accountType ?: ""
            info[UserAttributes().email] = it.currentCustomer.email ?: ""
            info[UserAttributes().nationality] = it.currentCustomer.nationality ?: ""
            info[UserAttributes().firstName] = it.currentCustomer.firstName ?: ""
            info[UserAttributes().lastName] = it.currentCustomer.lastName
            info[UserAttributes().documentsVerified] = it.documentsVerified ?: false
            trackEventWithAttributes(info)
        }
    }
}