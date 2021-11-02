package co.yap.modules.dashboard.widgets

import android.app.Application
import androidx.databinding.ObservableField
import co.yap.yapcore.BaseViewModel

class WidgetViewModel(application: Application) :
    BaseViewModel<IWidget.State>(application = application),
    IWidget.ViewModel {
    override val state: IWidget.State = WidgetState()

    override var widgetAdapter: ObservableField<WidgetAdapter>? = ObservableField()

    fun getWidgetList() {
        val widgetData = mutableListOf<Widget>()

        for (i in 0..1) {
            for (j in 0..5) {
                when (i) {
                    0 -> {
                        when (j) {
                            5 -> {
                                val widget = Widget(-1, "Send money")
                                widgetData.add(widget)
                            }
                            else -> {
                                val widget = Widget(i, "Send money")
                                widgetData.add(widget)
                            }
                        }
                    }
                    1 -> {
                        val widget = Widget(i, "Coins")
                        widgetData.add(widget)
                    }
                }
            }
        }
        state.widgetMap?.value = widgetData
        widgetAdapter?.get()?.setData(state.widgetMap?.value)
    }

}