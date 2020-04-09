package co.yap.widgets.guidedtour

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics
import android.view.View
import co.yap.widgets.guidedtour.models.GuidedTourViewDetail
import co.yap.widgets.guidedtour.shape.Focus
import co.yap.widgets.guidedtour.shape.FocusGravity
import co.yap.widgets.guidedtour.view.DescriptionView

class TourSetup() : MaterialIntroListener {

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

        DescriptionView.Builder(activity)
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