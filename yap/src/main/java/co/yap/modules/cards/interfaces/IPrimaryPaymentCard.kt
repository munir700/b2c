package co.yap.modules.cards.interfaces

import co.yap.yapcore.IBase


interface IPrimaryPaymentCard {

    interface View:IBase.View<ViewModel>

    interface ViewModel:IBase.ViewModel<State>

    interface State : IBase.State
}