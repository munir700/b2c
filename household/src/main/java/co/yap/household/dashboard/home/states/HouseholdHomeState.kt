package co.yap.household.dashboard.home.states

import androidx.databinding.ObservableField
import co.yap.household.dashboard.home.interfaces.IHouseholdHome
import co.yap.networking.transactions.responsedtos.transaction.HomeTransactionListData
import co.yap.yapcore.BaseState

class HouseholdHomeState : BaseState(), IHouseholdHome.State {
    override val transactionList: ObservableField<MutableList<HomeTransactionListData>> =
        ObservableField(mutableListOf())
}