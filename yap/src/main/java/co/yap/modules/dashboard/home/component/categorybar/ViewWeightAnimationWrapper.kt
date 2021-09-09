package co.yap.modules.dashboard.home.component.categorybar

import android.view.View
import android.widget.LinearLayout

class ViewWeightAnimationWrapper(view: View) {
    private var view: View? = null
    var weight: Float
        get() = (view?.layoutParams as LinearLayout.LayoutParams).weight
        set(weight) {
            val params = view?.layoutParams as LinearLayout.LayoutParams
            params.weight = weight
            view?.parent?.requestLayout()
        }

    init {
        if (view.layoutParams is LinearLayout.LayoutParams) {
            this.view = view
        } else {
            throw IllegalArgumentException("The view should have LinearLayout as parent")
        }
    }
}