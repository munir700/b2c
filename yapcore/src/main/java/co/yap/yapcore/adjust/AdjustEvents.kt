package co.yap.yapcore.adjust

import androidx.annotation.Keep

@Keep
enum class AdjustEvents(val type: String) {
    //DELIVERY_CONFIRMED("66c7tk"),
    KYC_END("8r4o52"),
    KYC_START("kelb07"),
    SET_PIN_END("7vzpfo"),
    SET_PIN_START("i3m1cv"),
    SIGN_UP_MOBILE_NUMBER_VERIFIED("kx5hl6"),
    SIGN_UP_END("skzf2k"),
    SIGN_UP_START("w6rmpa"),
    TOP_UP_END("6yum4e"),
    TOP_UP_START("vquxsb"),
    INVITER("efnby4");
}