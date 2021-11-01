package co.yap.modules.dashboard.widgets

import android.app.Application
import androidx.databinding.ObservableField
import co.yap.translation.Strings
import co.yap.yapcore.BaseViewModel

class WidgetViewModel(application: Application) :
    BaseViewModel<IWidget.State>(application = application),
    IWidget.ViewModel {
    override val state: IWidget.State = WidgetState()

    override var widgetAdapter: ObservableField<WidgetAdapter>? = ObservableField()

    fun getWidgetList() {
        val widgetData = mutableMapOf<String?, List<Widget>>()

        for (i in 0..1) {
            val list = arrayListOf<Widget>()
            for (j in 0..5) {
                when (i) {
                    0 -> {
                        val widget = Widget(i, "Send money")
                        list.add(widget)
                        widgetData["Hide Widgets"] = list.toList()
                    }
                    1 -> {
                        val widget = Widget(i, "Coins")
                        list.add(widget)
                        widgetData["Hidden"] = list.toList()
                    }
                }
            }
        }
        state.widgetMap?.value = widgetData
        widgetAdapter?.get()?.setData(state.widgetMap?.value)
    }

}