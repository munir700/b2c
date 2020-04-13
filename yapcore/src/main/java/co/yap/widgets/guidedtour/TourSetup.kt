package co.yap.widgets.guidedtour

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics
import co.yap.widgets.couchmark.BubbleShowCase
import co.yap.widgets.couchmark.ScreenUtils
import co.yap.widgets.guidedtour.models.GuidedTourViewDetail
import co.yap.widgets.guidedtour.shape.Circle
import co.yap.widgets.guidedtour.shape.Focus
import co.yap.widgets.guidedtour.shape.FocusGravity
import co.yap.widgets.guidedtour.target.Target
import co.yap.widgets.guidedtour.target.ViewTarget
import co.yap.widgets.guidedtour.view.DescriptionView

class TourSetup() : DescriptionBoxListener {

    var previousViewId: Int = 0
    var currentViewId: Int = 0
    var activity: Activity? = null
    var isMultipleViewsTour: Boolean = false
    var guidedTourViewViewsList: ArrayList<GuidedTourViewDetail> = ArrayList()

    lateinit var metrics: DisplayMetrics
    lateinit var context: Context

    var descView: DescriptionView? = null

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
        currentViewId += 1
        println(currentViewId)


    }

    fun focusSingleView(guidedTourViewDetail: GuidedTourViewDetail) {
        activity?.let {
            showIntro(
                guidedTourViewDetail,
                Focus.ALL, it
            )
        }
    }


    fun showIntro(
        guidedTourViewDetail: GuidedTourViewDetail,
        focusType: Focus?, activity: Activity
    ) {

        descView = DescriptionView.Builder(activity)
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
                    //focusMultipleViews()
//                    currentViewId += 1
                    updateCurrentDescriptionBox(descView!!, guidedTourViewViewsList[currentViewId])
                }
            }

        }
    }

    private fun updateCurrentDescriptionBox(
        descView: DescriptionView,
        currentHint: GuidedTourViewDetail
    ) {

        val dBox = descView.viewDataBinding.descriptionView
        //dBox.animate().y(currentHint.view.y).setDuration(500).start()
        updateShape(descView,currentHint)
    }

    fun updateShape(materialIntroView: DescriptionView,currentHint: GuidedTourViewDetail) {
        // no custom shape supplied, build our own
        currentHint.view.let { ViewTarget(it) }.let { materialIntroView.setTarget(it) }
        val shape = Circle(
            materialIntroView.targetView,
            materialIntroView.focusType,
            materialIntroView.focusGravity,
            materialIntroView.padding
        )
//        getTooltipPosition(materialIntroView.targetView!!)
        materialIntroView.setShape(shape)
        materialIntroView.setInfoLayout()
        materialIntroView.invalidate()
    }

//    private fun getTooltipPosition(targetView: Target) {
//        if (mArrowPositionList.isEmpty()) {
//            if (ScreenUtils.isViewLocatedAtHalfTopOfTheScreen(
//                    context,
//                    targetView.view
//                )
//            ) mArrowPositionList.add(BubbleShowCase.ArrowPosition.TOP) else mArrowPositionList.add(
//                BubbleShowCase.ArrowPosition.BOTTOM
//            )
//        }
//    }
}