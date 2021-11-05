package co.yap.modules.dashboard.widgets

import androidx.databinding.ObservableField
import co.yap.networking.customers.models.dashboardwidget.WidgetData
import co.yap.yapcore.IBase

interface IWidget {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State> {
        val widgetAdapter: ObservableField<WidgetAdapter>?
        var widgetDataList: MutableList<WidgetData>
        fun filterWidgetDataList()
    }

    interface State : IBase.State {
    }
}