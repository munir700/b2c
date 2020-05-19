package co.yap.household.dashboard.home

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import co.yap.household.R
import co.yap.networking.notification.HomeNotification
import co.yap.yapcore.BaseListItemViewModel

class HHNotificationItemVM : BaseListItemViewModel<HomeNotification>() {
    private lateinit var mItem: HomeNotification
    override fun setItem(item: HomeNotification, position: Int) {
        mItem = item
    }

    override fun getItem() = mItem

    override fun layoutRes() = R.layout.item_hh_notification

    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {}

    override fun onItemClick(view: View, data: Any, pos: Int) {
    }
}