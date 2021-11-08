package co.yap.modules.dashboard.widgets.landing

import androidx.databinding.ObservableField
import co.yap.networking.customers.models.dashboardwidget.WidgetData
import co.yap.yapcore.IBase

class IWidgetLanding {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State> {
        val widgetAdapter: ObservableField<WidgetAdapter>?
        var widgetDataList: MutableList<WidgetData>
        fun filterWidgetDataList()
        fun changeStatus( position:Int)
    }

    interface State : IBase.State {
    }
}