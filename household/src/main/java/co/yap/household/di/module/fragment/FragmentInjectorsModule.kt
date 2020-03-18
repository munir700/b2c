package co.yap.household.di.module.fragment

import co.yap.household.dashboard2.home.HouseHoldHomeModule
import co.yap.household.dashboard2.home.HouseholdHomeFragment
import co.yap.household.onboard.onboarding.kycsuccess.KycSuccessFragment
import co.yap.household.onboard.onboarding.kycsuccess.KycSuccessModule
import co.yap.yapcore.dagger.di.qualifiers.ActivityScope
import co.yap.yapcore.dagger.di.qualifiers.FragmentScope
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class FragmentInjectorsModule {
    @ContributesAndroidInjector(modules = [KycSuccessModule::class])
    abstract fun KycSuccessFragmentInjector(): KycSuccessFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [HouseHoldHomeModule::class])
    abstract fun HouseholdHomeFragmentInjector(): HouseholdHomeFragment

}