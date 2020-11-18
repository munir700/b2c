package co.yap.sendmoney.home.main

import co.yap.yapcore.IBase

class ISMBeneficiaryParent {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State>
    interface State : IBase.State
}
