package co.yap.household.dashboard.more

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import co.yap.household.R
import co.yap.modules.dashboard.more.home.models.MoreOption
import co.yap.yapcore.BaseListItemViewModel

class HHMoreItemVM : BaseListItemViewModel<MoreOption>() {
    private lateinit var moreOption: MoreOption
    var position: Int = -1
    override fun setItem(item: MoreOption, position: Int) {
        moreOption = item
        this.position = position
    }

    override fun getItem() = moreOption

    override fun layoutRes() = R.layout.item_hh_more

    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
    }

    override fun onItemClick(view: View, data: Any, pos: Int) {
    }
}
