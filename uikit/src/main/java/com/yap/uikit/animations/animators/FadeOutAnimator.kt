package com.yap.uikit.animations.animators

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import com.yap.uikit.animations.Animator


class FadeOutAnimator : Animator() {
    override fun with(view: View, duration: Long?): AnimatorSet =
        runTogether(ObjectAnimator.ofFloat(view, "alpha", 1f, 0f)).apply {
            this.duration = duration!!
        }
}