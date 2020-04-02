package co.yap.widgets.guidedtour

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup
import co.yap.widgets.guidedtour.description.CoachMarkConfig
import co.yap.widgets.guidedtour.models.GuidedTourViewDetail
import co.yap.widgets.guidedtour.shape.Focus
import co.yap.widgets.guidedtour.shape.FocusGravity
import co.yap.widgets.guidedtour.view.MaterialIntroView
import co.yap.yapcore.R
import com.leanplum.internal.FileManager.resources
import it.sephiroth.android.library.xtooltip.ClosePolicy
import it.sephiroth.android.library.xtooltip.Tooltip
import it.sephiroth.android.library.xtooltip.Typefaces
import timber.log.Timber

class TourSetup() : MaterialIntroListener {

    var previousViewId: Int = 0
    var currentViewId: Int = 0
    var activity: Activity? = null
    var isMultipleViewsTour: Boolean = false
    var guidedTourViewViewsList: ArrayList<GuidedTourViewDetail> = ArrayList()
    var tooltip: Tooltip? = null
    lateinit var metrics : DisplayMetrics
    lateinit var context: Context

    constructor (  context: Context,activity: Activity, guidedTourViewDetail: GuidedTourViewDetail) : this() {
        this.activity = activity
        this.context = context
          metrics = activity.resources.displayMetrics

        focusSingleView(guidedTourViewDetail)

    }

    constructor(
         context: Context, activity: Activity,
        guidedTourViewViewsList: ArrayList<GuidedTourViewDetail>
    ) : this() {
        this.activity = activity
        this.context = context
        metrics = activity.resources.displayMetrics

        this.guidedTourViewViewsList = guidedTourViewViewsList
        focusMultipleViews()
    }

    fun focusMultipleViews() {

        isMultipleViewsTour = true
        previousViewId = currentViewId

        focusSingleView(guidedTourViewViewsList[currentViewId])
        currentViewId = currentViewId + 1
        println(currentViewId)

    }

    fun focusSingleView(guidedTourViewDetail: GuidedTourViewDetail) {
        activity?.let {
            showIntro(
                guidedTourViewDetail.view,
                guidedTourViewDetail.view?.id.toString(),
                guidedTourViewDetail.description,
                Focus.ALL, it
            )
        }
    }


    fun showIntro(
        view: View?,
        id: String,
        text: String,
        focusType: Focus?, activity: Activity
    ) {
//        val gravity = Tooltip.Gravity.valueOf(Tooltip.Gravity.TOP.toString())
//         val closePolicy = getClosePolicy()
////        val typeface = if (checkbox_font.isChecked) Typefaces[this, "fonts/GillSans.ttc"] else null
////        val animation = if (checkbox_animation.isChecked) Tooltip.Animation.DEFAULT else null
//        val showDuration = 5000L
//        val arrow = true
//        val overlay = true
//        val style =    R.style.ToolTipAltStyle
//        val text = text

//        Timber.v("gravity: $gravity")
//        Timber.v("closePolicy: $closePolicy")

//        tooltip?.dismiss()
//        view?.let {
//        tooltip =
//            Tooltip.Builder(activity)
//                .anchor(it, 0, 0, false)
//                .text(text)
//                .styleId(style)
//                 .maxWidth(metrics.widthPixels / 2)
//                .arrow(arrow)
//                 .closePolicy(closePolicy)
//                .showDuration(showDuration)
//                .overlay(overlay)
//                .create()
//
//            tooltip
//                ?.doOnHidden {
//                    tooltip = null
//                }
//                ?.doOnFailure { }
//                ?.doOnShown {}
//                ?.show(it, gravity, true)
//        }
//        Tooltip.Builder(context!!)
//            .anchor(view!!, 0, 0, false)
//            .closePolicy(ClosePolicy.TOUCH_NONE)
//            .showDuration(0)
//            .text("This is a dialogdialogdialogdialogdialogdialogdialogdialogdialogdialogdialogdialogdialog")
//            .create()
//        .show(view!!, Tooltip.Gravity.TOP, false)


        MaterialIntroView.Builder(activity)
            .setFocusGravity(FocusGravity.CENTER)
            .setFocusType(focusType!!)
            .setDelayMillis(200)
            .enableFadeAnimation(true)
            .setListener(this)
            .performClick(true)
            .setInfoText(text)
            .setTarget(view)
            .setUsageId(id)
            .show()
    }

    private fun getClosePolicy(): ClosePolicy {
        val builder = ClosePolicy.Builder()
        builder.inside(true)
        builder.outside(true)
        builder.consume(true)
        return builder.build()
    }

    override fun onUserClicked(materialIntroViewId: String?) {
        // make a check to handle next or skip tour options here

        if (materialIntroViewId != null) {
            if (isMultipleViewsTour) {
                if (currentViewId < guidedTourViewViewsList.size) {
                    focusMultipleViews()
                }
            }

        }
    }
}