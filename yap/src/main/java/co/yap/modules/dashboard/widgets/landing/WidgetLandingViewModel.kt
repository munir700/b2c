package co.yap.modules.dashboard.widgets.landing

import android.app.Application
import androidx.databinding.ObservableField
import co.yap.modules.dashboard.widgets.main.WidgetViewModel
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.models.dashboardwidget.WidgetData
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.SingleLiveEvent

class WidgetLandingViewModel(application: Application) :
    BaseViewModel<IWidgetLanding.State>(application = application),
    IWidgetLanding.ViewModel {
    private val customerRepository: CustomersRepository = CustomersRepository
    override val widgetAdapter: ObservableField<WidgetAdapter> = ObservableField()
    override var widgetDataList: MutableList<WidgetData> = mutableListOf()
    override val apiSuccessEvent: SingleLiveEvent<Boolean> = SingleLiveEvent()
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val state: IWidgetLanding.State = WidgetLandingState()

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

    var parentViewModel: WidgetViewModel? = null

    override fun filterWidgetDataList() {
        widgetDataList.forEach {
            it.isPinned = false
            it.isShuffled = false
        }
        sortList()
        val index = widgetDataList.count {
            it.status == true
        }
        widgetDataList.add(index, WidgetData(id = 2000, name = "Hidden"))
        widgetAdapter.get()?.setData(widgetDataList)
    }

    override fun changeStatus( positionFrom: Int, positionTo: Int, status: Boolean, isDragDrop: Boolean) {
        val widgetData = widgetDataList[positionFrom]
        widgetData.status = status
        widgetData.isPinned = false
        widgetData.isShuffled = true
        when( isDragDrop){
            false->{
                widgetDataList.removeAt(positionFrom)
            }
        }
        when (status) { //status true mean it is add to dashboard widget bar section and false mean it is added to hidden section
            true -> {
                when(isDragDrop){
                    false->{
                        widgetDataList.add(widgetDataList.count {
                            it.status == true
                        }, widgetData)
                    }
                }
            }
            else -> {
                widgetDataList.add(widgetDataList.size, widgetData)
            }
        }
        widgetAdapter.get()?.setData(widgetDataList)
    }

    fun sortList() {
        widgetDataList = widgetDataList.sortedByDescending { it.status }.toMutableList()
    }

    fun getWidgetShuffledList(): List<WidgetData> {
        val count  = widgetDataList.count {
            it.isShuffled == true
        }
        return if( count > 0){
            val tempWidgetList = widgetDataList.filter { it.isShuffled == true || it.status == true }
            tempWidgetList.forEachIndexed { index, widgetData ->
                if (widgetData.status == true) {
                    widgetData.shuffleIndex = index + 1
                } else {
                    widgetData.shuffleIndex = -1
                }
            }
            tempWidgetList
        }else{
            emptyList()
        }
    }

    override fun requestWidgetUpdation() {
        launch {
            if(getWidgetShuffledList().isNotEmpty()) {
                state.loading = true
                when (val response =
                    customerRepository.updateDashboardWidget(getWidgetShuffledList())) {
                    is RetroApiResponse.Success -> {
                        apiSuccessEvent.value = true
                    }
                    is RetroApiResponse.Error -> {
                        state.loading = false
                    }
                }
            }else{
                apiSuccessEvent.value = false
            }
        }
    }
}