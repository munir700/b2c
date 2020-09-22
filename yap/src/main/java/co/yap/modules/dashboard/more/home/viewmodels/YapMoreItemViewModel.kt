package co.yap.modules.dashboard.more.home.viewmodels

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import co.yap.R
import co.yap.modules.dashboard.more.home.models.MoreOption
import co.yap.networking.customers.responsedtos.AccountInfo
import co.yap.yapcore.BaseListItemViewModel
import co.yap.yapcore.interfaces.OnItemClickListener


class YapMoreItemViewModel() : BaseListItemViewModel<MoreOption>() {
    lateinit var moreOption: MoreOption
    var position: Int = -1
    var onItemClickListener: OnItemClickListener?=null
    override fun setItem(item: MoreOption, position: Int) {
        moreOption = item
        this.position = position
    }

    override fun getItem() = moreOption

    override fun layoutRes() = R.layout.item_yap_more

    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
    }

    override fun onItemClick(view: View, data: Any, pos: Int) {
    }

    fun handlePressOnView(view: View) {
        onItemClickListener?.onItemClick(view, moreOption, position)
    }
}