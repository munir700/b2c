package co.yap.modules.dashboard.cards.cardlist

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import co.yap.R
import co.yap.networking.cards.responsedtos.Card
import co.yap.yapcore.BaseListItemViewModel

class CardChildItemViewModel : BaseListItemViewModel<Card>() {
    private lateinit var mItem: Card
    override fun setItem(item: Card, position: Int) {
        this.mItem = item
    }

    override fun getItem() = mItem

    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {}

    override fun layoutRes() = R.layout.item_card

    override fun onItemClick(view: View, data: Any, pos: Int) {}
}