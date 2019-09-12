package co.yap.modules.dashboard.cards.paymentcarddetail.addfunds.interfaces

import co.yap.modules.dashboard.cards.paymentcarddetail.models.CardInfoModel
import co.yap.yapcore.IBase

interface IFundActions {

    interface View: IBase.View<ViewModel>

    interface ViewModel: IBase.ViewModel<State>{
    }

    interface State : IBase.State{
        var toolBarHeader:String
        var cardName:String
        var cardNumber:String
    }
}