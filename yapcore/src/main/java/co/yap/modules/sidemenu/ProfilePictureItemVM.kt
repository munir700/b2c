package co.yap.modules.sidemenu

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import co.yap.networking.customers.responsedtos.AccountInfo
import co.yap.yapcore.BaseListItemViewModel
import co.yap.yapcore.R

class ProfilePictureItemVM : BaseListItemViewModel<AccountInfo>() {
    private lateinit var mItem: AccountInfo
    var position: Int = -1
    override fun setItem(item: AccountInfo, position: Int) {
        this.mItem = item
        this.position = position
    }

    override fun getItem() = mItem

    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
    }

    override fun layoutRes() = R.layout.item_dashboard_menu_profile_pic
    override fun onItemClick(view: View, data: Any, pos: Int) {
    }
}