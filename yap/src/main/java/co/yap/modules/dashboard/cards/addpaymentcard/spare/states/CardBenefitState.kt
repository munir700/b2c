package co.yap.modules.dashboard.cards.addpaymentcard.spare.states

import androidx.databinding.Bindable
import co.yap.BR
import co.yap.modules.dashboard.cards.addpaymentcard.spare.interfaces.ICardBenefit
import co.yap.yapcore.BaseState

class CardBenefitState : BaseState(), ICardBenefit.State {

    @get:Bindable
    override var benefitTitle: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.benefitTitle)
        }

    @get:Bindable
    override var benefitDetail: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.benefitDetail)
        }
}