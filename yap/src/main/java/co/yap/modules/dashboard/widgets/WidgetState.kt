package co.yap.modules.dashboard.widgets

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import co.yap.yapcore.BaseState

class WidgetState : BaseState(), IWidget.State {
    override var toolBarVisibility: ObservableBoolean? = ObservableBoolean(false)
    override var toolBarRightIconVisibility: ObservableBoolean? = ObservableBoolean(false)

    override var widgetMap: MutableLiveData<MutableMap<String?, List<Widget>>>? = MutableLiveData()
}