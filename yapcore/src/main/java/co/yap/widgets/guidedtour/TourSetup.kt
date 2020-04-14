package co.yap.widgets.guidedtour

import android.app.Activity
import android.content.Context
import android.graphics.RectF
import android.view.View
import co.yap.widgets.couchmark.*
import co.yap.widgets.guidedtour.models.GuidedTourViewDetail
import co.yap.widgets.guidedtour.view.CoachMarkDialogueOverlay
import co.yap.yapcore.R

class TourSetup() {

    var currentViewId: Int = 0
    var previousViewId = currentViewId
    var activity: Activity? = null
    var isMultipleViewsTour: Boolean = false
    private var guidedTourViewViewsList: ArrayList<GuidedTourViewDetail> = ArrayList()
    var layer: CoachMarkDialogueOverlay? = null
    private var descBox: BubbleShowCaseBuilder? = null
    var showCase: BubbleShowCase? = null

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
            descBox = getDescBox(context, it)
            descBox?.listener(listener)
            showCase = descBox?.show()
        }
    }

    private val listener = object : BubbleShowCaseListener {
        override fun onCloseActionImageClick(bubbleShowCase: BubbleShowCase) {
            moveNext()
        }
    }

    private fun moveNext() {
        if (currentViewId < guidedTourViewViewsList.size) {
            previousViewId = currentViewId
            currentViewId += 1
            updateDescriptionBox()
        } else {
            showCase?.dismiss()
        }
    }


    private fun updateDescriptionBox() {
        getCurrentItem()?.let {
            val builder = getBubbleMessageViewBuilder(it)
            val msgView = showCase?.getView()
//            val xPos = ScreenUtils.getAxisXpositionOfViewOnScreen(it.view).toFloat()
//            val yPos = ScreenUtils.getAxisYpositionOfViewOnScreen(it.view).toFloat()
//            msgView?.animate()?.x(xPos)?.y(yPos)?.setDuration(1000)?.start()
            msgView?.setAttributes(builder)
            msgView?.invalidate()
        }
    }


    private fun getBubbleMessageViewBuilder(tourDetail: GuidedTourViewDetail): BubbleMessageView.Builder {
        return BubbleMessageView.Builder(activity!!)
            .from(activity!!)
            .arrowPosition(getTooltipPosition(tourDetail.view))
            .backgroundColor(context.getColor(R.color.white))
            .title(tourDetail.title)
            .subtitle(tourDetail.description)
            .targetViewScreenLocation(getTargetViewScreenLocation(tourDetail.view))
            .listener(object : OnBubbleMessageViewListener {
                override fun onBubbleClick() {
                    listener.onBubbleClick(showCase!!)
                }

                override fun onCloseActionImageClick() {
                    listener.onCloseActionImageClick(showCase!!)
                }
            })
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


    private fun getTooltipPosition(targetView: View): ArrayList<BubbleShowCase.ArrowPosition> {
        val list: ArrayList<BubbleShowCase.ArrowPosition> = arrayListOf()
        if (ScreenUtils.isViewLocatedAtHalfTopOfTheScreen(
                activity!!,
                targetView
            )
        ) list.add(BubbleShowCase.ArrowPosition.TOP) else list.add(
            BubbleShowCase.ArrowPosition.BOTTOM
        )
        return list
    }

    private fun getTargetViewScreenLocation(
        targetView: View
    ): RectF {
        return RectF(
            showCase?.getXposition(targetView)?.toFloat()!!,
            showCase?.getYposition(targetView)?.toFloat()!!,
            showCase?.getXposition(targetView)?.toFloat()!! + targetView.width,
            showCase?.getYposition(targetView)?.toFloat()!! + targetView.height
        )
    }

    private fun getCurrentItem(): GuidedTourViewDetail? {
        return if (!guidedTourViewViewsList.isNullOrEmpty() && currentViewId < guidedTourViewViewsList.size) guidedTourViewViewsList[currentViewId] else null
    }
}