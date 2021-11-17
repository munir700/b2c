package co.yap.modules.dashboard.widgets

import androidx.databinding.ObservableField
import co.yap.yapcore.BaseState

class WidgetState : BaseState(), IWidget.State {
    override var isVisibilityChange: ObservableField<Boolean> = ObservableField(false)
}