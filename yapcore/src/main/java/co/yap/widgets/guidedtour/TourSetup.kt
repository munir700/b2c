package co.yap.widgets.guidedtour

import android.app.Activity
import android.content.Context
import co.yap.widgets.guidedtour.models.GuidedTourViewDetail
import co.yap.widgets.guidedtour.shape.Focus
import co.yap.widgets.guidedtour.shape.FocusGravity
import co.yap.widgets.guidedtour.view.CoachMarkDialogueOverlay
import co.yap.widgets.guidedtour.view.DescriptionView

class TourSetup() : DescriptionBoxListener {

    var previousViewId: Int = 0
    var currentViewId: Int = 0
    var activity: Activity? = null
    var isMultipleViewsTour: Boolean = false
    private var guidedTourViewViewsList: ArrayList<GuidedTourViewDetail> = ArrayList()
    var layer: CoachMarkDialogueOverlay? = null

    lateinit var context: Context

    constructor(
        context: Context, activity: Activity,
        guidedTourViewViewsList: ArrayList<GuidedTourViewDetail>
    ) : this() {
        this.activity = activity
        this.context = context
        this.guidedTourViewViewsList = guidedTourViewViewsList
        focusMultipleViews()
    }

    private fun focusMultipleViews() {

        isMultipleViewsTour = true
        previousViewId = currentViewId
        focusSingleView(guidedTourViewViewsList[currentViewId])
        currentViewId += 1
    }

    private fun focusSingleView(guidedTourViewDetail: GuidedTourViewDetail) {
        layer = CoachMarkDialogueOverlay(context, guidedTourViewViewsList)
        layer?.show()
    }


    fun showIntro(
        guidedTourViewDetail: GuidedTourViewDetail,
        focusType: Focus?, activity: Activity
    ) {

        DescriptionView.Builder(activity)
            .setFocusGravity(FocusGravity.CENTER)
            .setFocusType(focusType!!)
            .setDelayMillis(200)
            .setViewCount((currentViewId + 1), guidedTourViewViewsList.size)
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