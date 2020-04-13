package co.yap.widgets.guidedtour.view

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.RelativeLayout
import co.yap.widgets.guidedtour.models.GuidedTourViewDetail
import co.yap.yapcore.R

class CoachMarkDialogueOverlay(
    internal val context: Context,
    private val guidedTourViewViewsList: ArrayList<GuidedTourViewDetail>
) : Dialog(context, android.R.style.Theme_Light) {
    private var circleView: CircleOverlayView? = null
    private var rootMain: RelativeLayout? = null
    private var skip: Button? = null
    //private var layer = dialog.findViewById(R.id.layerView) as View

    var currentViewId: Int = 0
    var previousViewId = currentViewId

    init {
        setCancelable(true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialogue_coach_mark_overlay)
        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        window?.setBackgroundDrawableResource(android.R.color.transparent)
        circleView = findViewById(R.id.circleView)
        rootMain = findViewById(R.id.rlMain)
        skip = findViewById(R.id.skip)
        skip?.setOnClickListener {
            moveNext()
        }
        updateCircle()
    }

    private fun moveNext() {
        if (currentViewId < guidedTourViewViewsList.size) {
            previousViewId = currentViewId
            currentViewId += 1
            updateCircle()
        } else {
            dismiss()
        }
    }

    private fun updateCircle() {
        getCurrentItem()?.let {
            circleView?.centerX = it.pointX.toFloat() + (it.view.width.toFloat() / 2)
            circleView?.centerY = it.pointY.toFloat() - (it.view.height.toFloat() / 2)
            circleView?.invalidate()
        }
    }

    private fun getCurrentItem(): GuidedTourViewDetail? {
        return if (!guidedTourViewViewsList.isNullOrEmpty() && currentViewId < guidedTourViewViewsList.size) guidedTourViewViewsList[currentViewId] else null
    }
}


