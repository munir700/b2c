package co.yap.modules.onboarding.enums

import androidx.annotation.Keep

@Keep
sealed class OnboardingPhase(val id: Int) {
    object EVENT_POST_VERIFICATION_EMAIL : OnboardingPhase(0)
    object NOTIFICATION_KFS_FLOW : OnboardingPhase(1)
    object NOTIFICATION_SELECTED : OnboardingPhase(2)
    object EVENT_POST_DEMOGRAPHIC : OnboardingPhase(3)
    object EVENT_NAVIGATE_NEXT : OnboardingPhase(4)
}




