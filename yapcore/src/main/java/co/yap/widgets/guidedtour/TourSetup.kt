package co.yap.widgets.guidedtour

import android.app.Activity
import android.content.Context
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
//        layer?.show()
        getCurrentItem()?.let {
            descBox = getDescBox(context, it)
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
        guidedTourViewDetail: GuidedTourViewDetail
    ): BubbleShowCaseBuilder {
        return BubbleShowCaseBuilder(activity!!)
            .title(guidedTourViewDetail.title)
            .description(guidedTourViewDetail.description)
            .backgroundColor(context.getColor(R.color.white)) //Bubble background color
            .targetView(guidedTourViewDetail.view)
    }

    private fun getCurrentItem(): GuidedTourViewDetail? {
        return if (!guidedTourViewViewsList.isNullOrEmpty() && currentViewId < guidedTourViewViewsList.size) guidedTourViewViewsList[currentViewId] else null
    }
}