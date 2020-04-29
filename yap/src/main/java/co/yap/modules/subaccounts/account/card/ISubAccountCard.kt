package co.yap.modules.subaccounts.account.card

import co.yap.networking.customers.household.responsedtos.SubAccount
import co.yap.yapcore.IBase

interface ISubAccountCard {
    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State>
    {
        fun getSubAccount()
        fun resendRequestToHouseHoldUser(account: SubAccount)
        fun RemoveRefundHouseHoldUser(account: SubAccount)
    }

    interface State : IBase.State
}