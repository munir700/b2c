package co.yap.modules.dashboard.transaction.totalpurchases

import co.yap.yapcore.IBase

interface ITotalPurchases {
    interface View: IBase.View<ViewModel>{}
    interface ViewModel:IBase.ViewModel<State> {}
    interface State : IBase.State{}
}