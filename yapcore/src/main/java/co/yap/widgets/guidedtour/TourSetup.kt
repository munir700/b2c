package co.yap.widgets.guidedtour

import android.app.Activity
import android.content.Context
import co.yap.widgets.couchmark.BubbleShowCaseBuilder
import co.yap.widgets.guidedtour.models.GuidedTourViewDetail
import co.yap.widgets.guidedtour.shape.Focus
import co.yap.widgets.guidedtour.view.CoachMarkDialogueOverlay
import co.yap.yapcore.R

class TourSetup() : DescriptionBoxListener {

    var previousViewId: Int = 0
    var currentViewId: Int = 0
    var activity: Activity? = null
    var isMultipleViewsTour: Boolean = false
    var guidedTourViewViewsList: ArrayList<GuidedTourViewDetail> = ArrayList()

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
        focusSingleView()
        currentViewId += 1
    }

    private fun focusSingleView() {
        CoachMarkDialogueOverlay(context, guidedTourViewViewsList)
        showIntro(guidedTourViewViewsList[currentViewId],activity!!)
    }


    fun showIntro(
        guidedTourViewDetail: GuidedTourViewDetail, activity: Activity
    ) {

        BubbleShowCaseBuilder(activity)
            .title(guidedTourViewDetail.title)
            .description(guidedTourViewDetail.description)
            .backgroundColor(context.getColor(R.color.white)) //Bubble background color
            .textColor(context.getColor(R.color.quantum_black_100)) //Bubble Text color
            .titleTextSize(17) //Title text size in SP (default value 16sp)
            .descriptionTextSize(15) //Subtitle text size in SP (default value 14sp)
            .targetView(guidedTourViewDetail.view)
            .show()

//        DescriptionView.Builder(activity)
//            .setFocusGravity(FocusGravity.CENTER)
//            .setFocusType(focusType!!)
//            .setDelayMillis(200)
//            .setViewCount((currentViewId + 1), guidedTourViewViewsList.size)
//            .enableFadeAnimation(true)
//            .setListener(this)
//            .performClick(true)
//            .setInfoText(guidedTourViewDetail)
//            .setTarget(guidedTourViewDetail.view)
//            .setUsageId(guidedTourViewDetail.view?.id.toString())
//            .show()
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