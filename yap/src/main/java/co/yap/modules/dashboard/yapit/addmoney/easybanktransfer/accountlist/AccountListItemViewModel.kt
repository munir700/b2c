package co.yap.modules.dashboard.yapit.addmoney.easybanktransfer.accountlist

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import co.yap.R
import co.yap.yapcore.BaseListItemViewModel

class AccountListItemViewModel: BaseListItemViewModel<AccountsListModel>(){
    private lateinit var list: AccountsListModel
    override fun setItem(item: AccountsListModel, position: Int) {
        list= item
        notifyChange()
    }
    override fun getItem(): AccountsListModel = list
    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {}
    override fun layoutRes(): Int = R.layout.item_account_list
    override fun onItemClick(view: View, data: Any, pos: Int) {}
}