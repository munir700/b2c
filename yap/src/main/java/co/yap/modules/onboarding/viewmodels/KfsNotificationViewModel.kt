package co.yap.modules.onboarding.viewmodels

import android.app.Application
import co.yap.modules.onboarding.enums.NotificationType
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
import co.yap.yapcore.leanplum.trackKfsWithAttributes
import java.util.*

class KfsNotificationViewModel(application: Application) :
    OnboardingChildViewModel<IKfsNotification.State>(application), IKfsNotification.ViewModel,
    IRepositoryHolder<CustomersRepository> {
    override val state: KfsNotificationState = KfsNotificationState()
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override val repository: CustomersRepository = CustomersRepository
    val notificationRepository: NotificationsApi = NotificationsRepository

    override fun onCreate() {
        super.onCreate()
        state.notificationMap.clear()
    }

    override fun onResume() {
        super.onResume()
        setProgress(80)
    }

    override fun handleOnPressView(id: Int) {
        clickEvent.setValue(id)
    }

    fun setUpNotificationOnCheck(
        key: NotificationType,
        checked: Boolean,
        enableAll: Boolean,
        checkSelection: (MutableMap<NotificationType, Boolean?>) -> Unit
    ) {
        state.notificationMap[key] =
            when (key) {
                NotificationType.NONE_NOTIFICATION -> checked
                NotificationType.SMS_NOTIFICATION, NotificationType.EMAIL_NOTIFICATION, NotificationType.IN_APP_NOTIFICATION -> {
                    if (checked) {
                        state.notificationMap[NotificationType.NONE_NOTIFICATION] = false
                        state.notificationMap[NotificationType.ALL_NOTIFICATION] = true
                    }
                    checked && state.notificationMap[NotificationType.ALL_NOTIFICATION] ?: false
                }
                NotificationType.ALL_NOTIFICATION -> {
                    if (checked.not() && isAnyOfNotificationSelected()) setAllAppNotifications(
                        enableAll = false,
                        enableNone = false
                    ) else if (checked && isAnyOfNotificationSelected().not()) setAllAppNotifications(
                        enableAll = true,
                        enableNone = false
                    )
                    checked
                }
            }
        state.notificationMap[NotificationType.ALL_NOTIFICATION] =
            enableAll || (state.notificationMap[NotificationType.ALL_NOTIFICATION] ?: false && isAnyOfNotificationSelected())

        setNotificationSelection(checked)
        if (key == NotificationType.NONE_NOTIFICATION && state.notificationMap[NotificationType.NONE_NOTIFICATION] == true) setAllAppNotifications(
            enableAll = false
        )
        checkSelection.invoke(state.notificationMap)
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
                    kfsAcceptedTimeStamp = DateUtils.getCurrentDateWithFormat(
                        DateUtils.SERVER_DATE_FULL_FORMAT,
                        TimeZone.getTimeZone("UTC")
                    )
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

                    trackEvent(
                        SignupEvents.SIGN_UP_EMAIL.type,
                        parentViewModel?.onboardingData?.email ?: ""
                    )
                    trackEventWithScreenName(FirebaseEvent.SIGNUP_EMAIL)
                    SharedPreferenceManager.getInstance(context)
                        .saveUserNameWithEncryption(parentViewModel?.onboardingData?.email ?: "")
                    saveNotificationSettings { isNotificationSaved ->
                        state.isNotificationSaved.value = isNotificationSaved
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

    fun saveNotificationSettings(success: (isSaved: Boolean) -> Unit) {
        launch {
            when (val response = notificationRepository.saveNotificationSettings(
                NotificationSettings(
                    emailEnabled = state.notificationMap[NotificationType.EMAIL_NOTIFICATION]
                        ?: false,
                    inAppEnabled = state.notificationMap[NotificationType.IN_APP_NOTIFICATION]
                        ?: false,
                    smsEnabled = state.notificationMap[NotificationType.SMS_NOTIFICATION] ?: false,
                    optIn = isOptTrue()
                )
            )) {
                is RetroApiResponse.Success -> {
                    parentViewModel?.state?.notificationAction?.value =
                        OnboardingPhase.NOTIFICATION_SELECTED
                    setNotificationSelection(state.valid.get())
                    trackKfsWithAttributes(
                        state.notificationMap[NotificationType.SMS_NOTIFICATION] ?: false,
                        state.notificationMap[NotificationType.EMAIL_NOTIFICATION] ?: false,
                        state.notificationMap[NotificationType.IN_APP_NOTIFICATION] ?: false
                    )
                    success.invoke(true)
                    state.loading = false
                }
                is RetroApiResponse.Error -> {
                    state.loading = false
                    success.invoke(false)
                    state.toast = response.error.message
                }
            }
        }
    }

    fun setAllAppNotifications(enableAll: Boolean, enableNone: Boolean = true) {
        with(state) {
            notificationMap.putAll(
                mutableListOf(
                    NotificationType.ALL_NOTIFICATION to enableAll,
                    NotificationType.SMS_NOTIFICATION to enableAll,
                    NotificationType.EMAIL_NOTIFICATION to enableAll,
                    NotificationType.IN_APP_NOTIFICATION to enableAll,
                    NotificationType.NONE_NOTIFICATION to enableNone,
                )
            )
        }
    }


    fun isAnyOfNotificationSelected(): Boolean =
        state.notificationMap[NotificationType.SMS_NOTIFICATION] == true
                || state.notificationMap[NotificationType.EMAIL_NOTIFICATION] == true
                || state.notificationMap[NotificationType.IN_APP_NOTIFICATION] == true

    fun isOptTrue() =
        state.notificationMap[NotificationType.NONE_NOTIFICATION] == false && isAnyOfNotificationSelected()

}