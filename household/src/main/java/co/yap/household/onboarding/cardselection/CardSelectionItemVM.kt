package co.yap.household.onboarding.cardselection

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import co.yap.household.R
import co.yap.networking.customers.responsedtos.HouseHoldCardsDesign
import co.yap.yapcore.BaseListItemViewModel

class CardSelectionItemVM : BaseListItemViewModel<HouseHoldCardsDesign>() {
    private lateinit var mItem: HouseHoldCardsDesign
    override fun setItem(item: HouseHoldCardsDesign, position: Int) {
        mItem = item
    }

    override fun getItem() = mItem

    override fun layoutRes() = R.layout.item_card_selection

    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
    }

    override fun onItemClick(view: View, data: Any, pos: Int) {
    }
}