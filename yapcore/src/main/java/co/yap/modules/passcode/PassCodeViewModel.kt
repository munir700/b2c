package co.yap.modules.passcode

import android.app.Application
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.requestdtos.ForgotPasscodeRequest
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.messages.MessagesRepository
import co.yap.networking.messages.requestdtos.CreateForgotPasscodeOtpRequest
import co.yap.networking.models.RetroApiResponse
import co.yap.translation.Strings
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.enums.AlertType
import co.yap.yapcore.helpers.SharedPreferenceManager
import co.yap.yapcore.helpers.StringUtils
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.extentions.toast

class PassCodeViewModel(application: Application) : BaseViewModel<IPassCode.State>(application),
    IPassCode.ViewModel, IRepositoryHolder<CustomersRepository> {
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val state: PassCodeState = PassCodeState()
    override val repository: CustomersRepository = CustomersRepository
    private val messagesRepository: MessagesRepository = MessagesRepository
    override var mobileNumber: String = ""

    override fun setTitles(title: String, buttonTitle: String) {
        state.title = title
        state.buttonTitle = buttonTitle
    }

    override fun handlePressView(id: Int) {
        clickEvent.setValue(id)
    }

    override fun validatePassCode(success: (isSuccess: Boolean) -> Unit) {
        launch {
            state.loading = true
            when (repository.validateCurrentPasscode(
                state.passCode
            )) {
                is RetroApiResponse.Success -> {
                    success(true)
                    state.loading = false
                }
                is RetroApiResponse.Error -> {
                    success(false)
                    state.loading = false
                }
            }
        }
    }

    override fun updatePassCodeRequest(success: () -> Unit) {
        launch {
            state.loading = true
            when (val response = repository.changePasscode(state.passCode)) {
                is RetroApiResponse.Success -> {
                    state.loading = false
                    success()
                }
                is RetroApiResponse.Error -> {
                    state.toast = "${response.error.message}^${AlertType.DIALOG.name}"
                    state.loading = false
                }
            }
        }
    }

    override fun forgotPassCodeRequest(success: () -> Unit) {
        launch {
            state.loading = true
            when (val response =
                repository.forgotPasscode(
                    ForgotPasscodeRequest(
                        mobileNumber,
                        state.passCode
                    )
                )) {
                is RetroApiResponse.Success -> {
                    state.loading = false
                    val sharedPreferenceManager = SharedPreferenceManager(context)
                    sharedPreferenceManager.savePassCodeWithEncryption(state.passCode)
                    success()
                }
                is RetroApiResponse.Error -> {
                    state.toast = "${response.error.message}^${AlertType.DIALOG.name}"
                    state.loading = false
                }

            }
        }
    }

    override fun forgotPassCodeOtpRequest(success: () -> Unit, username: String?) {
        username?.let {
            launch {
                state.loading = true
                when (val response = messagesRepository.createForgotPasscodeOTP(
                    CreateForgotPasscodeOtpRequest(
                        Utils.verifyUsername(it), !Utils.isUsernameNumeric(it)
                    )
                )) {
                    is RetroApiResponse.Success -> {
                        response.data.data?.let {
                            mobileNumber = it
                        }
                        state.loading = false
                        success()
                    }
                    is RetroApiResponse.Error -> {
                        state.loading = false
                    }
                }
            }
        } ?: toast(context, "Invalid user name")
    }

    override fun isValidPassCode(): Boolean {
        val isSame = StringUtils.hasAllSameChars(state.passCode)
        val isSequenced = StringUtils.isSequenced(state.passCode)
        if (isSequenced) state.dialerError =
            getString(Strings.screen_create_passcode_display_text_error_sequence)
        if (isSame) state.dialerError =
            getString(Strings.screen_create_passcode_display_text_error_same_digits)
        return !isSame && !isSequenced
    }

    override fun isUserLoggedIn(): Boolean {
        val sharedPreferenceManager = SharedPreferenceManager(context)
        return sharedPreferenceManager.getValueBoolien(
            Constants.KEY_IS_USER_LOGGED_IN,
            false
        )
    }
}