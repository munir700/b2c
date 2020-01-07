package co.yap.household.onboarding.onboarding.viewmodels

import android.view.View
import co.yap.household.onboarding.onboarding.fragments.CardColorSelectionModel
import co.yap.yapcore.interfaces.OnItemClickListener

class HouseHoldCardSelectionItemViewModel(
    var postion: Int?,
    var cardColorSelectionModel: CardColorSelectionModel,
    private val onItemClickListener: OnItemClickListener?
) {
    fun handlePressOnClick(view: View) {
        onItemClickListener?.onItemClick(view, cardColorSelectionModel ?: 0, postion ?: 0)
    }
}