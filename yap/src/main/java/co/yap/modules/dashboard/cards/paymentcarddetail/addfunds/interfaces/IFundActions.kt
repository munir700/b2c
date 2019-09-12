package co.yap.modules.dashboard.cards.paymentcarddetail.addfunds.interfaces

import co.yap.yapcore.IBase

interface IFundActions {

    interface View: IBase.View<ViewModel>

    interface ViewModel: IBase.ViewModel<State>

    interface State : IBase.State
}