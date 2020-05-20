package co.yap.household.dashboard.main

import co.yap.household.dashboard.main.menu.ProfilePictureAdapter
import co.yap.yapcore.adpters.SectionsPagerAdapter
import co.yap.yapcore.dagger.di.InjectionViewModelProvider
import co.yap.yapcore.dagger.di.module.fragment.BaseFragmentModule
import co.yap.yapcore.dagger.di.qualifiers.FragmentScope
import co.yap.yapcore.dagger.di.qualifiers.ViewModelInjection
import co.yap.yapcore.managers.MyUserManager
import dagger.Module
import dagger.Provides

@Module
class HouseHoldDashboardModule : BaseFragmentModule<HouseholdDashboardFragment>() {

    @Provides
    @ViewModelInjection
    @FragmentScope
    fun provideDashBoardVM(
        fragment: HouseholdDashboardFragment,
        viewModelProvider: InjectionViewModelProvider<HouseHoldDashBoardVM>
    ) = viewModelProvider.get(fragment, HouseHoldDashBoardVM::class)

    @Provides
    @FragmentScope
    fun provideHouseholdDashboardPagerAdapter(fragment: HouseholdDashboardFragment) =
        SectionsPagerAdapter(fragment.requireActivity(), fragment.childFragmentManager)

    @Provides
    @FragmentScope
    fun provideProfilePictureAdapter() =
        ProfilePictureAdapter(MyUserManager.usersList?.value ?: mutableListOf(), null)

    @Provides
    @FragmentScope
    fun provideHouseholdHomeState(): IHouseholdDashboard.State = HouseholdDashboardState()
}
