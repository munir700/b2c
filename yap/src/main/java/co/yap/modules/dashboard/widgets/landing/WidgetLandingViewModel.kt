package co.yap.modules.dashboard.widgets.landing

import android.app.Application
import androidx.databinding.ObservableField
import co.yap.modules.dashboard.widgets.main.WidgetViewModel
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.models.dashboardwidget.WidgetData
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleLiveEvent
import co.yap.yapcore.SingleClickEvent

class WidgetLandingViewModel(application: Application) :
    BaseViewModel<IWidgetLanding.State>(application = application),
    IWidgetLanding.ViewModel {
    private val customerRepository: CustomersRepository = CustomersRepository
    override val widgetAdapter: ObservableField<WidgetAdapter>? = ObservableField()
    override var widgetDataList: MutableList<WidgetData> = mutableListOf()
     override val apiSuccessEvent: SingleLiveEvent<Boolean> = SingleLiveEvent()
    override val state: IWidgetLanding.State = WidgetLandingState()
    override val clickEvent: SingleClickEvent = SingleClickEvent()

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }
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

    override fun requestWidgetUpdation() {
        val widgetData = WidgetData(id = 8, status = false, shuffleIndex = 0)
        var list: MutableList<WidgetData>? = mutableListOf()
        list?.add(widgetData)
        launch {
            state.loading = true
            when (val response = list?.let { customerRepository.updateDashboardWidget(it) }) {
                is RetroApiResponse.Success -> {
                    apiSuccessEvent.value = true
                }
                is RetroApiResponse.Error -> {
                    state.loading = false
                }
            }
        }
    }
}