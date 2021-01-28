package co.yap.modules.dashboard.more.notifications.setting

import android.app.Application
import android.widget.CompoundButton
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.notification.NotificationsApi
import co.yap.networking.notification.NotificationsRepository
import co.yap.networking.notification.responsedtos.NotificationSettings
import co.yap.yapcore.BaseViewModel

class NotificationSettingsViewModel(application: Application) :
    BaseViewModel<INotificationSettings.State>(application = application),
    INotificationSettings.ViewModel {
    override val state: NotificationSettingsState =
        NotificationSettingsState()
    override val repository: NotificationsApi = NotificationsRepository
    override fun onCreate() {
        super.onCreate()
        getNotificationSettings()
    }

    override fun getNotificationSettings() {
        launch {
            state.loading = true
            when (val response = repository.getNotificationSettings()) {
                is RetroApiResponse.Success -> {
                    state.emailNotificationsAllowed = response.data.data?.emailEnabled
                    state.inAppNotificationsAllowed = response.data.data?.inAppEnabled
                    state.smsNotificationsAllowed = response.data.data?.smsEnabled
                    state.loading = false
                }
                is RetroApiResponse.Error -> {
                    state.loading = false
                    state.toast = response.error.message
                }
            }
        }
    }

    override fun saveNotificationSettings() {
        launch {
            state.loading = true
            when (val response = repository.saveNotificationSettings(
                NotificationSettings(
                    state.emailNotificationsAllowed,
                    state.inAppNotificationsAllowed,
                    state.smsNotificationsAllowed
                )
            )) {
                is RetroApiResponse.Success -> {
                    state.loading = false
                }
                is RetroApiResponse.Error -> {
                    state.loading = false
                    state.toast = response.error.message
                }
            }
        }
    }

    fun onSwitchChanged(switch: CompoundButton, isChecked: Boolean) {
        saveNotificationSettings()
    }
}