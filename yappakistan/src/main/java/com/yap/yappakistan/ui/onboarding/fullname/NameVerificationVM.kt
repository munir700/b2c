package com.yap.yappakistan.ui.onboarding.fullname

import com.yap.core.base.BaseViewModel
import com.yap.core.base.SingleClickEvent
import com.yap.yappakistan.ui.onboarding.fullname.INameVerification
import com.yap.yappakistan.ui.onboarding.fullname.NameVerificationState
import com.yap.yappakistan.utils.NameValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NameVerificationVM @Inject constructor(
    override val viewState: NameVerificationState,
    val nameValidator: NameValidator
) : BaseViewModel<INameVerification.State>(), INameVerification.ViewModel {
    override val clickEvent: SingleClickEvent = SingleClickEvent()

    fun onFistNameTextChanged(
        s: CharSequence, start: Int, before: Int,
        count: Int
    ) {
        viewState.isValid.value =
            validateFullName(s.toString(), viewState.lastName.value ?: "")
    }

    fun onLastNameTextChanged(
        s: CharSequence, start: Int, before: Int,
        count: Int
    ) {
        viewState.isValid.value =
            validateFullName(viewState.firstName.value ?: "", s.toString())
    }

    fun validateFullName(firstName: String, lastName: String): Boolean {
        return nameValidator.firstNameValidate(firstName) && nameValidator.lastNameValidate(lastName)
    }
}