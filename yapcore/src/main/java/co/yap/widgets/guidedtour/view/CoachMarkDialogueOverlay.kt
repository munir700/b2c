package co.yap.widgets.guidedtour.view

import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.RelativeLayout
import co.yap.widgets.guidedtour.models.GuidedTourViewDetail
import co.yap.yapcore.R

class CoachMarkDialogueOverlay(
    context: Context,
    guidedTourViewViewsList: ArrayList<GuidedTourViewDetail>
) :
    Dialog(context) {
    init {
        var guidedTourViewViewsList = guidedTourViewViewsList
        var guidedTourViewDetail: GuidedTourViewDetail? = null
        var currentViewId: Int = 0
        var previousViewId: Int = 0


        previousViewId = currentViewId
        if (currentViewId < guidedTourViewViewsList.size) {

            guidedTourViewDetail = guidedTourViewViewsList[currentViewId]

            currentViewId = currentViewId + 1

        }


        val dialog = Dialog(context, android.R.style.Theme_Light)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialogue_coach_mark_overlay)
        dialog.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        val circleView = dialog.findViewById(R.id.circleView) as CircleOverlayView
        val rootMain = dialog.findViewById(R.id.rlMain) as RelativeLayout
        val layer = dialog.findViewById(R.id.layerView) as View


        if (guidedTourViewDetail != null) {
            circleView.centerX = guidedTourViewDetail.pointX.toFloat()
            circleView.centerY = guidedTourViewDetail.pointY.toFloat()
            Log.i("CoachMarkDialogue", circleView.centerX.toString())
            Log.i("CoachMarkDialogue", circleView.centerY.toString())
            Log.i("CoachMarkDialogue", guidedTourViewDetail?.title.toString())
        }


        dialog.setOnShowListener {
            layer.visibility = View.VISIBLE
        }
        dialog.setOnDismissListener {
        }
        dialog.show()
    }


}