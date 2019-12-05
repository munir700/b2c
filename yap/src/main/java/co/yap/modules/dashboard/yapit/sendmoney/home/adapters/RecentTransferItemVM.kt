package co.yap.modules.dashboard.yapit.sendmoney.home.adapters

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import co.yap.R
import co.yap.modules.dashboard.yapit.y2y.home.fragments.YapToYapFragmentDirections
import co.yap.networking.customers.responsedtos.beneficiary.RecentBeneficiary
import co.yap.yapcore.BaseListItemViewModel

class RecentTransferItemVM : BaseListItemViewModel<RecentBeneficiary>() {
    private lateinit var mItem: RecentBeneficiary
    private var navigation: NavController? = null
    var position: Int = -1

    override fun setItem(item: RecentBeneficiary, position: Int) {
        this.mItem = item
        this.position = position
        notifyChange()
    }

    override fun getItem() = mItem

    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController) {
        this.navigation = navigation
    }

    override fun layoutRes() = R.layout.item_recent_transfer
    override fun onItemClick(view: View, data: Any, pos: Int) {
        navigation?.navigate(
            YapToYapFragmentDirections.actionYapToYapHomeToY2YTransferFragment(
                (data as RecentBeneficiary).beneficiaryPictureUrl
                , data.beneficiaryUuid, data.title, pos
            )
        )
    }
}