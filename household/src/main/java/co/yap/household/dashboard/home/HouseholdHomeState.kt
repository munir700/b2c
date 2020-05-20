package co.yap.household.dashboard.home

import androidx.databinding.ObservableField
import co.yap.modules.dashboard.home.adaptor.NotificationAdapter
import co.yap.networking.transactions.responsedtos.transaction.HomeTransactionListData
import co.yap.yapcore.BaseState


class HouseholdHomeState : BaseState(), IHouseholdHome.State {
    override val transactionList: ObservableField<MutableList<HomeTransactionListData>> =  ObservableField(mutableListOf())
}
