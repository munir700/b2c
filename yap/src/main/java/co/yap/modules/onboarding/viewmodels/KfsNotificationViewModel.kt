package co.yap.modules.onboarding.viewmodels

import android.app.Application
import co.yap.modules.onboarding.interfaces.IKfsNotification
import co.yap.modules.onboarding.states.KfsNotificationState
import co.yap.yapcore.SingleClickEvent

class KfsNotificationViewModel(application: Application) :
    OnboardingChildViewModel<IKfsNotification.State>(application), IKfsNotification.ViewModel {
    override val state: KfsNotificationState = KfsNotificationState()
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override fun handleOnPressView(id: Int) {
        clickEvent.setValue(id)
    }

    fun getAllAppNotificationSettings(): Boolean = state.inappNotificationAccepted.value == true
            && state.smsNotificationAccepted.value == true
            && state.emailNotificationAccepted.value == true

    fun revertAllAppNotifications() {
        state.inappNotificationAccepted.value = false
        state.smsNotificationAccepted.value = false
        state.emailNotificationAccepted.value = false
        state.allNotificationAccepted.value = false
        state.noNotificationAccepted.value = true

    }

    fun enableAllAppNotifications() {
        state.inappNotificationAccepted.value = true
        state.smsNotificationAccepted.value = true
        state.emailNotificationAccepted.value = true
        state.allNotificationAccepted.value = true
        state.noNotificationAccepted.value = false
    }
    fun isAnyNotificationSelected(): Boolean = state.inappNotificationAccepted.value == true
            || state.smsNotificationAccepted.value == true
            || state.emailNotificationAccepted.value == true
}