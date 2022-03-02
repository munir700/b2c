package com.yap.yappakistan.ui.auth.forgotpasscode.createnewpasscode

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yap.core.base.BaseViewModel
import com.yap.core.utils.KEY_TOUCH_ID_ENABLED
import com.yap.yappakistan.SharedPreferenceManager
import com.yap.core.utils.SingleEvent
import com.yap.uikit.extensions.hasAllSameChars
import com.yap.uikit.extensions.isSequenced
import com.yap.yappakistan.R
import com.yap.yappakistan.di.ResourcesProviders
import com.yap.yappakistan.networking.apiclient.base.ApiResponse
import com.yap.yappakistan.networking.microservices.customers.CustomersApi
import com.yap.yappakistan.networking.microservices.customers.requestsdtos.CreateNewPasscodeRequest
import com.yap.yappk.localization.error_passcode_same_digits
import com.yap.yappk.localization.error_passcode_sequence
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CreateNewPasscodeVM @ViewModelInject constructor(
    override val viewState: CreateNewPasscodeState,
    private val customersApi: CustomersApi,
    override val sharedPreferenceManager: SharedPreferenceManager,
    private val resourcesProviders: ResourcesProviders
) : BaseViewModel<ICreateNewPasscode.State>(), ICreateNewPasscode.ViewModel {
    override var mobileNumber: String = ""
    override var optToken: String? = null

    private val _isPasscodeCreated: MutableLiveData<Boolean> = MutableLiveData()
    override val isPasscodeCreated: LiveData<Boolean> = _isPasscodeCreated

    private val _openCreateNewPasscodeSuccess = MutableLiveData<SingleEvent<Int>>()
    override val openCreateNewPasscodeSuccess: LiveData<SingleEvent<Int>> get() = _openCreateNewPasscodeSuccess

    override fun handlePressOnCreatePasscode() {
        if (isPasscodeValid(viewState.passcode.value ?: "")) {
            viewState.passcodeError.value = ""
        } else {
            viewState.passcodeError.value = getPassCodeError(viewState.passcode.value ?: "")
        }
    }

    override fun savePassCode() {
        sharedPreferenceManager.savePassCodeWithEncryption(viewState.passcode.value ?: "")
    }

    private fun isPasscodeValid(passcode: String): Boolean {
        return !passcode.hasAllSameChars() && !passcode.isSequenced()
    }

    private fun getPassCodeError(passcode: String): String {
        if (passcode.isSequenced()) return resourcesProviders.getString(keyID = error_passcode_sequence)
        if (passcode.hasAllSameChars()) return resourcesProviders.getString(keyID = error_passcode_same_digits)

        return ""
    }

    override fun isBiometricLoginEnabled(isBiometricAvailable: Boolean): Boolean {
        return if (isBiometricAvailable) {
            sharedPreferenceManager.getValueBoolien(KEY_TOUCH_ID_ENABLED, false)
                    && !sharedPreferenceManager.getDecryptedPassCode().isNullOrEmpty()
        } else
            false
    }

    override fun createNewPasscode(createNewPasscodeRequest: CreateNewPasscodeRequest) {
        launch {
            showLoading(true)
            val response = customersApi.createNewPasscode(createNewPasscodeRequest)
            withContext(Dispatchers.Main) {
                when (response) {
                    is ApiResponse.Success -> {
                        hideLoading()
                        _isPasscodeCreated.value = true
                    }
                    is ApiResponse.Error -> {
                        hideLoading()
                        _isPasscodeCreated.value = false
                        viewState.passcodeError.value = response.error.message
                    }
                }
            }
        }
    }

    override fun openCreateNewPasscodeSuccessScreen() {
        _openCreateNewPasscodeSuccess.value =
            SingleEvent(R.id.action_createNewPasscodeFragment_to_newPasscodeSuccessFragment)
    }


}
