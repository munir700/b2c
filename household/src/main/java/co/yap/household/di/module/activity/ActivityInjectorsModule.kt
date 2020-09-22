package co.yap.household.di.module.activity

import co.yap.household.onboarding.main.OnBoardingHouseHoldActivity
import co.yap.household.onboarding.main.OnBoardingHouseHoldModule
import co.yap.yapcore.dagger.base.MvvmNavHostModule
import co.yap.yapcore.dagger.di.qualifiers.ActivityScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityInjectorsModule {
    @ActivityScope
    @ContributesAndroidInjector(modules = [OnBoardingHouseHoldModule::class, MvvmNavHostModule::class])
    abstract fun onBoardingHouseHoldActivityInjector(): OnBoardingHouseHoldActivity

}