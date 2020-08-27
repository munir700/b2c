package co.yap.modules.subaccounts.paysalary.profile.adapter

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import co.yap.R
import co.yap.networking.transactions.responsedtos.transaction.HomeTransactionListData
import co.yap.yapcore.BaseListItemViewModel

class HHSalaryTransactionGroupItemVM : BaseListItemViewModel<HomeTransactionListData>() {
    private lateinit var mItem: HomeTransactionListData
    override fun setItem(item: HomeTransactionListData, position: Int) {
        this.mItem = item
    }

    override fun getItem() = mItem

    override fun layoutRes() = R.layout.item_home_transaction_group

    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {}

    override fun onItemClick(view: View, data: Any, pos: Int) {}
}
