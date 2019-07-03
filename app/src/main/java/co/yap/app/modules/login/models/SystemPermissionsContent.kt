package co.yap.app.modules.login.models

import android.graphics.drawable.Drawable

data class SystemPermissionsContent(
    val icon: Int,
    val title: String,
    val termsAndConditionsVisibility: Boolean,
    val buttonTitle: String
) {
}