package co.yap.household.onboarding.onboarding.states

import androidx.databinding.Bindable
import co.yap.household.BR
import co.yap.household.onboarding.onboarding.interfaces.IHouseHoldCardsSelection
import co.yap.yapcore.BaseState

class HouseHoldCardsSelectionState : BaseState(), IHouseHoldCardsSelection.State {

    @get:Bindable
    override var cardsHeading: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.cardsHeading)
        }
    @get:Bindable
    override var locationVisibility: Boolean = true
        set(value) {
            field = value
            notifyPropertyChanged(BR.locationVisibility)
        }
    @get:Bindable
    override var cardAddressTitle: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.cardAddressTitle)
        }
    @get:Bindable
    override var cardAddressSubTitle: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.cardAddressSubTitle)
        }
}