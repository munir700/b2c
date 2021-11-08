package co.yap.modules.dashboard.widgets.landing

import android.app.Application
import androidx.databinding.ObservableField
import co.yap.modules.dashboard.widgets.main.WidgetViewModel
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.models.dashboardwidget.WidgetData
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleLiveEvent

class WidgetLandingViewModel(application: Application) :
    BaseViewModel<IWidgetLanding.State>(application = application),
    IWidgetLanding.ViewModel {
    private val customerRepository: CustomersRepository = CustomersRepository
    override val widgetAdapter: ObservableField<WidgetAdapter>? = ObservableField()
    override var widgetDataList: MutableList<WidgetData> = mutableListOf()
    override val state: IWidgetLanding.State = WidgetLandingState()
    override val apiSuccessEvent: SingleLiveEvent<Boolean> = SingleLiveEvent()

    var parentViewModel: WidgetViewModel? = null

    override fun filterWidgetDataList() {
        widgetDataList.forEach {
            it.isPinned = false
        }

        reverseList()
        val index = widgetDataList.count {
            it.status == true
        }
        widgetDataList.add(index, WidgetData(id = 2000, name = "Heading"))
        widgetAdapter?.get()?.setData(widgetDataList)
    }

    override fun changeStatus( position:Int) {
        val widgetData = widgetDataList[position]
        widgetData.status = false
        widgetData.isPinned = false
        widgetDataList.removeAt(position)
        widgetDataList.add(widgetDataList.size, widgetData)
//        widgetAdapter?.get()?.removeAt(position)
        widgetAdapter?.get()?.setData(widgetDataList)
    }

    fun reverseList() {
        widgetDataList = widgetDataList.sortedByDescending { it.status }.toMutableList()
    }

    override fun requestWidgetUpdation() {
        val widgetData = WidgetData(id = 8, status = false, shuffleIndex = 0)
        var list: MutableList<WidgetData>? = mutableListOf()
        list?.add(widgetData)
        launch {
            state.loading = true
            when (val response = list?.let { customerRepository.updateDashboardWidget(it) }) {
                is RetroApiResponse.Success -> {
                    signInButtonPressEvent.value = true
                }
                is RetroApiResponse.Error -> {
                    state.loading = false
                }
            }
        }
    }
}