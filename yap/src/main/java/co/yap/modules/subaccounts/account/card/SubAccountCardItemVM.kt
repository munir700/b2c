package co.yap.modules.subaccounts.account.card

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import co.yap.R
import co.yap.networking.customers.responsedtos.SubAccount
import co.yap.yapcore.BaseListItemViewModel

class SubAccountCardItemVM : BaseListItemViewModel<SubAccount>() {
    private var mItem: SubAccount = SubAccount()
    var position: Int = 0

    override fun setItem(item: SubAccount, position: Int) {
        mItem = item
        this.position = position
    }

    override fun getItem() = mItem
    override fun layoutRes() = R.layout.item_sub_account_card
    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
    }

    override fun onItemClick(view: View, data: Any, pos: Int) {
    }

}