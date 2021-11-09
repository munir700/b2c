package co.yap.modules.dashboard.widgets.main

import co.yap.networking.customers.models.dashboardwidget.WidgetData
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IWidget {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State> {
        var widgetDataList: MutableList<WidgetData>
        val clickEvent: SingleClickEvent
        fun handlePressOnView(id: Int)
    }

    interface State : IBase.State {
    }
}