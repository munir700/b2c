package co.yap.modules.dashboard.widgets.landing

import androidx.databinding.ObservableField
import co.yap.networking.customers.models.dashboardwidget.WidgetData
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleLiveEvent

class IWidgetLanding {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State> {
        val widgetAdapter: ObservableField<WidgetAdapter>?
        var widgetDataList: MutableList<WidgetData>
        val apiSuccessEvent: SingleLiveEvent<Boolean>

        fun filterWidgetDataList()
        fun changeStatus( position:Int)
        fun requestWidgetUpdation()
    }

    interface State : IBase.State {
    }
}