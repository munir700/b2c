package co.yap.modules.dashboard.cards.paymentcarddetail.statments.states

import androidx.databinding.Bindable
import co.yap.BR
import co.yap.modules.dashboard.cards.paymentcarddetail.interfaces.IPaymentCardDetail
import co.yap.modules.dashboard.cards.paymentcarddetail.statments.interfaces.ICardStatments
import co.yap.yapcore.BaseState

class CardStatmentsState : BaseState(), ICardStatments.State {

    @get:Bindable
    override var message: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.message)
        }
}