package co.yap.modules.dashboard.widgets

import android.app.Application
import androidx.databinding.ObservableField
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.models.dashboardwidget.WidgetData
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.SingleLiveEvent

class WidgetViewModel(application: Application) :
    BaseViewModel<IWidget.State>(application = application),
    IWidget.ViewModel {
    private val customerRepository: CustomersRepository = CustomersRepository
    override val widgetAdapter: ObservableField<WidgetAdapter> = ObservableField()
    override var widgetDataList: MutableList<WidgetData> = mutableListOf()
    override val apiSuccessEvent: SingleLiveEvent<Boolean> = SingleLiveEvent()
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val state: IWidget.State = WidgetState()

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

    override fun filterWidgetDataList() {
        widgetDataList.forEach {
            it.isPinned = false
            it.isShuffled = false
        }
        sortList()
        widgetDataList.add(
            getCountOfStatusFromWidgetDataList(),
            WidgetData(id = 10000, name = "Hidden")
        )
        widgetAdapter.get()?.setData(widgetDataList)
    }

    /**
     * This method will change the status and isShuffled which will update the item list
     */
    override fun changeStatus(
        positionFrom: Int,
        positionTo: Int,
        status: Boolean,
        isDragDrop: Boolean
    ) {
        val widgetData = widgetDataList[positionFrom]
        widgetData.status = status
        widgetData.isPinned = false
        widgetData.isShuffled = true
        when (isDragDrop) { //isDragDrop true mean user have drag and drop item only in widget bar section so we did not wanted to remove this item in list because it will be handle in onMoveItem method of widgetAdapter
            false -> {
                widgetDataList.removeAt(positionFrom)
            }
        }
        when (status) { //status true mean it is add to dashboard widget bar section and false mean it is added to hidden section
            true -> {
                when (isDragDrop) {//isDragDrop true mean user have drag and drop item only in widget bar section so we did not wanted to remove this item in list because it will be handle in onMoveItem method of widgetAdapter
                    false -> {
                        widgetDataList.add(positionTo, widgetData)
                    }
                }
            }
            else -> {
                widgetDataList.add(positionTo, widgetData)
            }
        }
        widgetAdapter.get()?.setData(widgetDataList)
    }

    /**
     * This will return the total count of widget added in widget bar section
     */
    override fun getCountOfStatusFromWidgetDataList(): Int {
        return widgetDataList.count {
            it.status == true
        }
    }

    /**
     * This method will calculate how many item in the list is shuffled and will update the shuffledIndex with its position in the list in widget section and in hidden section shuffle index will be -1
     */
    override fun getWidgetShuffledList(): List<WidgetData> {
        val count = widgetDataList.count {
            it.isShuffled == true
        }
        return if (count > 0) {
            val tempWidgetList =
                widgetDataList.filter { it.isShuffled == true || it.status == true }
            tempWidgetList.forEachIndexed { index, widgetData ->
                if (widgetData.status == true) {
                    widgetData.shuffleIndex = index + 1
                } else {
                    widgetData.shuffleIndex = 0
                }
            }
            tempWidgetList
        } else {
            emptyList()
        }
    }

    override fun requestWidgetUpdation() {
        launch {
            if (getWidgetShuffledList().isNotEmpty()) {
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
            } else {
                apiSuccessEvent.value = false
            }
        }
    }

    /**
     * This method will sort the widgetDatalist by it status and status value true will be shown first
     */
    private fun sortList() {
        widgetDataList = widgetDataList.sortedByDescending { it.status }.toMutableList()
    }
}