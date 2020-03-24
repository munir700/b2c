package co.yap.household.di.module.activity

import co.yap.household.dashboard.main.HouseHoldDashboardModule
import co.yap.household.dashboard.main.HouseholdDashboardActivity
import co.yap.yapcore.dagger.di.qualifiers.ActivityScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityInjectorsModule {
    @ActivityScope
    @ContributesAndroidInjector(modules = [HouseHoldDashboardModule::class])
    abstract fun HouseholdDashboardActivityInjector(): HouseholdDashboardActivity


}