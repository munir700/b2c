package com.yap.yappakistan.ui.onboarding.passcode

import android.view.View
import com.yap.core.base.BaseViewModel
import com.yap.core.base.SingleClickEvent
import com.yap.uikit.extensions.hasAllSameChars
import com.yap.uikit.extensions.isSequenced
import com.yap.yappakistan.di.ResourcesProviders
import com.yap.yappk.localization.error_passcode_same_digits
import com.yap.yappk.localization.error_passcode_sequence
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PasscodeVM @Inject constructor(private val resourcesProviders: ResourcesProviders) :
    BaseViewModel<IPassCode.State>(), IPassCode.ViewModel {
    override val viewState: IPassCode.State = PasscodeState()
    override val clickEvent: SingleClickEvent = SingleClickEvent()

    fun handlePressOnCreatePasscode(view: View) {
        if (isPasscodeValid(viewState.passcode.value ?: "")) {
            viewState.passcodeError.value = ""
        } else {
            viewState.passcodeError.value = getPassCodeError(viewState.passcode.value ?: "")
        }
    }

    private fun isPasscodeValid(passcode: String): Boolean {
        return !passcode.hasAllSameChars() && !passcode.isSequenced()
    }

    private fun getPassCodeError(passcode: String): String {
        if (passcode.isSequenced()) return resourcesProviders.getString(keyID = error_passcode_sequence)
        if (passcode.hasAllSameChars()) return resourcesProviders.getString(keyID = error_passcode_same_digits)

        return ""
    }

}