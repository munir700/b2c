package co.yap.household.dashboard.home

import co.yap.yapcore.dagger.di.InjectionViewModelProvider
import co.yap.yapcore.dagger.di.module.fragment.BaseFragmentModule
import co.yap.yapcore.dagger.di.qualifiers.FragmentScope
import co.yap.yapcore.dagger.di.qualifiers.ViewModelInjection
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
    fun provideHouseholdHomeState():IHouseholdHome.State = HouseholdHomeState()

    @Provides
    fun provideHHNotificationsAdapter() =
        HHNotificationAdapter(
            mutableListOf(),
            null
        )
//
//    @Provides
//    fun provideHouseholdHomeStates()= HouseholdHomeState()
//    @Provides
//    fun provideTransactionsRepository() = TransactionsRepository

}