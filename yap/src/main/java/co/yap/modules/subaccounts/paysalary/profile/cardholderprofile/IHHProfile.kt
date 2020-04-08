package co.yap.modules.subaccounts.paysalary.profile.cardholderprofile

import co.yap.yapcore.IBase

interface IHHProfile {
    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State>

    interface State : IBase.State
}