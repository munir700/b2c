package co.yap.modules.dashboard.widgets

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import co.yap.R
import co.yap.networking.customers.models.dashboardwidget.WidgetData
import co.yap.yapcore.BaseListItemViewModel

class WidgetItemViewModel : BaseListItemViewModel<WidgetData>() {
    private lateinit var widgetData: WidgetData
    override fun setItem(item: WidgetData, position: Int) {
        widgetData = item
        notifyChange()
    }

    override fun getItem(): WidgetData = widgetData

    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
    }

    override fun layoutRes(): Int = R.layout.item_widget_add_remove_body


    override fun onItemClick(view: View, data: Any, pos: Int) {
    }

}
