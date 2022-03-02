package com.yap.yappakistan.ui.onboarding.fullname

import androidx.hilt.lifecycle.ViewModelInject
import com.yap.core.base.BaseViewModel
import com.yap.core.base.SingleClickEvent
import com.yap.yappakistan.utils.NameValidator


class NameVerificationVM @ViewModelInject constructor(
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