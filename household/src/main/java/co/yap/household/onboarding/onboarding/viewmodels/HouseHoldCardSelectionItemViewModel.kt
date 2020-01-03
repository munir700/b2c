package co.yap.household.onboarding.onboarding.viewmodels

import android.view.View
import co.yap.yapcore.interfaces.OnItemClickListener

class HouseHoldCardSelectionItemViewModel(
    var postion: Int?,
    var color: Int?,
    private val onItemClickListener: OnItemClickListener?
) {
    fun handlePressOnClick(view: View) {
        onItemClickListener?.onItemClick(view, color ?: 0, postion ?: 0)
    }
}