package co.yap.widgets.guidedtour

import android.app.Activity
import android.content.Context
import android.graphics.RectF
import android.view.View
import co.yap.widgets.couchmark.*
import co.yap.widgets.guidedtour.models.GuidedTourViewDetail
import co.yap.widgets.guidedtour.view.CoachMarkDialogueOverlay
import co.yap.yapcore.R

class TourSetup(
    private val activity: Activity,
    private val guidedTourViewViewsList: ArrayList<GuidedTourViewDetail>
) {

    private var currentViewId: Int = 0
    private var previousViewId = currentViewId
    private var layer: CoachMarkDialogueOverlay? = null
    private var descBox: BubbleMessageView.Builder? = null
    private var descBoxView: BubbleMessageView? = null
    private var showCase: BubbleShowCase? = null

    fun startTour() {
        layer = CoachMarkDialogueOverlay(activity, guidedTourViewViewsList)
        layer?.show()
        getCurrentItem()?.let {
            showCase = BubbleShowCase(getDescBox(activity, it))
            descBox = getBubbleMessageViewBuilder(it)
            val showCaseParams = showCase?.addBubbleMessageViewDependingOnTargetView(
                it.view,
                descBox!!
            )
            descBoxView = descBox?.build()
            descBoxView?.y = descBoxView?.y?.minus(it.padding) ?: 0f
            descBoxView?.layoutParams = showCaseParams
            layer?.rootMain?.addView(
                descBoxView, 0
            )
            descBoxView?.bringToFront()
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
            layer?.onItemClickListener?.onItemClick(currentViewId)
        } else {
            layer?.dismiss()
            showCase?.dismiss()
        }
    }


    private fun updateDescriptionBox() {
        getCurrentItem()?.let {
            val builder = getBubbleMessageViewBuilder(it)
            val arrowPosition = getTooltipPosition(it.view).first()
            val yPos = ScreenUtils.getAxisYpositionOfViewOnScreen(it.view).toFloat()
            val yPadding =
                if (arrowPosition == BubbleShowCase.ArrowPosition.TOP) yPos + it.padding else yPos - it.padding
            descBoxView?.animate()
                ?.y(yPadding)
                ?.setDuration(1000)?.start()
            //descBoxView?.animate()?.y(yPos + it.padding)?.setDuration(1000)?.start()
            descBoxView?.setAttributes(builder)
            // descBoxView?.y = descBoxView?.y?.minus(it.padding) ?: 0f
        }
    }


    private fun getBubbleMessageViewBuilder(tourDetail: GuidedTourViewDetail): BubbleMessageView.Builder {
        return BubbleMessageView.Builder(activity)
            .from(activity)
            .arrowPosition(getTooltipPosition(tourDetail.view))
            .backgroundColor(activity.getColor(R.color.white))
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
        return BubbleShowCaseBuilder(activity)
            .title(guidedTourViewDetail.title)
            .description(guidedTourViewDetail.description)
            .backgroundColor(context.getColor(R.color.white)) //Bubble background color
            .targetView(guidedTourViewDetail.view)
    }


    private fun getTooltipPosition(targetView: View): ArrayList<BubbleShowCase.ArrowPosition> {
        val list: ArrayList<BubbleShowCase.ArrowPosition> = arrayListOf()
        if (ScreenUtils.isViewLocatedAtHalfTopOfTheScreen(
                activity,
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