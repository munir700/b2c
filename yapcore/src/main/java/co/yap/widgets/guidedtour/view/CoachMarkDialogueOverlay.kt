package co.yap.widgets.guidedtour.view

import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RelativeLayout
import co.yap.widgets.CoreCircularImageView
import co.yap.widgets.guidedtour.models.GuidedTourViewDetail
import co.yap.yapcore.R
import co.yap.yapcore.helpers.extentions.log

class CoachMarkDialogueOverlay(
    internal val context: Context,
    private val guidedTourViewViewsList: ArrayList<GuidedTourViewDetail>
) : Dialog(context, android.R.style.Theme_Light) {
    var layer: CircleOverlayView? = null
    private var parentView: LinearLayout? = null
    private var circleImg: CoreCircularImageView? = null
    var rootMain: RelativeLayout? = null
    private var skip: Button? = null
    private val padding: Int = 80
    var mPointer: Pointer? = null

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
        parentView = findViewById(R.id.circleParent)
        layer = findViewById(R.id.circleView)
        circleImg = findViewById(R.id.focusView)
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
            layer?.updateCircle = true
            //
            updateCircle()
        } else {
            dismiss()
        }
    }


    private fun updateCircle() {
        getCurrentItem()?.let {
//            takeScreenshotOfSurfaceView(it.view)?.let { bitmap ->
//                circleImg?.setImageBitmap(bitmap)
//            }

            log("overlaay", "x -> ${it.view.x} + y -> ${it.view.y}")
            log("overlaay", "point x -> ${it.pointX} + y -> ${it.pointY}")

//            layer?.centerX = it.view.x - padding.div(2)
//            layer?.centerY = it.view.y - padding.div(2)


            layer?.centerX = (layer!!.width - (layer!!.width - it.view.x)) + (it.view.x / 2)


            layer?.centerY = it.view.y + it.view.height - it.view.height


            layer?.invalidate()

            log("overlaay", "new xy x -> ${parentView?.x} + y -> ${parentView?.y}")

        }
    }


    private fun updateParentView(it: GuidedTourViewDetail) {
        val params = parentView?.layoutParams as RelativeLayout.LayoutParams
        params.width = getDimensionOfCurrentView(it)
        params.height = getDimensionOfCurrentView(it)
        parentView?.layoutParams = params

        log("overlaay", "dimension w -> ${params.width} + h -> ${params.height}")

        val childParams = circleImg?.layoutParams as LinearLayout.LayoutParams
        childParams.width = getDimensionOfCurrentView(it) - padding
        childParams.height = getDimensionOfCurrentView(it) - padding
        circleImg?.layoutParams = childParams

        log("overlaay", "dimension w -> ${childParams.width} + h -> ${childParams.height}")
    }

    private fun getDimensionOfCurrentView(it: GuidedTourViewDetail): Int {
        return if (it.view.width > it.view.height) return it.view.width.plus(padding) else it.view.height.plus(
            padding
        )
    }

    private fun getCurrentItem(): GuidedTourViewDetail? {
        return if (!guidedTourViewViewsList.isNullOrEmpty() && currentViewId < guidedTourViewViewsList.size) guidedTourViewViewsList[currentViewId] else null
    }

    private fun takeScreenshotOfSurfaceView(targetView: View): Bitmap? {
        if (targetView.width == 0 || targetView.height == 0) {
            return null
        }

        targetView.isDrawingCacheEnabled = true
        val bitmap: Bitmap = Bitmap.createBitmap(targetView.drawingCache)
        targetView.isDrawingCacheEnabled = false
        return bitmap
    }

    class Pointer @JvmOverloads constructor(var gravity: Int = Gravity.CENTER) {

        fun gravity(block: () -> Int) {
            gravity = block()
        }


    }
}


