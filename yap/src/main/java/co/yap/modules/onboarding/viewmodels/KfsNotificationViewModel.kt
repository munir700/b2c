package co.yap.modules.onboarding.viewmodels

import android.app.Application
import co.yap.modules.onboarding.enums.OnboardingPhase
import co.yap.modules.onboarding.interfaces.IKfsNotification
import co.yap.modules.onboarding.states.KfsNotificationState
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.requestdtos.SignUpRequest
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.notification.NotificationsApi
import co.yap.networking.notification.NotificationsRepository
import co.yap.networking.notification.responsedtos.NotificationSettings
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.firebase.FirebaseEvent
import co.yap.yapcore.firebase.trackEventWithScreenName
import co.yap.yapcore.helpers.DateUtils
import co.yap.yapcore.helpers.SharedPreferenceManager
import co.yap.yapcore.helpers.extentions.toast
import co.yap.yapcore.leanplum.SignupEvents
import co.yap.yapcore.leanplum.trackEvent

class KfsNotificationViewModel(application: Application) :
    OnboardingChildViewModel<IKfsNotification.State>(application), IKfsNotification.ViewModel,
    IRepositoryHolder<CustomersRepository> {
    override val state: KfsNotificationState = KfsNotificationState()
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override val repository: CustomersRepository = CustomersRepository
    val notificationRepository: NotificationsApi = NotificationsRepository
    override fun onResume() {
        super.onResume()
        setProgress(80)
    }

    override fun handleOnPressView(id: Int) {
        clickEvent.setValue(id)
    }

    fun getAllAppNotificationSettings(): Boolean = parentViewModel?.state?.let { it ->
        with(it) {
            inappNotificationAccepted.value == true
                    && smsNotificationAccepted.value == true
                    && emailNotificationAccepted.value == true
        }
    } == true

    fun revertAllAppNotifications() {
        parentViewModel?.state?.let { it ->
            with(it) {
                inappNotificationAccepted.value = false
                smsNotificationAccepted.value = false
                emailNotificationAccepted.value = false
                allNotificationAccepted.value = false
                noNotificationAccepted.value = true
            }
        }

    }

    fun enableAllAppNotifications() {
        parentViewModel?.state?.let { it ->
            with(it) {
                inappNotificationAccepted.value = true
                smsNotificationAccepted.value = true
                emailNotificationAccepted.value = true
                allNotificationAccepted.value = true
                noNotificationAccepted.value = false
            }
        }
    }

    fun signUp(success: () -> Unit) {
        launch {
            state.loading = true
            when (val response = repository.signUp(
                SignUpRequest(
                    parentViewModel?.onboardingData?.firstName,
                    parentViewModel?.onboardingData?.lastName,
                    parentViewModel?.onboardingData?.countryCode,
                    parentViewModel?.onboardingData?.mobileNo,
                    parentViewModel?.onboardingData?.email,
                    parentViewModel?.onboardingData?.passcode,
                    parentViewModel?.onboardingData?.accountType.toString(),
                    token = parentViewModel?.onboardingData?.token,
                    kfsAcceptedTimeStamp = DateUtils.getCurrentDateWithFormat(DateUtils.SERVER_DATE_FORMAT)
                )
            )) {
                is RetroApiResponse.Success -> {
                    SharedPreferenceManager.getInstance(context).save(
                        Constants.KEY_IS_USER_LOGGED_IN,
                        true
                    )

                    parentViewModel?.onboardingData?.passcode?.let { passCode ->
                        SharedPreferenceManager.getInstance(context)
                            .savePassCodeWithEncryption(passCode)
                    } ?: toast(context, "Invalid pass code")

                    trackEvent(SignupEvents.SIGN_UP_EMAIL.type,parentViewModel?.onboardingData?.email ?: "")
                    trackEventWithScreenName(FirebaseEvent.SIGNUP_EMAIL)
                    SharedPreferenceManager.getInstance(context)
                        .saveUserNameWithEncryption(parentViewModel?.onboardingData?.email ?: "")
                    saveNotificationSettings {
                        success.invoke()
                    }
                }

                is RetroApiResponse.Error -> {
                    state.loading = false
                    state.toast = response.error.message
                    parentViewModel?.emailError?.value = response.error.message
                    parentViewModel?.state?.emailError = true
                }
            }
        }
    }

    fun saveNotificationSettings(success: () -> Unit) {
        launch {
            when (val response = notificationRepository.saveNotificationSettings(
                NotificationSettings(
                    emailEnabled = parentViewModel?.state?.emailNotificationAccepted?.value,
                    inAppEnabled = parentViewModel?.state?.inappNotificationAccepted?.value,
                    smsEnabled = parentViewModel?.state?.smsNotificationAccepted?.value,
                    optIn = isOptTrue()
                )
            )) {
                is RetroApiResponse.Success -> {
                    parentViewModel?.state?.notificationAction?.value =
                        OnboardingPhase.NOTIFICATION_SELECTED
                    success.invoke()
                    state.loading = false
                }
                is RetroApiResponse.Error -> {
                    state.loading = false
                    state.toast = response.error.message
                }
            }
        }
    }

    fun isOptTrue() =
        parentViewModel?.state?.noNotificationAccepted?.value == false && isAnyNotificationSelected()
}