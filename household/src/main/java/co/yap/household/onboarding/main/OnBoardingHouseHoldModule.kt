package co.yap.household.onboarding.main

import co.yap.yapcore.dagger.di.InjectionViewModelProvider
import co.yap.yapcore.dagger.di.module.activity.BaseActivityModule
import co.yap.yapcore.dagger.di.qualifiers.ActivityScope
import co.yap.yapcore.dagger.di.qualifiers.ViewModelInjection
import dagger.Module
import dagger.Provides

@Module
class OnBoardingHouseHoldModule{ /*: BaseActivityModule<OnBoardingHouseHoldActivity>() {
    @Provides
    @ViewModelInjection
    fun provideOnBoardingHouseHoldVM(
        activity: OnBoardingHouseHoldActivity,
        viewModelProvider: InjectionViewModelProvider<OnBoardingHouseHoldVM>
    ) = viewModelProvider.get(activity, OnBoardingHouseHoldVM::class)

    @Provides
    @ActivityScope
    fun provideOnBoardingHouseHoldState(): IOnBoardingHouseHold.State = OnBoardingHouseHoldState()

    @Provides
    @ActivityScope
    fun provideOnBoardingHouseHoldActivityState(): OnBoardingHouseHoldState = OnBoardingHouseHoldState()*/
}