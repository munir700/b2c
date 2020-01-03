package co.yap.household.onboarding.onboarding.interfaces

import co.yap.yapcore.IBase

interface IHouseHoldCardsSelection {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State>{
        fun initViews()
    }
    interface State : IBase.State{
        var cardsHeading:String
    }
}