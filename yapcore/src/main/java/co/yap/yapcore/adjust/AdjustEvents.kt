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
    INVITER(YAPApplication.configManager?.getAdjustEvent(AdjustEvent.INVITER) ?: ""),
    //    TODO move into configManager.getAdjustEvent
    HOUSE_HOLD_MAIN_USER_SUBSCRIPTION("pcqzve"),
    HOUSE_HOLD_MAIN_SUB_PLAN_CONFIRM("8d6ih4"),
    HOUSE_HOLD_MAIN_PLAN_NAME("cbdm7f"),
    HOUSE_HOLD_MAIN_SHARE("1ud3kv"),
    ONBOARDING_START_NEW_HH_USER("w82q34"),//need to confirm the flow
    ONBOARDING_NEW_HH_USER_MAIN_USER_PHONE_CORRECT("mril67"),
    ONBOARDING_NEW_HH_USER_EMAIL("bp0dv8"),
    ONBOARDING_NEW_HH_USER_SIGNUP("f7b3ci"),
    ONBOARDING_NEW_HH_USER_EID("xtq06k"),
    ONBOARDING_NEW_HH_USER_EID_DECLINED("la5oeu"),
    ONBOARD_NEW_HH_USER_ONBOARDING_SUCCESS("c2x7a8"),
    HH_USER_ACCOUNT_ACTIVE("29lifk"),
    HH_USER_EXISTING_ACCOUNT_ACTIVE("l3lh9r");
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
