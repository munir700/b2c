package co.yap.household.dashboard.subaccounts

import androidx.databinding.ObservableField
import co.yap.yapcore.BaseState

class HouseHoldAccountsParentState : BaseState(), IHouseHoldAccountsParent.State {
    override var toolBarTitle: ObservableField<String> = ObservableField("")

}