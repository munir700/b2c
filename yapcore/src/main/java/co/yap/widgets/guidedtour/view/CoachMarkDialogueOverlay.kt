package co.yap.widgets.guidedtour.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Dialog
import android.content.Context
import android.view.View
import android.view.Window
import android.view.WindowManager
import co.yap.widgets.CoreCircularImageView
import co.yap.yapcore.R

class CoachMarkDialogueOverlay(context: Context) : Dialog(context) {
    init {

        val dialog = Dialog(context, android.R.style.Theme_Light)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialogue_coach_mark_overlay)
        dialog.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        val circleView = dialog.findViewById(R.id.circleView) as CoreCircularImageView
        val layer = dialog.findViewById(R.id.layer) as View

        dialog.setOnShowListener {
            circleView.visibility = View.VISIBLE
            layer.visibility = View.VISIBLE
            layer.alpha = 0f
            layer.animate().alpha(0.6f).setDuration(300)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        super.onAnimationEnd(animation)
                        layer.visibility = View.VISIBLE
                    }
                })
        }
        dialog.setOnDismissListener {
        }
        dialog.show()
    }

}