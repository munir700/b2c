package co.yap.modules.dashboard.more.notifications.setting

import android.app.Application
import android.widget.CompoundButton
import co.yap.R
import co.yap.yapcore.BaseViewModel

class NotificationSettingsViewModel(application: Application) :
    BaseViewModel<INotificationSettings.State>(application = application),
    INotificationSettings.ViewModel {
    override val state: NotificationSettingsState =
        NotificationSettingsState()

    fun onSwitchChanged(switch: CompoundButton, isChecked: Boolean) {
        if (switch.isPressed) {
            when (switch.id) {
                R.id.swWithdrawal -> {
                    //updateAllowAtm()
                }
                R.id.swOnlineTra -> {
//                    updateOnlineTransaction()
                }
                R.id.swAbroad -> {
//                    updateAbroadPayment()
                }
                R.id.swRetail -> {
//                    updateRetailPayment()
                }
            }
        }
    }
}