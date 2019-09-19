package co.yap.modules.dashboard.cards.paymentcarddetail.limits.states

import androidx.databinding.Bindable
import co.yap.BR
import co.yap.modules.dashboard.cards.paymentcarddetail.limits.interfaces.ICardLimits
import co.yap.yapcore.BaseState

class CardLimitState : BaseState(), ICardLimits.State {

    @get:Bindable
    override var serialNumber: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.serialNumber)
        }
}