package co.yap.household.di.module.fragment

import co.yap.household.dashboard.cards.MyCardFragment
import co.yap.household.dashboard.cards.MyCardModule
import co.yap.household.dashboard.expense.HouseHoldExpenseFragment
import co.yap.household.dashboard.expense.HouseHoldExpenseModule
import co.yap.household.dashboard.home.HouseHoldHomeModule
import co.yap.household.dashboard.home.HouseholdHomeFragment
import co.yap.household.dashboard.main.HouseHoldDashboardModule
import co.yap.household.dashboard.main.HouseholdDashboardFragment
import co.yap.household.dashboard.more.HouseHoldMoreFragment
import co.yap.household.dashboard.more.HouseHoldMoreModule
import co.yap.household.onboard.onboarding.kycsuccess.KycSuccessFragment
import co.yap.household.onboard.onboarding.kycsuccess.KycSuccessModule
import co.yap.household.onboarding.cardselection.HHOnBoardingCardSelectionFragment
import co.yap.household.onboarding.cardselection.HHOnBoardingCardSelectionModule
import co.yap.household.onboarding.invalideid.HHOnBoardingInvalidEidFragment
import co.yap.household.onboarding.invalideid.HHOnBoardingInvalidEidModule
import co.yap.household.onboarding.onboardemail.HHOnBoardingEmailFragment
import co.yap.household.onboarding.onboardemail.HHOnBoardingEmailModule
import co.yap.household.onboarding.onboardmobile.HHOnBoardingMobileFragment
import co.yap.household.onboarding.onboardmobile.HHOnBoardingMobileModule
import co.yap.household.onboarding.onboardsuccess.HHOnBoardingSuccessFragment
import co.yap.household.onboarding.onboardsuccess.HHOnBoardingSuccessModule
import co.yap.household.setpin.setnewpin.HHSetPinFragment
import co.yap.household.setpin.setnewpin.HHSetPinModule
import co.yap.household.setpin.setpinstart.HHSetPinCardReviewFragment
import co.yap.household.setpin.setpinstart.HHSetPinCardReviewModule
import co.yap.household.setpin.setpinsuccess.HHSetPinSuccessFragment
import co.yap.household.setpin.setpinsuccess.HHSetPinSuccessModule
import co.yap.modules.dashboard.store.household.paymentconfirmation.HouseHoldConfirmPaymentFragment
import co.yap.modules.dashboard.store.household.paymentconfirmation.HouseHoldConfirmPaymentModule
import co.yap.modules.dashboard.store.household.subscriptionselection.SubscriptionSelectionFragment
import co.yap.modules.dashboard.store.household.subscriptionselection.SubscriptionSelectionModule
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

    @ContributesAndroidInjector(modules = [HouseHoldExpenseModule::class])
    @FragmentScope
    abstract fun HouseHoldExpenseFragmentInjector(): HouseHoldExpenseFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [SubscriptionSelectionModule::class])
    abstract fun SubscriptionSelectionFragmentInjector(): SubscriptionSelectionFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [HouseHoldConfirmPaymentModule::class])
    abstract fun houseHoldConfirmPaymentFragmentInjector(): HouseHoldConfirmPaymentFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [HouseHoldMoreModule::class])
    abstract fun houseHoldMoreFragmentInjector(): HouseHoldMoreFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [HHOnBoardingSuccessModule::class])
    abstract fun hHOnBoardingSuccessFragmentInjector(): HHOnBoardingSuccessFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [HHOnBoardingMobileModule::class])
    abstract fun hHOnBoardingMobileFragmentInjector(): HHOnBoardingMobileFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [HHOnBoardingEmailModule::class])
    abstract fun hHOnBoardingEmailFragmentInjector(): HHOnBoardingEmailFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [HHOnBoardingCardSelectionModule::class])
    abstract fun hHOnBoardingCardSelectionFragmentInjector(): HHOnBoardingCardSelectionFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [HHOnBoardingInvalidEidModule::class])
    abstract fun hHOnBoardingInvalidEidFragmentInjector(): HHOnBoardingInvalidEidFragment
}
