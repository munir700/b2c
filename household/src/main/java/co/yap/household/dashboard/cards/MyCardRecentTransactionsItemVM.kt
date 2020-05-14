package co.yap.household.dashboard.cards

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import co.yap.household.R
import co.yap.networking.transactions.responsedtos.transaction.Content
import co.yap.yapcore.BaseListItemViewModel

class MyCardRecentTransactionsItemVM : BaseListItemViewModel<Content>() {
    private lateinit var mItem: Content
    override fun setItem(item: Content, position: Int) {
        mItem = item
    }
    override fun getItem() = mItem

    override fun layoutRes() = R.layout.item_my_card_transaction

    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {}

    override fun onItemClick(view: View, data: Any, pos: Int) {
    }
}