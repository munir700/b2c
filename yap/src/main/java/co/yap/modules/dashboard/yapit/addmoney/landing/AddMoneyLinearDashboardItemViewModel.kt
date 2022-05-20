package co.yap.modules.dashboard.yapit.addmoney.landing

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import co.yap.R
import co.yap.yapcore.BaseListItemViewModel

class AddMoneyLinearDashboardItemViewModel : BaseListItemViewModel<AddMoneyLandingOptions>() {
    private lateinit var mItem: AddMoneyLandingOptions

    override fun layoutRes(): Int = R.layout.item_yap_it_add_money_linear_landing

    override fun getItem(): AddMoneyLandingOptions = mItem

    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
    }

    override fun onItemClick(view: View, data: Any, pos: Int) {
    }

    override fun setItem(item: AddMoneyLandingOptions, position: Int) {
        mItem = item
        notifyChange()
    }
}