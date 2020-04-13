package co.yap.widgets.guidedtour

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics
import co.yap.widgets.guidedtour.models.GuidedTourViewDetail
import co.yap.widgets.guidedtour.shape.Focus
import co.yap.widgets.guidedtour.shape.FocusGravity
import co.yap.widgets.guidedtour.view.DescriptionView
import co.yap.widgets.guidedtour.view.CoachMarkDialogueOverlay

class TourSetup() : DescriptionBoxListener {

    var previousViewId: Int = 0
    var currentViewId: Int = 0
    var activity: Activity? = null
    var isMultipleViewsTour: Boolean = false
    var guidedTourViewViewsList: ArrayList<GuidedTourViewDetail> = ArrayList()

    lateinit var metrics: DisplayMetrics
    lateinit var context: Context

    constructor (
        context: Context,
        activity: Activity,
        guidedTourViewDetail: GuidedTourViewDetail
    ) : this() {
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

        CoachMarkDialogueOverlay(context)


//        activity?.let {
//            showIntro(
//                guidedTourViewDetail,
//                Focus.ALL, it
//            )
//        }
    }


    fun showIntro(
        guidedTourViewDetail: GuidedTourViewDetail,
        focusType: Focus?, activity: Activity
    ) {

        DescriptionView.Builder(activity)
            .setFocusGravity(FocusGravity.CENTER)
            .setFocusType(focusType!!)
            .setDelayMillis(200)
            .setViewCount((currentViewId+1),guidedTourViewViewsList.size)
            .enableFadeAnimation(true)
            .setListener(this)
            .performClick(true)
            .setInfoText(guidedTourViewDetail)
            .setTarget(guidedTourViewDetail.view)
            .setUsageId(guidedTourViewDetail.view?.id.toString())
            .show()
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