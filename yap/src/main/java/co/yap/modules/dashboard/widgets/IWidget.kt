package co.yap.modules.dashboard.widgets

import androidx.databinding.ObservableField
import co.yap.networking.customers.models.dashboardwidget.WidgetData
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.SingleLiveEvent

class IWidget {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State> {
        val clickEvent: SingleClickEvent
        val widgetAdapter: ObservableField<WidgetAdapter>?
        var widgetDataList: MutableList<WidgetData>
        val apiSuccessEvent: SingleLiveEvent<Boolean>

        fun filterWidgetDataList()
        fun requestWidgetUpdation()
        fun handlePressOnView(id: Int)
        fun changeStatus(positionFrom: Int, positionTo: Int, status: Boolean, isDragDrop: Boolean)
        fun getCountOfStatusFromWidgetDataList(): Int
        fun getWidgetShuffledList(): List<WidgetData>
    }

    interface State : IBase.State {
        var isVisibilityChange: ObservableField<Boolean>
    }
}