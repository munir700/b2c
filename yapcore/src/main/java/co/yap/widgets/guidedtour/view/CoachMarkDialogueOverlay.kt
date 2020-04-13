package co.yap.widgets.guidedtour.view

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.RelativeLayout
import co.yap.widgets.CoreCircularImageView
import co.yap.widgets.guidedtour.models.GuidedTourViewDetail
import co.yap.yapcore.R
import co.yap.yapcore.helpers.extentions.log

class CoachMarkDialogueOverlay(
    internal val context: Context,
    private val guidedTourViewViewsList: ArrayList<GuidedTourViewDetail>
) : Dialog(context, android.R.style.Theme_Light) {
    private var layer: CircleOverlayView? = null
    private var circleView: CoreCircularImageView? = null
    private var rootMain: RelativeLayout? = null
    private var skip: Button? = null
    private val padding: Int = 100

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
        layer = findViewById(R.id.circleView)
        circleView = findViewById(R.id.focusView)
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
            log("overlaay", "x -> ${it.view.x} + y -> ${it.view.y}")
            log("overlaay", "point x -> ${it.pointX} + y -> ${it.pointY}")
            val params = circleView?.layoutParams as RelativeLayout.LayoutParams
            params.width = getDimensionOfCurrentView(it)
            params.height = getDimensionOfCurrentView(it)
            circleView?.layoutParams = params
            log("overlaay", "dimension w -> ${params.width} + h -> ${params.height}")
            circleView?.x = it.view.x - padding.div(2)
            circleView?.y = it.view.y - padding.div(2)
            log("overlaay", "new xy x -> ${circleView?.x} + y -> ${circleView?.y}")

        }
    }

    private fun getDimensionOfCurrentView(it: GuidedTourViewDetail): Int {
        return if (it.view.width > it.view.height) return it.view.width.plus(padding) else it.view.height.plus(
            padding
        )
    }

    private fun getCurrentItem(): GuidedTourViewDetail? {
        return if (!guidedTourViewViewsList.isNullOrEmpty() && currentViewId < guidedTourViewViewsList.size) guidedTourViewViewsList[currentViewId] else null
    }
}


