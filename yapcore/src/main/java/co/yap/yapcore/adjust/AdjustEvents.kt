package co.yap.yapcore.adjust

import androidx.annotation.Keep
import co.yap.app.YAPApplication
import co.yap.yapcore.leanplum.KYCEvents

@Keep
enum class AdjustEvents(val type: String) {
    KYC_END(YAPApplication.configManager?.getAdjustEvent(AdjustEvent.KYC_END) ?: ""),
    KYC_START(YAPApplication.configManager?.getAdjustEvent(AdjustEvent.KYC_START) ?: ""),
    SET_PIN_END(YAPApplication.configManager?.getAdjustEvent(AdjustEvent.SET_PIN_END) ?: ""),
    SET_PIN_START(YAPApplication.configManager?.getAdjustEvent(AdjustEvent.SET_PIN_START) ?: ""),
    SIGN_UP_MOBILE_NUMBER_VERIFIED(
        YAPApplication.configManager?.getAdjustEvent(AdjustEvent.SIGN_UP_MOBILE_NUMBER_VERIFIED)
            ?: ""
    ),
    SIGN_UP_END(YAPApplication.configManager?.getAdjustEvent(AdjustEvent.SIGN_UP_END) ?: ""),
    SIGN_UP_START(YAPApplication.configManager?.getAdjustEvent(AdjustEvent.SIGN_UP_START) ?: ""),
    TOP_UP_END(YAPApplication.configManager?.getAdjustEvent(AdjustEvent.TOP_UP_END) ?: ""),
    TOP_UP_START(YAPApplication.configManager?.getAdjustEvent(AdjustEvent.TOP_UP_START) ?: ""),
    INVITER(YAPApplication.configManager?.getAdjustEvent(AdjustEvent.INVITER) ?: "");
}

sealed class AdjustSealed{
    object KycStart:AdjustSealed()
}
fun AdjustSealed.KycStart.log(){
    when(YAPApplication.configManager?.flavor){
        ""->""
        ""->""
        ""->""
    }
}

class Ab{
    fun abc(){
        AdjustSealed.KycStart.log()
    }
}


@Keep
enum class AdjustEvent {
    KYC_END,
    KYC_START,
    SET_PIN_END,
    SET_PIN_START,
    SIGN_UP_MOBILE_NUMBER_VERIFIED,
    SIGN_UP_END,
    SIGN_UP_START,
    TOP_UP_END,
    TOP_UP_START,
    INVITER;
}