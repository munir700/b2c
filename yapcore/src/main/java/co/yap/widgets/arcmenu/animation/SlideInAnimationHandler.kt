package co.yap.widgets.arcmenu.animation

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.graphics.Point
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.FrameLayout
import co.yap.widgets.arcmenu.FloatingActionMenu

class SlideInAnimationHandler : MenuAnimationHandler() {

    /** holds the current state of animation  */

    private var animating: Boolean = false

    init {
        setAnimating(false)
    }

    override fun animateMenuOpening(center: Point) {
        super.animateMenuOpening(center)

        setAnimating(true)

        var lastAnimation: Animator? = null

        for (i in menu!!.subActionItems.indices) {

            menu!!.subActionItems[i].view.alpha = 0f

            val params =
                menu!!.subActionItems[i].view.layoutParams as FrameLayout.LayoutParams
            params.setMargins(
                menu?.subActionItems?.get(i)?.x!!,
                menu?.subActionItems?.get(i)?.y!! + DIST_Y,
                0,
                0
            )
            menu!!.subActionItems[i].view.setLayoutParams(params)

            //            PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat(View.TRANSLATION_X, menu.getSubActionItems().get(i).x/* - center.x + menu.getSubActionItems().get(i).width / 2*/);
            val pvhY = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, -DIST_Y.toFloat())
            //            PropertyValuesHolder pvhsX = PropertyValuesHolder.ofFloat(View.SCALE_X, 1);
            //            PropertyValuesHolder pvhsY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1);
            val pvhA = PropertyValuesHolder.ofFloat(View.ALPHA, 0f, 1f)

            val animation = ObjectAnimator.ofPropertyValuesHolder(
                menu!!.subActionItems[i].view,
                pvhY,
                pvhA
            )
            animation.duration = DURATION.toLong()
            animation.interpolator = DecelerateInterpolator()
            animation.addListener(
                SubActionItemAnimationListener(
                    menu!!.subActionItems[i],
                    ActionType.OPENING
                )
            )

            if (i == 0) {
                lastAnimation = animation
            }
            if (i == 0) {
                animation.startDelay = (menu!!.subActionItems.size * LAG_BETWEEN_ITEMS).toLong()
            } else if (i == 1) {
                animation.startDelay = LAG_BETWEEN_ITEMS.toLong()
            } else {
                animation.startDelay =
                    ((menu!!.subActionItems.size - 1) * LAG_BETWEEN_ITEMS).toLong()
                //animation.setStartDelay((menu.getSubActionItems().size() - i) * LAG_BETWEEN_ITEMS);
            }

            //animation.setStartDelay(Math.abs(menu.getSubActionItems().size()/2-i) * LAG_BETWEEN_ITEMS);
            animation.start()
        }
        lastAnimation?.addListener(LastAnimationListener())

    }

    override fun animateMenuClosing(center: Point) {
        super.animateMenuOpening(center)

        setAnimating(true)

        var lastAnimation: Animator? = null
        for (i in menu!!.subActionItems.indices) {
            //            PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat(View.TRANSLATION_X, - (menu.getSubActionItems().get(i).x - center.x + menu.getSubActionItems().get(i).width / 2));
            val pvhY = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, DIST_Y.toFloat())
            //            PropertyValuesHolder pvhsX = PropertyValuesHolder.ofFloat(View.SCALE_X, 0);
            //            PropertyValuesHolder pvhsY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 0);
            val pvhA = PropertyValuesHolder.ofFloat(View.ALPHA, 1f, 0f)

            val animation = ObjectAnimator.ofPropertyValuesHolder(
                menu?.subActionItems?.get(i)?.view,
                pvhY,
                pvhA
            )
            animation.duration = (DURATION + 125).toLong()
            animation.interpolator = AccelerateInterpolator()
            animation.addListener(
                SubActionItemAnimationListener(
                    menu!!.subActionItems[i],
                    ActionType.CLOSING
                )
            )

            if (i == 0) {
                lastAnimation = animation
            }
            animation.start()
        }
        lastAnimation?.addListener(LastAnimationListener())
    }

    override fun isAnimating() = animating

    override fun setAnimating(animating: Boolean) {
        this.animating = animating
    }

    protected inner class SubActionItemAnimationListener(
        private val subActionItem: FloatingActionMenu.Item,
        private val actionType: ActionType
    ) : Animator.AnimatorListener {

        override fun onAnimationStart(animation: Animator) {
            menu!!.mainActionView.isClickable = false

        }

        override fun onAnimationEnd(animation: Animator) {
            menu!!.mainActionView.isClickable = true
            restoreSubActionViewAfterAnimation(subActionItem, actionType)

        }

        override fun onAnimationCancel(animation: Animator) {
            restoreSubActionViewAfterAnimation(subActionItem, actionType)
        }

        override fun onAnimationRepeat(animation: Animator) {}
    }

    companion object {
        /** duration of animations, in milliseconds  */
        protected val DURATION = 200
        /** duration to wait between each of   */
        protected val LAG_BETWEEN_ITEMS = 200

        protected val DIST_Y = 50
    }
}
