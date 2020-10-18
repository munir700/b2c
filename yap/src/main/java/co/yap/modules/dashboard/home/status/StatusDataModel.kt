package co.yap.modules.dashboard.home.status

import android.graphics.drawable.Drawable
import co.yap.yapcore.enums.CardDeliveryStatus

data class StatusDataModel(
    val stage: PaymentCardOnboardingStage? = null,
    val statusTitle: String? = null,
    val statusDescription: String? = null,
    val statusAction: String? = null,
    val statusDrawable: Drawable,
    val progressStatus: StageProgress
)