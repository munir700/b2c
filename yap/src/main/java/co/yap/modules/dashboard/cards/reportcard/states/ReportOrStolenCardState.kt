package co.yap.modules.dashboard.cards.reportcard.states

import androidx.databinding.Bindable
import co.yap.BR
import co.yap.modules.dashboard.cards.reportcard.interfaces.IRepostOrStolenCard
import co.yap.yapcore.BaseState

class ReportOrStolenCardState : BaseState(), IRepostOrStolenCard.State {

    @get:Bindable
    override var cardType: String = "Spare Card"
        set(value) {
            field = value
            notifyPropertyChanged(BR.cardType)

        }
    @get:Bindable
    override var maskedCardNumber: String = "4044 2345 **** 1234"
        set(value) {
            field = value
            notifyPropertyChanged(BR.maskedCardNumber)

        }

}