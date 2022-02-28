package co.yap.modules.dashboard.home.models

import android.view.View
import co.yap.networking.customers.models.dashboardwidget.WidgetData
import co.yap.yapcore.interfaces.OnItemClickListener

class WidgetItemViewModel(
    val widgetDataItem: WidgetData?,
    val position: Int,
    private val onItemClickListener: OnItemClickListener?
) {
    fun onViewClicked(view: View) {
        onItemClickListener?.onItemClick(view, widgetDataItem!!, position)
    }
}
