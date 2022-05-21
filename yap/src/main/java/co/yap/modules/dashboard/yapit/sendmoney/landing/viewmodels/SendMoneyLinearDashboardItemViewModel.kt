package co.yap.modules.dashboard.yapit.sendmoney.landing.viewmodels

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import co.yap.R
import co.yap.modules.dashboard.yapit.sendmoney.main.SendMoneyLinearOptions
import co.yap.yapcore.BaseListItemViewModel

class SendMoneyLinearDashboardItemViewModel : BaseListItemViewModel<SendMoneyLinearOptions>() {
    private lateinit var mItem: SendMoneyLinearOptions

    override fun layoutRes(): Int = R.layout.item_yap_it_send_money_landing_linear_view

    override fun getItem(): SendMoneyLinearOptions = mItem

    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
    }

    override fun onItemClick(view: View, data: Any, pos: Int) {
    }

    override fun setItem(item: SendMoneyLinearOptions, position: Int) {
        mItem = item
        notifyChange()
    }
}