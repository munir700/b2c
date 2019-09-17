package co.yap.modules.dashboard.cards.status.state

import androidx.databinding.Bindable
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.cards.status.interfaces.IYapCardStatus
import co.yap.yapcore.BaseState

class YapCardStatusState : BaseState(), IYapCardStatus.State {

    @get:Bindable
    override var valid: Boolean = true
        set(value) {
            field = value
            notifyPropertyChanged(BR.valid)
        }

    @get:Bindable
    override var ordered: Int = R.drawable.ic_tick
        set(value) {
            field = value
            notifyPropertyChanged(BR.ordered)
        }

    @get:Bindable
    override var building: Int = R.drawable.ic_tick
        set(value) {
            field = value
            notifyPropertyChanged(BR.building)
        }

    @get:Bindable
    override var shipping: Int = R.drawable.ic_tick_disabled
        set(value) {
            field = value
            notifyPropertyChanged(BR.shipping)
        }

    @get:Bindable
    override var buildingProgress: Int = 100
        set(value) {
            field = value
            notifyPropertyChanged(BR.buildingProgress)
        }

    @get:Bindable
    override var shippingProgress: Int = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.shippingProgress)
        }

    @get:Bindable
    override var totalProgress: Int = 100
        set(value) {
            field = value
            notifyPropertyChanged(BR.totalProgress)
        }
}