package co.yap.modules.dashboard.widgets.landing

import android.app.Application
import androidx.databinding.ObservableField
import co.yap.modules.dashboard.main.viewmodels.YapDashBoardViewModel
import co.yap.modules.dashboard.widgets.main.WidgetViewModel
import co.yap.networking.customers.models.dashboardwidget.WidgetData
import co.yap.yapcore.BaseViewModel

class WidgetLandingViewModel(application: Application) :
    BaseViewModel<IWidgetLanding.State>(application = application),
    IWidgetLanding.ViewModel {
    override val widgetAdapter: ObservableField<WidgetAdapter>? = ObservableField()
    override var widgetDataList: MutableList<WidgetData> = mutableListOf()
    override val state: IWidgetLanding.State =  WidgetLandingState()


    override fun filterWidgetDataList() {
        for( widgetData in widgetDataList){
            widgetData.isPinned = false
            widgetData.clickId = System.currentTimeMillis()
        }
        widgetDataList.sortedByDescending { it.status }
        val index = widgetDataList.count {
            it.status == true
        }
        widgetDataList.add(index, WidgetData(id = -1, name = "Heading"))
        widgetAdapter?.get()?.setData(widgetDataList)
    }

    var parentViewModel: WidgetViewModel? = null
}