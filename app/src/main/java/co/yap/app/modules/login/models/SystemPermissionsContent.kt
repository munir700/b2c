package co.yap.app.modules.login.models

import android.graphics.drawable.Drawable
import android.opengl.Visibility

data class SystemPermissionsContent(
    val icon: Int,
    val title: String,
    val termsAndConditionsVisibility: Int,
    val buttonTitle: String
) {
}