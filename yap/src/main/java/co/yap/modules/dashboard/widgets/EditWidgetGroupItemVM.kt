package co.yap.modules.dashboard.widgets

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import co.yap.R
import co.yap.yapcore.BaseListItemViewModel

class EditWidgetGroupItemVM : BaseListItemViewModel<WidgetList>() {
    private lateinit var mItem: WidgetList
    override fun setItem(item: WidgetList, position: Int) {
        this.mItem = item
    }

    override fun getItem() = mItem

    override fun layoutRes() = R.layout.item_search_transaction_group

    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {}

    override fun onItemClick(view: View, data: Any, pos: Int) {}
}
