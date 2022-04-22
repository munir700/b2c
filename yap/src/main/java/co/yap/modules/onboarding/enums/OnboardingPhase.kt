package co.yap.modules.onboarding.enums

import androidx.annotation.Keep

@Keep
sealed class OnboardingPhase(val id : Int) {
    object EVENT_NAVIGATE_NEXT : OnboardingPhase(0)
    object EVENT_POST_VERIFICATION_EMAIL : OnboardingPhase(1)
    object EVENT_POST_DEMOGRAPHIC : OnboardingPhase(2)
    object NOTIFICATION_SELECTED : OnboardingPhase(3)
    object NOTIFICATION_KFS_FLOW : OnboardingPhase(4)
    object NONE : OnboardingPhase(5)
}
