package co.yap.yapcore.helpers

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.view.View
import android.view.animation.*
import android.view.animation.AnimationUtils
import androidx.annotation.AnimRes
import androidx.annotation.AnimatorRes
import co.yap.yapcore.animations.animators.*

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

    fun scale(
        view: View,
        duration: Long? = 500,
        from: Float,
        to: Float,
        interpolator: Interpolator? = LinearInterpolator()
    ): AnimatorSet =
        ScaleAnimator(from, to, interpolator).with(view, duration)

    fun pulse(view: View, duration: Long? = 500): AnimatorSet = PulseAnimator().with(view, duration)

    fun bounce(view: View, duration: Long? = 500, from: Float, to: Float): AnimatorSet =
        scale(view, duration, from, to, OvershootInterpolator())

    fun slideVertical(
        view: View,
        duration: Long? = 500,
        from: Float,
        to: Float,
        interpolator: Interpolator? = DecelerateInterpolator()
    ): AnimatorSet = TranslateAnimator("y", from, to, interpolator).with(view, duration)

    fun slideHorizontal(
        view: View,
        duration: Long? = 500,
        from: Float,
        to: Float,
        interpolator: Interpolator? = DecelerateInterpolator()
    ): AnimatorSet = TranslateAnimator("x", from, to, interpolator).with(view, duration)

    /**
     * Translate and FadeIn animation running in parallel on the view
     */
    fun jumpInAnimation(
        view: View,
        duration: Long? = 500,
        from: Float? = view.y + 300,
        to: Float? = view.y
    ): AnimatorSet =
        runTogether(fadeIn(view, 200), slideVertical(view, duration!!, from!!, to!!, OvershootInterpolator()))

    /**
     * Bounce and FadeIn animation running in parallel on the view
     */
    fun outOfTheBoxAnimation(view: View): AnimatorSet = runTogether(fadeIn(view, 150), bounce(view, 300, 0.5f, 1f))

    fun valueCounter(inital: Int, final: Int, duration: Long? = 500) = ValueAnimator.ofInt(inital, final).apply {
        this.duration = duration!!
    }


}