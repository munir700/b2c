package co.yap.modules.dashboard.widgets.main

import androidx.databinding.ObservableField
import co.yap.modules.dashboard.widgets.landing.WidgetAdapter
import co.yap.networking.customers.models.dashboardwidget.WidgetData
import co.yap.yapcore.IBase

interface IWidget {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State> {
        var widgetDataList: MutableList<WidgetData>
    }

    interface State : IBase.State {
    }
}