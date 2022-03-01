package co.yap.modules.dashboard.cards.cardlist

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import co.yap.R
import co.yap.networking.cards.CardListData
import co.yap.yapcore.BaseListItemViewModel

class CardHeaderItemViewModel : BaseListItemViewModel<CardListData>() {
    private lateinit var mItem: CardListData
    override fun setItem(item: CardListData, position: Int) {
        this.mItem = item
    }

    override fun getItem() = mItem

    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
    }

    override fun layoutRes()= R.layout.item_card_header


    override fun onItemClick(view: View, data: Any, pos: Int) {
    }
}