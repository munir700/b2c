package co.yap.app.modules.login.viewmodels

import android.annotation.TargetApi
import android.app.Application
import android.os.Build
import co.yap.app.R
import co.yap.app.constants.Constants
import co.yap.app.modules.login.interfaces.ISystemPermission
import co.yap.app.modules.login.states.SystemPermissionState
import co.yap.translation.Strings
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleLiveEvent

class SystemPermissionViewModel(application: Application) : BaseViewModel<ISystemPermission.State>(application),
    ISystemPermission.ViewModel {

    override val permissionGrantedPressEvent: SingleLiveEvent<Boolean> = SingleLiveEvent()
    override val permissionNotGrantedPressEvent: SingleLiveEvent<Boolean> = SingleLiveEvent()
    override var screenType: String = ""

    override fun onCreate() {
        super.onCreate()
        setupViews()
    }


    override fun permissionGranted() {
        permissionGrantedPressEvent.value = true
    }

    override fun permissionNotGranted() {
        permissionNotGrantedPressEvent.value = true
    }


    fun setupViews() {
        if (screenType == Constants.TOUCH_ID_SCREEN_TYPE) touchIdViews() else notificationViews()
    }

    override val state: SystemPermissionState = SystemPermissionState()

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    fun touchIdViews() {
        state.icon = R.drawable.ic_fingerprint
        state.title = getString(Strings.screen_system_permission_text_title)
        state.subTitle = getString(Strings.screen_system_permission_text_sub_title)
        state.termsAndConditionsVisibility = true
        state.buttonTitle = getString(Strings.screen_system_permission_button_touch_id)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    fun notificationViews() {
        state.icon = R.drawable.ic_notification_settings
        state.title = getString(Strings.screen_notification_permission_text_title)
        state.termsAndConditionsVisibility = false
        state.buttonTitle = getString(Strings.screen_notification_permission_button_title)
    }
}