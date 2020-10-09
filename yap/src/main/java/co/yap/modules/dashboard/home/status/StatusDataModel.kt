package co.yap.modules.dashboard.home.status

import android.graphics.drawable.Drawable

data class StatusDataModel(
    val position: Int? = 0,
    val statusTitle: String? = null,
    val statusDescription: String? = null,
    val statusAction: String? = null,
    val statusDrawable: Drawable,
    val progressStatus: NotificationProgressStatus


)