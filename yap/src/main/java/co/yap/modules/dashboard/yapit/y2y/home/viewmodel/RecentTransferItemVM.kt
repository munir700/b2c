package co.yap.modules.dashboard.yapit.y2y.home.viewmodel

import android.os.Bundle
import android.view.View
import co.yap.R
import co.yap.networking.customers.responsedtos.beneficiary.RecentBeneficiary
import co.yap.yapcore.BaseListItemViewModel

class RecentTransferItemVM : BaseListItemViewModel<RecentBeneficiary>() {
    private lateinit var mItem: RecentBeneficiary

    override fun setItem(item: RecentBeneficiary) {
        this.mItem = item
        notifyChange()
    }

    override fun getItem() = mItem

    override fun onFirsTimeUiCreate(bundle: Bundle?) {
    }

    override fun layoutRes() = R.layout.item_recent_transfer

    override fun onItemClick(view: View, data: Any, pos: Int) {
    }
}