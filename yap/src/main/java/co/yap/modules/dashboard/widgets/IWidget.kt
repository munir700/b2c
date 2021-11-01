package co.yap.modules.dashboard.widgets

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.yapcore.IBase

interface IWidget {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State> {
        val widgetAdapter: ObservableField<WidgetAdapter>?
    }

    interface State : IBase.State {
        var widgetMap: MutableLiveData<MutableMap<String?, List<Widget>>>?
    }
}