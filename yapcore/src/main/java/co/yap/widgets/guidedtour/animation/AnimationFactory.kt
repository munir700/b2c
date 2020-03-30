package co.yap.widgets.guidedtour.animation

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.view.View

object AnimationFactory {
    /**
     * MaterialIntroView will appear on screen with
     * fade in animation. Notifies onAnimationStartListener
     * when fade in animation is about to start.
     *
     * @param view
     * @param duration
     * @param onAnimationStartListener
     */
    fun animateFadeIn(
        view: View?,
        duration: Long,
        onAnimationStartListener: AnimationListener.OnAnimationStartListener?
    ) {
        val objectAnimator =
            ObjectAnimator.ofFloat(view, "alpha", 0f, 1f)
        objectAnimator.duration = duration
        objectAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
                if (onAnimationStartListener != null) onAnimationStartListener.onAnimationStart()
            }

            override fun onAnimationEnd(animation: Animator) {}
            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        })
        objectAnimator.start()
    }

    /**
     * MaterialIntroView will disappear from screen with
     * fade out animation. Notifies onAnimationEndListener
     * when fade out animation is ended.
     *
     * @param view
     * @param duration
     * @param onAnimationEndListener
     */
    fun animateFadeOut(
        view: View?,
        duration: Long,
        onAnimationEndListener: AnimationListener.OnAnimationEndListener?
    ) {
        val objectAnimator =
            ObjectAnimator.ofFloat(view, "alpha", 1f, 0f)
        objectAnimator.duration = duration
        objectAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}
            override fun onAnimationEnd(animation: Animator) {
                if (onAnimationEndListener != null) onAnimationEndListener.onAnimationEnd()
            }

            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        })
        objectAnimator.start()
    }

    fun performAnimation(view: View) {
        val animatorSet = AnimatorSet()
        val scaleX: ValueAnimator =
            ObjectAnimator.ofFloat(
                view,
                View.SCALE_X,
                0.6f
            )
        scaleX.repeatCount = ValueAnimator.INFINITE
        scaleX.repeatMode = ValueAnimator.REVERSE
        scaleX.duration = 1000
        val scaleY: ValueAnimator =
            ObjectAnimator.ofFloat(
                view,
                View.SCALE_Y,
                0.6f
            )
        scaleY.repeatCount = ValueAnimator.INFINITE
        scaleY.repeatMode = ValueAnimator.REVERSE
        scaleY.duration = 1000
        animatorSet.playTogether(scaleX, scaleY)
        animatorSet.start()
    }
}
