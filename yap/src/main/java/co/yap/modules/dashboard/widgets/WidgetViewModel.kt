package co.yap.modules.dashboard.widgets

import android.app.Application
import androidx.databinding.ObservableField
import co.yap.networking.customers.models.dashboardwidget.WidgetData
import co.yap.yapcore.BaseViewModel

class WidgetViewModel(application: Application) :
    BaseViewModel<IWidget.State>(application = application),
    IWidget.ViewModel {
    override val state: IWidget.State = WidgetState()
    override val widgetAdapter: ObservableField<WidgetAdapter>? = ObservableField()
    override var widgetDataList: MutableList<WidgetData> = mutableListOf()

    fun getWidgetList() {
        widgetDataList.sortedByDescending { it.status }
        val index = widgetDataList.count {
            it.status == true
        }
        widgetDataList.add(index, WidgetData(id = -1, name = "Heading"))

//        widgetDataList.run {
//            sortedByDescending { it.status }.toMutableList().run {
//                add(
//                    count { count -> count.status == true },
//                    WidgetData(id = -1, name = "Heading")
//                )
//            }
//        }

        widgetAdapter?.get()?.setData(widgetDataList)
    }

}