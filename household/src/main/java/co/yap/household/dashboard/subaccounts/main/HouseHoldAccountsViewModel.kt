package co.yap.household.dashboard.subaccounts.main

import android.app.Application
import co.yap.yapcore.BaseViewModel

class HouseHoldAccountsViewModel(application: Application) :
    BaseViewModel<IHouseHoldAccounts.State>(application = application),
    IHouseHoldAccounts.ViewModel {
    override val state: HouseHoldAccountsState = HouseHoldAccountsState()
}