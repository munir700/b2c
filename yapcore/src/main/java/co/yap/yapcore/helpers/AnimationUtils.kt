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

object AnimationUtils {

    /**
     * Run a set of Animators in Parallel
     */
    fun runTogether(vararg animator: Animator): AnimatorSet = AnimatorSet().apply { playTogether(*animator) }

    /**
     * Run a set of Animators in sequence
     */
    fun runSequentially(vararg animator: Animator): AnimatorSet = AnimatorSet().apply { playSequentially(*animator) }

    fun fadeIn(view: View, duration: Long): ObjectAnimator {
        return ObjectAnimator.ofFloat(view, "alpha", 0f, 1f).apply {
            this.duration = duration
        }
    }

    fun translateY(view: View, duration: Long, from: Float, to: Float): ObjectAnimator =
        ObjectAnimator.ofFloat(view, "y", from, to).apply {
            this.duration = duration
        }

    fun scale(view: View, duration: Long, from: Float, to: Float): AnimatorSet {
        val scaleX = ObjectAnimator.ofFloat(view, "scaleX", from, to).apply {
            this.duration = duration
        }

        val scaleY = ObjectAnimator.ofFloat(view, "scaleY", from, to).apply {
            this.duration = duration
        }

        return runTogether(scaleX, scaleY)
    }


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

    /**
     * Translate and FadeIn animation running in parallel on the view
     */
    fun enterSlideAnimation(
        view: View,
        duration: Long? = 500,
        from: Float? = view.y + 300,
        to: Float? = view.y,
        interpolator: Interpolator? = OvershootInterpolator()
    ): AnimatorSet {
        val fade = fadeIn(view, 200)

        // TranslateY animation
        val slide = translateY(view, duration!!, from!!, to!!).apply {
            this.interpolator = interpolator
        }

        return runTogether(fade, slide)
    }

    /**
     * Translate and FadeIn animation running in parallel on the view
     */
    fun enterScaleAnimation(view: View): AnimatorSet {
        val fade = fadeIn(view, 150)
        val slide = scale(view, 250, 0.2f, 1.0f)
        return runTogether(fade, slide)
    }

}