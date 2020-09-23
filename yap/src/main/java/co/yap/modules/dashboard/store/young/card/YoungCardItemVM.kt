package co.yap.modules.dashboard.store.young.card

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import co.yap.R
import co.yap.networking.customers.responsedtos.HouseHoldCardsDesign
import co.yap.yapcore.BaseListItemViewModel

class YoungCardItemVM : BaseListItemViewModel<HouseHoldCardsDesign>() {

    private lateinit var mItem: HouseHoldCardsDesign

    override fun setItem(item: HouseHoldCardsDesign, position: Int) {
        mItem = item
    }

    override fun getItem()= mItem

    override fun layoutRes()= R.layout.item_card_edit

    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
    }

    override fun onItemClick(view: View, data: Any, pos: Int) {
    }
}