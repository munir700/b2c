package co.yap.household.onboarding.onboarding.interfaces

import co.yap.household.onboarding.onboarding.fragments.CardColorSelectionModel
import co.yap.household.onboarding.onboarding.fragments.HouseHoldCardSelectionAdapter
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IHouseHoldCardsSelection {
    interface View : IBase.View<ViewModel> {
        fun setObservers()
        fun setUpUI()
    }

    interface ViewModel : IBase.ViewModel<State> {
        fun initViews()
        val clickEvent: SingleClickEvent
        fun handlePressOnButton(id: Int)
        fun getCardsColorList(): MutableList<CardColorSelectionModel>
        fun getCardsColorListRequest()
        var adapter: HouseHoldCardSelectionAdapter

    }

    interface State : IBase.State {
        var cardsHeading: String
        var locationVisibility: Boolean
        var cardAddressTitle: String
        var cardAddressSubTitle: String
    }
}