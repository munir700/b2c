package co.yap.modules.dashboard.cards.addpaymentcard.spare.states

import androidx.databinding.Bindable
import co.yap.BR
import co.yap.modules.dashboard.cards.addpaymentcard.spare.interfaces.IAddSpareCard
import co.yap.yapcore.BaseState

class AddSpareCardState : BaseState(), IAddSpareCard.State {

    @get:Bindable
    override var cardType: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.cardType)
        }

    //add virtual card layout fields
    @get:Bindable
    override var virtualCardFee: String = "AED 29.99"
        set(value) {
            field = value
            notifyPropertyChanged(BR.virtualCardFee)
        }

    @get:Bindable
    override var virtualCardAvaialableBalance: String = "AED 29.99"
        set(value) {
            field = value
            notifyPropertyChanged(BR.virtualCardAvaialableBalance)
        }

    //add physical card layout fields
    @get:Bindable
    override var physicalCardFee: String = "AED 29.99"
        set(value) {
            field = value
            notifyPropertyChanged(BR.physicalCardFee)
        }

    @get:Bindable
    override var physicalCardAvaialableBalance: String = "AED 29.99"
        set(value) {
            field = value
            notifyPropertyChanged(BR.physicalCardAvaialableBalance)
        }

    @get:Bindable
    override var physicalCardAddressTitle: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.physicalCardAddressTitle)
        }

    @get:Bindable
    override var physicalCardAddressSubTitle: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.physicalCardAddressSubTitle)
        }

    @get:Bindable
    override var physicalCardAddressConfirmed: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.physicalCardAddressConfirmed)
        }


    @get:Bindable
    override var physicalCardAddressCheckVisibility: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.physicalCardAddressCheckVisibility)
        }


    @get:Bindable
    override var physicalCardAddressButtonsVisibility: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.physicalCardAddressButtonsVisibility)
        }

}