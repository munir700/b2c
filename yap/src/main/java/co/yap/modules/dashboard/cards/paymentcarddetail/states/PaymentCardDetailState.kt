package co.yap.modules.dashboard.cards.paymentcarddetail.states

import androidx.databinding.Bindable
import co.yap.BR
import co.yap.modules.dashboard.cards.paymentcarddetail.interfaces.IPaymentCardDetail
import co.yap.yapcore.BaseState

class PaymentCardDetailState : BaseState(), IPaymentCardDetail.State{


    @get:Bindable
    override var accountType: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.accountType)
        }

    @get:Bindable
    override var cardType: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.cardType)
        }

    @get:Bindable
    override var cardPanNumber: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.cardPanNumber)
        }

    @get:Bindable
    override var cardBalance: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.cardBalance)
        }

    @get:Bindable
    override var cardName: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.cardName)
        }

    @get:Bindable
    override var blocked: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.blocked)
        }
}