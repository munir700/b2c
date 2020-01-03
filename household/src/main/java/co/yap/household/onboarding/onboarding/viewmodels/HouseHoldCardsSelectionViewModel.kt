package co.yap.household.onboarding.onboarding.viewmodels

import android.app.Application
import co.yap.household.onboarding.onboarding.interfaces.IHouseHoldCardsSelection
import co.yap.household.onboarding.onboarding.states.HouseHoldCardsSelectionState
import co.yap.translation.Strings
import co.yap.yapcore.BaseViewModel

class HouseHoldCardsSelectionViewModel(application: Application) :
    BaseViewModel<IHouseHoldCardsSelection.State>(application), IHouseHoldCardsSelection.ViewModel {
    override val state: HouseHoldCardsSelectionState = HouseHoldCardsSelectionState()
    override fun onCreate() {
        super.onCreate()
        initViews()
    }

    override fun initViews() {
        state.cardsHeading =
            getString(Strings.screen_house_hold_card_color_selection_display_text_heading)
    }

}