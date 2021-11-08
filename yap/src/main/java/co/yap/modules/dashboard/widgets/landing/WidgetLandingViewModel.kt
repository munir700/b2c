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

    var parentViewModel: WidgetViewModel? = null

    override fun filterWidgetDataList() {
        widgetDataList.forEach {
            it.isPinned = false
        }
        sortList()
        val index = widgetDataList.count {
            it.status == true
        }
        widgetDataList.add(index, WidgetData(id = 2000 , name = "Heading"))
        widgetAdapter?.get()?.setData(widgetDataList)
    }

    override fun changeStatus( position:Int, status:Boolean) {
        val widgetData = widgetDataList[position]
        widgetData.status = status
        widgetData.isPinned = false
        widgetDataList.removeAt(position)
        when(status){
            true->{
                widgetDataList.add(widgetDataList.count {
                    it.status == true
                }, widgetData)
            }else->{
            widgetDataList.add(widgetDataList.size, widgetData)
            }
        }
        widgetAdapter?.get()?.setData(widgetDataList)
    }

    fun sortList(){
        widgetDataList = widgetDataList.sortedByDescending { it.status }.toMutableList()
    }
}