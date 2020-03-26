package co.yap.household.dashboard.subaccounts.householdsubaccounts

import android.app.Application
import co.yap.yapcore.BaseViewModel

class HouseHoldSubAccountsViewModel(application: Application) :
    BaseViewModel<IHouseHoldSubAccounts.State>(application),
    IHouseHoldSubAccounts.ViewModel {
    override val state: HouseHoldSubAccountsState =
        HouseHoldSubAccountsState()
}