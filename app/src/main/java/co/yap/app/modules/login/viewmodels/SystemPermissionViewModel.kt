package co.yap.app.modules.login.viewmodels

import android.annotation.TargetApi
import android.app.Application
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.opengl.Visibility
import android.os.Build
import android.view.View
import co.yap.app.R
import co.yap.app.constants.Constants
import co.yap.app.modules.login.interfaces.ISystemPermission
import co.yap.app.modules.login.models.SystemPermissionsContent
import co.yap.app.modules.login.states.SystemPermissionState
import co.yap.translation.Strings
import co.yap.yapcore.BaseViewModel

class SystemPermissionViewModel(application: Application) : BaseViewModel<ISystemPermission.State>(application),
    ISystemPermission.ViewModel {

    override var screenType: String = ""

    override fun onCreate() {
        super.onCreate()
        setupViews()
    }

    fun setupViews() {
        if (screenType == Constants.TOUCH_ID_SCREEN_TYPE) touchIdViews() else notificationViews()
    }

    override val state: SystemPermissionState = SystemPermissionState()

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    fun touchIdViews() {
        state.icon = R.drawable.ic_fingerprint
        state.title = getString(Strings.screen_system_permission_text_title)
        state.termsAndConditionsVisibility = true
        state.buttonTitle = getString(Strings.screen_system_permission_button_touch_id)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    fun notificationViews() {
        state.icon = R.drawable.ic_notification
        state.title = getString(Strings.screen_notification_permission_text_title)
        state.termsAndConditionsVisibility = false
        state.buttonTitle = getString(Strings.screen_notification_permission_button_title)
    }

}