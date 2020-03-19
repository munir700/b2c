package co.yap.household.dashboard2.main

import co.yap.yapcore.adpters.SectionsPagerAdapter
import co.yap.yapcore.dagger.di.InjectionViewModelProvider
import co.yap.yapcore.dagger.di.module.activity.BaseActivityModule
import co.yap.yapcore.dagger.di.qualifiers.ActivityScope
import co.yap.yapcore.dagger.di.qualifiers.ViewModelInjection
import dagger.Module
import dagger.Provides

@Module
class HouseHoldDashboardModule : BaseActivityModule<HouseholdDashboardActivity>() {

    @Provides
    @ViewModelInjection
    fun provideDashBoardVM(
        activity: HouseholdDashboardActivity,
        viewModelProvider: InjectionViewModelProvider<HouseHoldDashBoardVM>
    ) = viewModelProvider.get(activity, HouseHoldDashBoardVM::class)

    @Provides
    fun provideHouseholdDashboardPagerAdapter(activity: HouseholdDashboardActivity): SectionsPagerAdapter {
        return SectionsPagerAdapter(activity, activity.supportFragmentManager)
    }
    @Provides
    @ActivityScope
    fun provideHouseholdHomeState():IHouseholdDashboard.State = HouseholdDashboardState()
}