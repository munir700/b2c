package co.yap.yapcore.helpers

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.Interpolator
import android.view.animation.OvershootInterpolator
import androidx.annotation.AnimRes
import androidx.annotation.AnimatorRes
import co.yap.yapcore.animations.animators.*
import com.daimajia.easing.Glider
import com.daimajia.easing.Skill

object AnimationUtils {

    /**
     * Run a set of Animators in Parallel
     */
    fun runTogether(vararg animator: Animator): AnimatorSet = AnimatorSet().apply { playTogether(*animator) }

    /**
     * Run a set of Animators in sequence
     */
    fun runSequentially(vararg animator: Animator): AnimatorSet = AnimatorSet().apply { playSequentially(*animator) }


    /**
     * Load Animator from resource animator and apply on the view
     */
    fun loadAnimator(context: Context, @AnimatorRes animatorResId: Int, view: View): AnimatorSet {
        return (AnimatorInflater.loadAnimator(context, animatorResId) as AnimatorSet)
            .apply {
                setTarget(view)
            }
    }

    fun loadAnimation(context: Context, @AnimRes resId: Int): Animation = AnimationUtils.loadAnimation(context, resId)


    fun fadeIn(view: View, duration: Long? = 500): AnimatorSet = FadeInAnimator().with(view, duration)
    fun fadeOut(view: View, duration: Long? = 500): AnimatorSet = FadeOutAnimator().with(view, duration)
    fun scale(view: View, duration: Long? = 500, from: Float, to: Float): AnimatorSet = ScaleAnimator(from, to).with(view, duration)
    fun bounce(view: View, duration: Long? = 500): AnimatorSet = BounceAnimator().with(view, duration)
    fun translateY(view: View, duration: Long? = 500, from: Float, to: Float): AnimatorSet = TranslateAnimator("y", from, to).with(view, duration)
    fun translateX(view: View, duration: Long? = 500, from: Float, to: Float): AnimatorSet = TranslateAnimator("x", from, to).with(view, duration)

    /**
     * Translate and FadeIn animation running in parallel on the view
     */
    fun enterSlideAnimation(
        view: View,
        duration: Long? = 500,
        from: Float? = view.y + 300,
        to: Float? = view.y,
        interpolator: Interpolator? = OvershootInterpolator()
    ): AnimatorSet = runTogether(fadeIn(view, 200), translateY(view, duration!!, from!!, to!!).apply {
        this.interpolator = interpolator
    })

    /**
     * Translate and FadeIn animation running in parallel on the view
     */
    fun enterScaleAnimation(view: View): AnimatorSet = runTogether(fadeIn(view, 150), scale(view, 250, 0.2f, 1.0f))



}