package co.yap.widgets.guidedtour

import android.app.Activity
import android.content.Context
import android.view.View
import co.yap.widgets.couchmark.BubbleShowCase
import co.yap.widgets.couchmark.BubbleShowCaseBuilder
import co.yap.widgets.guidedtour.models.GuidedTourViewDetail
import co.yap.widgets.guidedtour.view.CoachMarkDialogueOverlay
import co.yap.yapcore.R

class TourSetup() : DescriptionBoxListener {

    var currentViewId: Int = 0
    var previousViewId = currentViewId
    var activity: Activity? = null
    var isMultipleViewsTour: Boolean = false
    private var guidedTourViewViewsList: ArrayList<GuidedTourViewDetail> = ArrayList()
    var layer: CoachMarkDialogueOverlay? = null
    var descBox: BubbleShowCaseBuilder? = null

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
    }

    private fun focusSingleView() {
        layer = CoachMarkDialogueOverlay(context, guidedTourViewViewsList)
        layer?.show()
        getCurrentItem()?.let {
            descBox = getDescBox(context, it.view)
            descBox?.show()
        }
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

    private fun getDescBox(
        context: Context,
        currentView: View
    ): BubbleShowCaseBuilder {
        return BubbleShowCaseBuilder(context as Activity)
            .title("Your current balance")
            .description("Here you can see your account’s current balance. It will be updated in-real time after every transaction.")
            .backgroundColor(context.getColor(R.color.white)) //Bubble background color
            .textColor(context.getColor(R.color.quantum_black_100)) //Bubble Text color
            .titleTextSize(17) //Title text size in SP (default value 16sp)
            .descriptionTextSize(15) //Subtitle text size in SP (default value 14sp)
            .highlightMode(BubbleShowCase.HighlightMode.VIEW_CIRCLE)
            .targetView(currentView)
    }

    private fun getCurrentItem(): GuidedTourViewDetail? {
        return if (!guidedTourViewViewsList.isNullOrEmpty() && currentViewId < guidedTourViewViewsList.size) guidedTourViewViewsList[currentViewId] else null
    }
}