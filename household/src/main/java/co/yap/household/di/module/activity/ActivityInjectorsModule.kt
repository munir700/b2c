package co.yap.household.di.module.activity

import co.yap.household.dashboard2.main.HouseHoldDashboardModule
import co.yap.household.dashboard2.main.HouseholdDashboardActivity
import co.yap.household.onboard.onboarding.kycsuccess.KycSuccessFragment
import co.yap.household.onboard.onboarding.kycsuccess.KycSuccessModule
import co.yap.yapcore.dagger.di.qualifiers.ActivityScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityInjectorsModule {
    @ActivityScope
    @ContributesAndroidInjector(modules = [HouseHoldDashboardModule::class])
    abstract fun HouseholdDashboardActivityInjector(): HouseholdDashboardActivity


}