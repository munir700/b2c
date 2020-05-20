package co.yap.household.di.module.fragment

import co.yap.household.dashboard.cards.MyCardFragment
import co.yap.household.dashboard.cards.MyCardModule
import co.yap.household.dashboard.home.HouseHoldHomeModule
import co.yap.household.dashboard.home.HouseholdHomeFragment
import co.yap.household.dashboard.main.HouseHoldDashboardModule
import co.yap.household.dashboard.main.HouseholdDashboardFragment
import co.yap.household.onboard.onboarding.kycsuccess.KycSuccessFragment
import co.yap.household.onboard.onboarding.kycsuccess.KycSuccessModule
import co.yap.household.setpin.setnewpin.HHSetPinFragment
import co.yap.household.setpin.setnewpin.HHSetPinModule
import co.yap.household.setpin.setpinstart.HHSetPinCardReviewFragment
import co.yap.household.setpin.setpinstart.HHSetPinCardReviewModule
import co.yap.household.setpin.setpinsuccess.HHSetPinSuccessFragment
import co.yap.household.setpin.setpinsuccess.HHSetPinSuccessModule
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

    @FragmentScope
    @ContributesAndroidInjector(modules = [MyCardModule::class])
    abstract fun MyCardFragmentInjector(): MyCardFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [HouseHoldDashboardModule::class])
    abstract fun HouseholdDashboardFragmentInjector(): HouseholdDashboardFragment

    @ContributesAndroidInjector(modules = [HHSetPinCardReviewModule::class])
    @FragmentScope
    abstract fun hhSetPinCardReviewFragmentInjector(): HHSetPinCardReviewFragment

    @ContributesAndroidInjector(modules = [HHSetPinSuccessModule::class])
    @FragmentScope
    abstract fun hhSetPinSuccessFragmentInjector(): HHSetPinSuccessFragment

    @ContributesAndroidInjector(modules = [HHSetPinModule::class])
    @FragmentScope
    abstract fun hhSetPinFragmentInjector(): HHSetPinFragment

}
