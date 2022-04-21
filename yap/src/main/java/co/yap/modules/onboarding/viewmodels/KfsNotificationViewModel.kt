package co.yap.modules.onboarding.viewmodels

import android.app.Application
import co.yap.modules.onboarding.interfaces.IKfsNotification
import co.yap.modules.onboarding.states.KfsNotificationState
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.yapcore.SingleClickEvent

class KfsNotificationViewModel(application: Application) :
    OnboardingChildViewModel<IKfsNotification.State>(application), IKfsNotification.ViewModel,
    IRepositoryHolder<CustomersRepository> {
    override val state: KfsNotificationState = KfsNotificationState()
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override val repository: CustomersRepository = CustomersRepository

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

}