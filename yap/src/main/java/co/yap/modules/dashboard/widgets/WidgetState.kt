package co.yap.modules.dashboard.widgets

import androidx.lifecycle.MutableLiveData
import co.yap.yapcore.BaseState

class WidgetState : BaseState(), IWidget.State {
    override var widgetMap: MutableLiveData<MutableMap<String?, List<Widget>>>? = MutableLiveData()
}