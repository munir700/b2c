package co.yap.household.dashboard.home

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import co.yap.household.R
import co.yap.networking.notification.responsedtos.HomeNotification
import co.yap.yapcore.BaseListItemViewModel

class HHNotificationItemVM : BaseListItemViewModel<HomeNotification>() {
    private lateinit var mItem: HomeNotification
    private var mPosition: Int = 0
    override fun setItem(item: HomeNotification, position: Int) {
        mItem = item
        mPosition = position
    }

    override fun getItem() = mItem

    override fun layoutRes() = R.layout.item_hh_notification

    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {}

    override fun onItemClick(view: View, data: Any, pos: Int) {
    }
    fun handleOnClick(view: View) {
        onChildViewClickListener?.invoke(view, mPosition, getItem())
    }
}