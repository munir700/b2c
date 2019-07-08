package co.yap.yapcore.animations.animators

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import co.yap.yapcore.animations.CoreAnimator
import co.yap.yapcore.animations.IAnimator

class ScaleAnimator(val from: Float, val to: Float) : CoreAnimator(), IAnimator {
    override fun with(view: View, duration: Long?): AnimatorSet =
        runTogether(
            ObjectAnimator.ofFloat(view, "scaleX", from, to),
            ObjectAnimator.ofFloat(view, "scaleY", from, to)
        ).apply { this.duration = duration!!}
}