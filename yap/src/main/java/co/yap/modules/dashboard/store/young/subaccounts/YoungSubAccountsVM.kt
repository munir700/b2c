package co.yap.modules.dashboard.store.young.subaccounts

import co.yap.networking.customers.household.responsedtos.SubAccount
import co.yap.yapcore.dagger.base.viewmodel.BaseRecyclerAdapterVM
import javax.inject.Inject

class YoungSubAccountsVM @Inject constructor(override val state: IYoungSubAccounts.State) :
    BaseRecyclerAdapterVM<SubAccount, IYoungSubAccounts.State>(), IYoungSubAccounts.ViewModel {
    override fun getSubAccount() {
    }
}