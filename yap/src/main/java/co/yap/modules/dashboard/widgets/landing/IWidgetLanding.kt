package co.yap.modules.dashboard.widgets.landing

import androidx.databinding.ObservableField
import co.yap.networking.customers.models.dashboardwidget.WidgetData
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

class IWidgetLanding {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State> {
        val clickEvent: SingleClickEvent
        val widgetAdapter: ObservableField<WidgetAdapter>?
        var widgetDataList: MutableList<WidgetData>
        fun filterWidgetDataList()
        fun handlePressOnView(id: Int)
        fun changeStatus( position:Int, status:Boolean)
    }

    interface State : IBase.State {
    }
}