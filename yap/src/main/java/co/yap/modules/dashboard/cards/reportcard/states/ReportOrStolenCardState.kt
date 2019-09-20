package co.yap.modules.dashboard.cards.reportcard.states

import androidx.databinding.Bindable
import co.yap.BR
import co.yap.modules.dashboard.cards.reportcard.interfaces.IRepostOrStolenCard
import co.yap.yapcore.BaseState

class ReportOrStolenCardState : BaseState(), IRepostOrStolenCard.State {

    @get:Bindable
    override var tootlBarTitle: String = "4044 2345 **** 1234"
        set(value) {
            field = value
            notifyPropertyChanged(BR.tootlBarTitle)

        }
    @get:Bindable
    override var tootlBarVisibility: Int = 0x00000000
        set(value) {
            field = value
            notifyPropertyChanged(BR.tootlBarVisibility)

        }
    @get:Bindable
    override var cardType: String = "Spare Card"
        set(value) {
            field = value
            notifyPropertyChanged(BR.cardType)

        }
    @get:Bindable
    override var maskedCardNumber: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.maskedCardNumber)

        }

}