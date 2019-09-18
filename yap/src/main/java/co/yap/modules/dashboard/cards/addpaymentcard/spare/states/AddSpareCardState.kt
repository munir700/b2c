package co.yap.modules.dashboard.cards.addpaymentcard.spare.states

import androidx.databinding.Bindable
import co.yap.BR
import co.yap.modules.dashboard.cards.addpaymentcard.spare.interfaces.IAddSpareCard
import co.yap.yapcore.BaseState

class AddSpareCardState : BaseState(), IAddSpareCard.State {

    val VISIBLE: Int = 0x00000000
    val GONE: Int = 0x00000008

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
    override var virtualCardAvaialableBalance: String = "AED 39.99"
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
    override var physicalCardAvaialableBalance: String = "AED 39.99"
        set(value) {
            field = value
            notifyPropertyChanged(BR.physicalCardAvaialableBalance)
        }

    @get:Bindable
    override var physicalCardAddressTitle: String = "12 Street Road 10"
        set(value) {
            field = value
            notifyPropertyChanged(BR.physicalCardAddressTitle)
        }

    @get:Bindable
    override var physicalCardAddressSubTitle: String = "Suite 102. Dubai, UAE"
        set(value) {
            field = value
            notifyPropertyChanged(BR.physicalCardAddressSubTitle)
        }

    @get:Bindable
    override var toggleVisibility: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.toggleVisibility)
            if (!field) {
                physicalCardAddressButtonsVisibility = VISIBLE
                physicalCardAddressCheckVisibility = GONE
            } else {
                physicalCardAddressButtonsVisibility = GONE
                physicalCardAddressCheckVisibility = VISIBLE
            }

        }

  @get:Bindable
    override var onChangeLocationClick: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.onChangeLocationClick)

        }


    @get:Bindable
    override var physicalCardAddressCheckVisibility: Int = GONE
        set(value) {
            field = value
            notifyPropertyChanged(BR.physicalCardAddressCheckVisibility)
        }


    @get:Bindable
    override var physicalCardAddressButtonsVisibility: Int = VISIBLE
        set(value) {
            field = value
            notifyPropertyChanged(BR.physicalCardAddressButtonsVisibility)

        }
}