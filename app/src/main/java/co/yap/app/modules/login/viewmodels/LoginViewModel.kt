package co.yap.app.modules.login.viewmodels

import android.app.Application
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import co.yap.app.main.MainChildViewModel
import co.yap.app.modules.login.interfaces.ILogin
import co.yap.app.modules.login.states.LoginState
import co.yap.modules.onboarding.models.LoadConfig
import co.yap.modules.onboarding.models.UserVerifierProvider
import co.yap.networking.authentication.AuthRepository
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.ApiError
import co.yap.networking.models.RetroApiResponse
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.helpers.getCountryCodeForRegion
import co.yap.yapcore.helpers.isValidPhoneNumber
import co.yap.yapcore.helpers.validation.IValidator
import co.yap.yapcore.helpers.validation.Validator

class LoginViewModel(application: Application) :
    MainChildViewModel<ILogin.State>(application),
    ILogin.ViewModel,
    IRepositoryHolder<AuthRepository>, IValidator,
    Validator.ValidationListener {
    override val state: LoginState = LoginState(application = application)
    override val repository: AuthRepository = AuthRepository
    private val customersRepository: CustomersRepository = CustomersRepository
    override var isAccountBlocked: MutableLiveData<Boolean> = MutableLiveData(false)
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

/*    fun handlePressOnLogin(eventHandle: () -> Unit) {
        eventHandle.invoke()
        state.twoWayTextWatcher =
            Utils.verifyUsername(state.twoWayTextWatcher.trim().filter { !it.isWhitespace() })
        validateUsername {}
    }*/
//    override fun handlePressOnSignUp() {
//   signUpButtonPressEvent.value = true
//    }

    override fun onEditorActionListener(): TextView.OnEditorActionListener {
        return TextView.OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                //handlePressOnLogin(){}
            }
            false
        }
    }

    fun validateUsername(success: (errorMessage: String) -> Unit) {
        launch {
            state.loading = true

            when (val response =
                customersRepository.verifyUsername(state.mobileNumber.value ?: "")) {
                is RetroApiResponse.Success -> {
                    if (response.data.data) {
                        //  parentViewModel?.signingInData?.clientId = state.twoWayTextWatcher
                        state.mobile.set(parentViewModel?.signingInData?.clientId)
                        success.invoke("")
                        state.isError.set(false)
                    } else {
                        state.emailError.value =
                            getString(Strings.screen_sign_in_display_text_error_text)
                        success.invoke(state.emailError.value ?: "")
                        state.isError.set(true)
                    }
                    state.loading = false
                }
                is RetroApiResponse.Error -> {
                    state.emailError.value = handleBlockedAccountError(response.error)
                    success.invoke(state.emailError.value ?: "")
                    state.loading = false
                    state.isError.set(true)
                }
            }
        }
    }

    private fun handleBlockedAccountError(error: ApiError): String {
        return when (error.actualCode) {
            "AD-10018" -> {
                isAccountBlocked.value = true
                ""
            }
            else -> {
                state.error = error.message
                error.message
            }

        }
    }

    override var validator: Validator? = Validator(null)
    fun onPhoneNumberTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        state.isError.set(false)
        state.valid.set(
            isValidPhoneNumber(
                s.toString(),
                getCountryCodeForRegion(
                    state.countryCode.get().toString().replace("+", "").toInt()
                )
            )
        )
    }

    private val _userVerified: MutableLiveData<String> = MutableLiveData()
    val userVerified: LiveData<String> = _userVerified
    private val userVerifier: UserVerifierProvider = UserVerifierProvider()

    fun verifyUser(countryCode: String, mobileNumber: String) {
        state.loading = true
        LoadConfig(context).initYapRegion(countryCode)
        userVerifier.provide(countryCode).verifyUser(mobileNumber) { result ->
            state.loading = false
            if (result.isSuccess && result.getOrNull() == true) {
                _userVerified.value = countryCode
            }
        }
    }
}