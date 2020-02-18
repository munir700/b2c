package co.yap.modules.kyc.viewmodels

import android.app.Application
import co.yap.modules.onboarding.interfaces.IInformationError
import co.yap.modules.onboarding.states.InformationErrorState
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.helpers.DateUtils

class InformationErrorViewModel(application: Application) :
    KYCChildViewModel<IInformationError.State>(application),
    IInformationError.ViewModel {
    override val state: InformationErrorState = InformationErrorState(application)
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override var countryName: String = " "

    override fun onCreate() {
        super.onCreate()
        state.buttonTitle = getString(Strings.screen_kyc_information_error_button_go_to_dashboard)
    }

    override fun onResume() {
        super.onResume()
        populateState()
    }

    override fun handlePressOnGoToDashboard() {
        clickEvent.call()
    }

    private fun populateState() {
        if (countryName.isEmpty()) {
            parentViewModel?.identity?.let {
                val expiry = it.expirationDate.run { DateUtils.toDate(day, month, year) }
                when {
                    DateUtils.isDatePassed(expiry) -> state.apply {
                        errorTitle =
                            getString(Strings.screen_kyc_information_error_display_text_title_expired_card)
                        errorGuide =
                            getString(Strings.screen_kyc_information_error_display_text_explanation_expired_card)
                    }
                    DateUtils.getAge(it.dateOfBirth.run {
                        DateUtils.toDate(
                            day,
                            month,
                            year
                        )
                    }) < 18 -> state.apply {
                        errorTitle =
                            getString(Strings.screen_kyc_information_error_display_text_title_under_age)
                        errorGuide =
                            getString(Strings.screen_kyc_information_error_display_text_explanation_under_age)
                    }
                    it.nationality.equals("USA", true) -> state.apply {
                        errorTitle = "We're so sorry"//countryName
                        errorGuide =
                            "Unfortunately, we cannot go ahead with creating your account at this time. Thank you for your interest in YAP."
//                        errorGuide = getString(Strings.screen_kyc_information_error_display_text_explanation_from_usa)
                    }
                    else -> {
                    }
                }
            }
        } else {
            state.errorTitle = "We're so sorry"
            state.errorGuide =
                "Unfortunately, we cannot go ahead with creating your account at this time. Thank you for your interest in YAP."
        }
    }
}