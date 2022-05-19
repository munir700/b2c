package co.yap.household.di.module.activity

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ActivityInjectorsModule {
/*
    @ActivityScope
    @ContributesAndroidInjector(modules = [OnBoardingHouseHoldModule::class, MvvmNavHostModule::class])
    abstract fun onBoardingHouseHoldActivityInjector(): OnBoardingHouseHoldActivity
*/

}