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
import co.yap.app.modules.login.interfaces.ISystemPermission
import co.yap.app.modules.login.models.SystemPermissionsContent
import co.yap.app.modules.login.states.SystemPermissionState
import co.yap.translation.Strings
import co.yap.yapcore.BaseViewModel

class SystemPermissionViewModel(application: Application) : BaseViewModel<ISystemPermission.State>(application),
    ISystemPermission.ViewModel {
    // var screenType:String?=null

    override var screenType: String = ""

    override fun onResume() {
        super.onResume()
        setViews()
    }

    override fun setViews() {
        if (screenType == "Touch_id") touchIdViews() else notificationViews()
    }

    override val state: SystemPermissionState = SystemPermissionState()


    override fun checkFingerPrint() {

    }

    /* private fun touchIdViews(): SystemPermissionsContent {
         return SystemPermissionsContent(
             R.drawable.ic_fingerprint,
             getString(Strings.screen_system_permission_text_title),
             View.VISIBLE,
             getString(Strings.screen_system_permission_button_touch_id)
         )

     }*/
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    fun touchIdViews() {
        val drawable: Drawable = context.getDrawable(R.drawable.ic_fingerprint)
        // pending
        val bitmap: Bitmap = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.ic_fingerprint)
        state.icon = bitmap // R.drawable.ic_fingerprint
        state.title = getString(Strings.screen_system_permission_text_title)
        state.termsAndConditionsVisibility = true
        state.buttonTitle = getString(Strings.screen_system_permission_button_touch_id)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    fun notificationViews() {
        val drawable: Drawable = context.getDrawable(R.drawable.ic_notification)
        // pending
        val bitmap: Bitmap = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.ic_fingerprint)

        state.icon = bitmap// R.drawable.ic_notification
        state.title = getString(Strings.screen_notification_permission_text_title)
        state.termsAndConditionsVisibility = false
        state.buttonTitle = getString(Strings.screen_notification_permission_button_title)
    }
    /*   private fun notificationViews(): SystemPermissionsContent {
           return SystemPermissionsContent(
               R.drawable.ic_notification,
               getString(Strings.screen_notification_permission_text_title),
               View.GONE,
               getString(Strings.screen_notification_permission_button_title)
           )
       }*/


}