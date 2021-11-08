package co.yap.modules.dashboard.widgets.main

import android.app.Application
import co.yap.networking.customers.models.dashboardwidget.WidgetData
import co.yap.yapcore.BaseViewModel

class WidgetViewModel(application: Application) :
    BaseViewModel<IWidget.State>(application = application),
    IWidget.ViewModel {
    override val state: IWidget.State = WidgetState()
    override var widgetDataList: MutableList<WidgetData> = mutableListOf()

}