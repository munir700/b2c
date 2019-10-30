package co.yap.modules.dashboard.yapit.y2y.home.viewmodel

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import co.yap.R
import co.yap.modules.dashboard.yapit.y2y.home.fragments.YapToYapFragmentDirections
import co.yap.networking.customers.responsedtos.beneficiary.RecentBeneficiary
import co.yap.yapcore.BaseListItemViewModel

class RecentTransferItemVM : BaseListItemViewModel<RecentBeneficiary>() {
    private lateinit var mItem: RecentBeneficiary
    private var navigation: NavController?=null

    override fun setItem(item: RecentBeneficiary) {
        this.mItem = item
        notifyChange()
    }

    override fun getItem() = mItem

    override fun onFirsTimeUiCreate(bundle: Bundle?,navigation: NavController) {
        this.navigation = navigation
    }

    override fun layoutRes() = R.layout.item_recent_transfer
    override fun onItemClick(view: View, data: Any, pos: Int) {
        navigation?.navigate(
            YapToYapFragmentDirections.actionYapToYapHomeToY2YTransferFragment(
//                data.beneficiaryPictureUrl!!
//                , data.accountDetailList?.get(0)?.accountUuid!!, data.title!!,pos
            )
        )

    }
}