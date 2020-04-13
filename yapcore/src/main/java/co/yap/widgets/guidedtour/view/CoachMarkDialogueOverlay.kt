package co.yap.widgets.guidedtour.view

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.RelativeLayout
import co.yap.widgets.guidedtour.models.GuidedTourViewDetail
import co.yap.yapcore.R

class CoachMarkDialogueOverlay(
    internal val context: Context,
    private val guidedTourViewViewsList: ArrayList<GuidedTourViewDetail>
) : Dialog(context, android.R.style.Theme_Light) {
    private var circleView: CircleOverlayView? = null
    private var rootMain: RelativeLayout? = null
    //private val layer = dialog.findViewById(R.id.layerView) as View

    var currentViewId: Int = 0
    var previousViewId = currentViewId

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
        updateCircle()
    }

    override fun setOnShowListener(listener: DialogInterface.OnShowListener?) {
        super.setOnShowListener(listener)
        //updateCircle()
    }

    override fun setOnDismissListener(listener: DialogInterface.OnDismissListener?) {
        super.setOnDismissListener(listener)
        if (currentViewId < guidedTourViewViewsList.size) {
            currentViewId += 1
        }
    }

    init {
        setCancelable(false)
    }

    fun updateCircle() {
        getCurrentItem()?.let {
            circleView?.centerX = it.pointX.toFloat()
            circleView?.centerY = it.pointY.toFloat()
        }
    }

    private fun getCurrentItem(): GuidedTourViewDetail? {
        return guidedTourViewViewsList[currentViewId]
    }
}


