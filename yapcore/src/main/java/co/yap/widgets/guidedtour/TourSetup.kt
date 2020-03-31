package co.yap.widgets.guidedtour

import android.app.Activity
import android.view.View
import co.yap.widgets.guidedtour.shape.Focus
import co.yap.widgets.guidedtour.shape.FocusGravity
import co.yap.widgets.guidedtour.view.MaterialIntroView

class TourSetup() : MaterialIntroListener {

    var previousViewId: Int = 0
    var currentViewId: Int = 0
    var activity: Activity? = null
    var isMultipleViewsTour: Boolean = false
    var guidedTourViewViewsList: ArrayList<GuidedTourViewDetail> = ArrayList()

    constructor (activity: Activity, guidedTourViewDetail: GuidedTourViewDetail) : this() {
        this.activity = activity
        focusSingleView(guidedTourViewDetail)
    }

    constructor(
        activity: Activity,
        guidedTourViewViewsList: ArrayList<GuidedTourViewDetail>
    ) : this() {
        this.activity = activity
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