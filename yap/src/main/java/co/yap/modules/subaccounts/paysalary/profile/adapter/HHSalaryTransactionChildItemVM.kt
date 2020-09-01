package co.yap.modules.subaccounts.paysalary.profile.adapter

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import co.yap.R
import co.yap.networking.transactions.responsedtos.transaction.Transaction
import co.yap.yapcore.BaseListItemViewModel

class HHSalaryTransactionChildItemVM : BaseListItemViewModel<Transaction>() {
    private lateinit var mItem: Transaction
    override fun setItem(item: Transaction, position: Int) {
        this.mItem = item
    }

    override fun getItem() = mItem

    override fun layoutRes() = R.layout.item_hh_profile_transaction_child

    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {}

    override fun onItemClick(view: View, data: Any, pos: Int) {
    }
}
