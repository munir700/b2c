package co.yap.modules.dashboard.widgets

import androidx.databinding.ObservableBoolean
import co.yap.yapcore.BaseState

class EditWidgetState: BaseState(),IEditWidget.State {
    override var toolBarVisibility: ObservableBoolean? = ObservableBoolean(false)
    override var toolBarRightIconVisibility: ObservableBoolean? = ObservableBoolean(false)
}