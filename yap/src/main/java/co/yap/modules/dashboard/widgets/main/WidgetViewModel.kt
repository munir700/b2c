package co.yap.modules.dashboard.widgets.main

import android.app.Application
import co.yap.networking.customers.models.dashboardwidget.WidgetData
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent

class WidgetViewModel(application: Application) :
    BaseViewModel<IWidget.State>(application = application),
    IWidget.ViewModel {
    override val state: IWidget.State = WidgetState()
    override var widgetDataList: MutableList<WidgetData> = mutableListOf()
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

}