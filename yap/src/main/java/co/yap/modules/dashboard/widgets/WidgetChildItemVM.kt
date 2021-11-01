package co.yap.modules.dashboard.widgets

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import co.yap.R
import co.yap.yapcore.BaseListItemViewModel

class WidgetChildItemVM : BaseListItemViewModel<Widget>() {
    private lateinit var mItem: Widget
    override fun setItem(item: Widget, position: Int) {
        this.mItem = item
    }

    override fun getItem() = mItem

    override fun layoutRes() = R.layout.item_widget_add_remove_body

    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {}

    override fun onItemClick(view: View, data: Any, pos: Int) {
    }
}
