package co.yap.modules.dashboard.widgets.landing

import androidx.databinding.ObservableField
import co.yap.yapcore.BaseState

class WidgetLandingState : BaseState(), IWidgetLanding.State {
    override var isVisibilityChange: ObservableField<Boolean> = ObservableField(false)
}