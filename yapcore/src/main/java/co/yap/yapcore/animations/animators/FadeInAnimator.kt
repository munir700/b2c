package co.yap.yapcore.animations.animators

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import co.yap.yapcore.animations.CoreAnimator
import co.yap.yapcore.animations.IAnimator

class FadeInAnimator : CoreAnimator(), IAnimator {
    override fun with(view: View, duration: Long?): AnimatorSet =
        runTogether(ObjectAnimator.ofFloat(view, "alpha", 0f, 1f)).apply {
            this.duration = duration!!
        }
}