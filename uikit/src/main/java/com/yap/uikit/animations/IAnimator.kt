package com.yap.uikit.animations

import android.animation.AnimatorSet
import android.view.View

interface IAnimator {
    fun with(view: View, duration: Long? = 500): AnimatorSet
}