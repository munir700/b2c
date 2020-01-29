package co.yap.app.modules.login.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.app.constants.Constants
import co.yap.app.login.EncryptionUtils
import co.yap.app.modules.login.interfaces.IVerifyPasscode
import co.yap.app.modules.login.states.VerifyPasscodeState
import co.yap.networking.authentication.AuthRepository
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.responsedtos.AccountInfo
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.messages.MessagesRepository
import co.yap.networking.messages.requestdtos.CreateForgotPasscodeOtpRequest
import co.yap.networking.messages.requestdtos.CreateOtpGenericRequest
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.SingleLiveEvent
import co.yap.yapcore.helpers.SharedPreferenceManager
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.extentions.trackEventWithAttributes
import co.yap.yapcore.leanplum.UserAttributes
import co.yap.yapcore.managers.MyUserManager
import java.util.regex.Pattern

class VerifyPasscodeViewModel(application: Application) :
    BaseViewModel<IVerifyPasscode.State>(application),
    IVerifyPasscode.ViewModel, IRepositoryHolder<AuthRepository> {

    override val forgotPasscodeButtonPressEvent: SingleClickEvent = SingleClickEvent()
    override val repository: AuthRepository = AuthRepository
    override val state: VerifyPasscodeState = VerifyPasscodeState()
    override val signInButtonPressEvent: SingleLiveEvent<Boolean> = SingleLiveEvent()
    override val loginSuccess: SingleLiveEvent<Boolean> = SingleLiveEvent()
    override val validateDeviceResult: SingleLiveEvent<Boolean> = SingleLiveEvent()
    override val createOtpResult: SingleLiveEvent<Boolean> = SingleLiveEvent()
    override var isFingerprintLogin: Boolean = false
    private val customersRepository: CustomersRepository = CustomersRepository
    override var mobileNumber: String = ""
    override var EVENT_LOGOUT_SUCCESS: Int = 101

    override val accountInfo: MutableLiveData<AccountInfo> = MutableLiveData()
    private val messagesRepository: MessagesRepository = MessagesRepository

    override fun login() {
        launch {
            state.loading = true
            when (val response = repository.login(state.username, state.passcode)) {
                is RetroApiResponse.Success -> {
                    loginSuccess.postValue(true)
                    state.loading = false
                }
                is RetroApiResponse.Error -> {
                    // state.toast = response.error.message
                    loginSuccess.postValue(false)
                    state.loading = false
                }
            }
        }
    }

    override fun handlePressOnForgotPasscodeButton(id: Int) {
        val username = getUserName()
        username?.let {
            launch {
                state.loading = true
                when (val response = messagesRepository.createForgotPasscodeOTP(
                    CreateForgotPasscodeOtpRequest(
                        Utils.verifyUsername(username),
                        Utils.isUsernameNumeric(username)
                    )
                )) {
                    is RetroApiResponse.Success -> {
                        response.data.data?.let {
                            mobileNumber = it
                        }

                        state.loading = false
                        forgotPasscodeButtonPressEvent.setValue(id)
                    }
                    is RetroApiResponse.Error -> {
                        state.toast = response.error.message
                        state.loading = false
                    }
                }
            }
        }
    }

    private fun getUserName(): String? {
        val sharedPreferenceManager = SharedPreferenceManager(context)
        return if (!SharedPreferenceManager(context).getValueBoolien(
                SharedPreferenceManager.KEY_IS_USER_LOGGED_IN,
                false
            )
        ) {
            state.username
        } else {
            sharedPreferenceManager.getUserName()
        }
    }

    override fun validateDevice() {
        launch {
            when (val response = customersRepository.validateDemographicData(state.deviceId)) {
                is RetroApiResponse.Success -> {
                    response.data.data?.let {
                        if (it) state.loading = false
                        validateDeviceResult.postValue(it)
                    }
                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                    state.loading = false
                }
            }
        }
    }

    override fun getAccountInfo() {
        launch {
            state.loading = true
            when (val response = customersRepository.getAccountInfo()) {
                is RetroApiResponse.Success -> {
                    if (!response.data.data.isNullOrEmpty()) {
                        //MyUserManager.user = response.data.data[0]
                        MyUserManager.user = response.data.data[0]
                        accountInfo.postValue(response.data.data[0])
                        //MyUserManager.user?.setLiveData() // DOnt remove this line
                        setUserAttributes()
                    }
                }
                is RetroApiResponse.Error -> state.toast = response.error.message
            }
            state.loading = false
        }
    }

    override fun createOtp() {
        launch {
            state.loading = true
            when (val response =
                messagesRepository.createOtpGeneric(CreateOtpGenericRequest(Constants.ACTION_DEVICE_VERIFICATION))) {
                is RetroApiResponse.Success -> {
                    createOtpResult.postValue(true)
                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                    state.loading = false
                }
            }
            state.loading = false
        }
    }

    override fun handlePressOnSignInButton() {
        signInButtonPressEvent.postValue(true)
    }


    override fun logout() {
        val deviceId: String? =
            SharedPreferenceManager(context).getValueString(SharedPreferenceManager.KEY_APP_UUID)
        launch {
            state.loading = true
            when (val response = repository.logout(deviceId.toString())) {
                is RetroApiResponse.Success -> {
                    state.loading = false
                    forgotPasscodeButtonPressEvent.setValue(EVENT_LOGOUT_SUCCESS)
                }
                is RetroApiResponse.Error -> {
                    state.loading = false
                    forgotPasscodeButtonPressEvent.setValue(EVENT_LOGOUT_SUCCESS)
                }
            }
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
            info[UserAttributes().mainUser] = it.accountType == "B2C_ACCOUNT"
            info[UserAttributes().householdUser] = it.accountType == "B2C_HOUSEHOLD"
            info[UserAttributes().youngUser] = false
            info[UserAttributes().b2bUser] = false

            it.uuid?.let { trackEventWithAttributes(it, info) }
        }
    }

}