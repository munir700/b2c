package co.yap.household.dashboard.cards

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import co.yap.household.R
import co.yap.yapcore.BaseListItemViewModel

class MyCardRecentTransactionsItemVM : BaseListItemViewModel<TransactionModel>() {
    private lateinit var mItem: TransactionModel
    override fun setItem(item: TransactionModel, position: Int) {
        mItem = item
    }

    override fun getItem() = mItem

    override fun layoutRes() = R.layout.item_my_card_transaction

    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {}

    override fun onItemClick(view: View, data: Any, pos: Int) {
    }
}