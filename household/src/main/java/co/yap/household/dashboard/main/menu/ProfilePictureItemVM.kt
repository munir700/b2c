package co.yap.household.dashboard.main.menu

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.NavController
import co.yap.household.R
import co.yap.networking.customers.responsedtos.AccountInfo
import co.yap.yapcore.BaseListItemViewModel

class ProfilePictureItemVM : BaseListItemViewModel<AccountInfo>() {
    private lateinit var mItem: AccountInfo
    private var navigation: NavController? = null
    var position: Int = -1

    override fun setItem(item: AccountInfo, position: Int) {
        this.mItem = item
        this.position = position
        notifyChange()
    }

    override fun getItem() = mItem

    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
        this.navigation = navigation
    }

    override fun layoutRes() = R.layout.item_dashboard_menu_profile_pic
    override fun onItemClick(view: View, data: Any, pos: Int) {
    }
}