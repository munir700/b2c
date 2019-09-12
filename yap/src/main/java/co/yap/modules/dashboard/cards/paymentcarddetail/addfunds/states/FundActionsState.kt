package co.yap.modules.dashboard.cards.paymentcarddetail.addfunds.states

import androidx.databinding.Bindable
import co.yap.BR
import co.yap.modules.dashboard.cards.paymentcarddetail.addfunds.interfaces.IFundActions
import co.yap.yapcore.BaseState

class FundActionsState : BaseState(),IFundActions.State {
    @get:Bindable
    override var cardNumber: String=""
        set(value) {
            field=value
            notifyPropertyChanged(BR.cardNumber)
        }

    @get:Bindable
    override var toolBarHeader: String=""
        set(value) {
            field=value
            notifyPropertyChanged(BR.toolBarHeader)
        }

    @get:Bindable
    override var cardName: String=""
        set(value) {
            field=value
            notifyPropertyChanged(BR.cardName)
        }
}