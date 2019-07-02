package co.yap.app.modules.login.viewmodels

import android.app.Application
import co.yap.app.R
import co.yap.app.modules.login.interfaces.IBiometricPermission
import co.yap.app.modules.login.models.SystemPermissionsContent
import co.yap.app.modules.login.states.BiometricPermissionState
import co.yap.translation.Strings
import co.yap.yapcore.BaseViewModel

class BiometricPermissionViewModel(application: Application) : BaseViewModel<IBiometricPermission.State>(application),
    IBiometricPermission.ViewModel {
    // var screenType:String?=null

    override var screenType: String = ""

    override fun setViews(): SystemPermissionsContent {
        return if (screenType == "Touch_id") touchIdViews() else notificationViews()
    }

    override val state: IBiometricPermission.State
        get() = BiometricPermissionState("", true, "")

    override fun checkFingerPrint() {

    }

    private fun touchIdViews(): SystemPermissionsContent {
        return SystemPermissionsContent(
            R.drawable.ic_fingerprint,
            getString(Strings.screen_system_permission_text_title),
            true,
            getString(Strings.screen_system_permission_button_touch_id)
        )

    }

    private fun notificationViews(): SystemPermissionsContent {
        return SystemPermissionsContent(
            R.drawable.ic_notification,
            getString(Strings.screen_notification_permission_text_title),
            false,
            getString(Strings.screen_notification_permission_button_title)
        )
    }


}