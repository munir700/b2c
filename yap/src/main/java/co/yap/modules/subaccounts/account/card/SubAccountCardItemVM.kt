package co.yap.modules.subaccounts.account.card

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import co.yap.R
import co.yap.networking.customers.responsedtos.SubAccount
import co.yap.yapcore.BaseListItemViewModel
import co.yap.yapcore.enums.AccountType

class SubAccountCardItemVM : BaseListItemViewModel<SubAccount>() {
    private lateinit var mItem: SubAccount
    var position: Int = 0
    var fullName: String? = null
    var subTitle: String? = null
    override fun setItem(item: SubAccount, position: Int) {
        mItem = item
        this.position = position
        subTitle = mItem.cardStatus ?: "Add new card"
        fullName = mItem.accountType?.let {
            when (it) {
                AccountType.B2C_HOUSEHOLD.name -> mItem.getFullName()
                else -> "Your balance"
            }
        }
    }

    override fun getItem() = mItem
    override fun layoutRes() = R.layout.item_sub_account_card
    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
    }

    override fun onItemClick(view: View, data: Any, pos: Int) {
    }
}