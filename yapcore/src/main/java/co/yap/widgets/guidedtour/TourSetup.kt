package co.yap.widgets.guidedtour

import android.app.Activity
import android.view.View
import co.yap.widgets.guidedtour.shape.Focus
import co.yap.widgets.guidedtour.shape.FocusGravity
import co.yap.widgets.guidedtour.view.MaterialIntroView

class TourSetup(var view: View, var activity: Activity) : MaterialIntroListener {
    var previousViewId: Int? = null

    init {
        showIntro(
            view,
            view.id.toString(),
            "This intro view.",
            Focus.ALL, activity
        )
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
            .setUsageId(id) //THIS SHOULD BE UNIQUE ID
            .show()
    }

    override fun onUserClicked(materialIntroViewId: String?) {
        if (materialIntroViewId != null) {
//            toast(materialIntroViewId)
//            if (previousViewId != null) {
//                previousViewId = view.id
//            } else {
//                previousViewId
//            }
            if (!materialIntroViewId.equals(view.id.toString()) &&  view.id != previousViewId)
                showIntro(
                    view,
                    view?.id.toString(),
                    "This intro view new.",
                    Focus.ALL, activity
                )
            previousViewId=view.id
        }
    }
}