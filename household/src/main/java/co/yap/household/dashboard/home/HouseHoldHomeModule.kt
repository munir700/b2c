package co.yap.household.dashboard.home

import androidx.recyclerview.widget.RecyclerView
import co.yap.widgets.advrecyclerview.expandable.RecyclerViewExpandableItemManager
import co.yap.yapcore.dagger.di.InjectionViewModelProvider
import co.yap.yapcore.dagger.di.module.fragment.BaseFragmentModule
import co.yap.yapcore.dagger.di.qualifiers.FragmentScope
import co.yap.yapcore.dagger.di.qualifiers.ViewModelInjection
import co.yap.yapcore.helpers.NotificationHelper
import co.yap.yapcore.managers.MyUserManager
import dagger.Module
import dagger.Provides

@Module
class HouseHoldHomeModule : BaseFragmentModule<HouseholdHomeFragment>() {
    @Provides
    @ViewModelInjection
    fun provideHouseHoldHomeVM(
        fragment: HouseholdHomeFragment,
        viewModelProvider: InjectionViewModelProvider<HouseHoldHomeVM>
    ) = viewModelProvider.get(fragment, HouseHoldHomeVM::class)

    @Provides
    @FragmentScope
    fun provideHouseholdHomeState(): IHouseholdHome.State = HouseholdHomeState()

    @Provides
    @FragmentScope
    fun provideRecyclerViewExpandableItemManager() =
        RecyclerViewExpandableItemManager(null)

    @Provides
    @FragmentScope
    fun provideHHNotificationsAdapter(fragment: HouseholdHomeFragment) =
        HHNotificationAdapter(NotificationHelper.getNotifications(MyUserManager.user, MyUserManager.card.value, fragment.requireContext()), null)
//
//    @Provides
//    fun provideHouseholdHomeStates()= HouseholdHomeState()
//    @Provides
//    fun provideTransactionsRepository() = TransactionsRepository

    @Provides
    @FragmentScope
    fun provideHomeTransactionAdapter() = HomeTransactionAdapter()

    @Provides
    @FragmentScope
    fun provideWrappedAdapter(
        adapter: HomeTransactionAdapter,
        mRecyclerViewExpandableItemManager: RecyclerViewExpandableItemManager
    ): RecyclerView.Adapter<*> = mRecyclerViewExpandableItemManager.createWrappedAdapter(adapter)
}
