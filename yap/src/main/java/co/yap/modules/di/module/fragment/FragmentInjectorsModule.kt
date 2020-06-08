package co.yap.modules.di.module.fragment

import co.yap.modules.dashboard.store.household.landing.HouseHoldLandingFragment
import co.yap.modules.dashboard.store.household.landing.HouseHoldLandingModule
import co.yap.modules.subaccounts.account.card.SubAccountCardFragment
import co.yap.modules.subaccounts.account.card.SubAccountCardModule
import co.yap.modules.subaccounts.account.dashboard.SubAccountDashBoardFragment
import co.yap.modules.subaccounts.account.dashboard.SubAccountDashBoardModule
import co.yap.modules.subaccounts.confirmation.PaymentConfirmationFragment
import co.yap.modules.subaccounts.confirmation.PaymentConfirmationModule
import co.yap.modules.subaccounts.paysalary.employee.PayHHEmployeeSalaryFragment
import co.yap.modules.subaccounts.paysalary.employee.PayHHEmployeeSalaryModule
import co.yap.modules.subaccounts.paysalary.entersalaryamount.EnterSalaryAmountFragment
import co.yap.modules.subaccounts.paysalary.entersalaryamount.EnterSalaryAmountModule
import co.yap.modules.subaccounts.paysalary.future.FuturePaymentFragment
import co.yap.modules.subaccounts.paysalary.future.FuturePaymentModule
import co.yap.modules.subaccounts.paysalary.profile.HHSalaryProfileFragment
import co.yap.modules.subaccounts.paysalary.profile.HHSalaryProfileModule
import co.yap.modules.subaccounts.paysalary.profile.cardholderprofile.HHProfileFragment
import co.yap.modules.subaccounts.paysalary.profile.cardholderprofile.HHProfileModule
import co.yap.modules.subaccounts.paysalary.recurringpayment.RecurringPaymentFragment
import co.yap.modules.subaccounts.paysalary.recurringpayment.RecurringPaymentModule
import co.yap.modules.subaccounts.paysalary.subscription.SubscriptionFragment
import co.yap.modules.subaccounts.paysalary.subscription.SubscriptionModule
import co.yap.modules.subaccounts.paysalary.transfer.HHIbanSendMoneyFragment
import co.yap.modules.subaccounts.paysalary.transfer.HHIbanSendMoneyModule
import co.yap.modules.subaccounts.paysalary.transfer.confirmation.HHIbanSendMoneyConfirmationFragment
import co.yap.modules.subaccounts.paysalary.transfer.confirmation.HHIbanSendMoneyConfirmationModule
import co.yap.yapcore.dagger.di.qualifiers.FragmentScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentInjectorsModule {
    @ContributesAndroidInjector(modules = [SubAccountDashBoardModule::class])
    @FragmentScope
    abstract fun subAccountDashBoardFragmentInjector(): SubAccountDashBoardFragment

    @ContributesAndroidInjector(modules = [SubAccountCardModule::class])
    @FragmentScope
    abstract fun subAccountCardFragmentInjector(): SubAccountCardFragment

    @ContributesAndroidInjector(modules = [HHSalaryProfileModule::class])
    @FragmentScope
    abstract fun hHSalaryProfileFragmentInjector(): HHSalaryProfileFragment

    @ContributesAndroidInjector(modules = [SubscriptionModule::class])
    @FragmentScope
    abstract fun subscriptionFragmentInjector(): SubscriptionFragment

    @ContributesAndroidInjector(modules = [PaymentConfirmationModule::class])
    @FragmentScope
    abstract fun paymentConfirmationFragmentInjector(): PaymentConfirmationFragment

    @ContributesAndroidInjector(modules = [PayHHEmployeeSalaryModule::class])
    @FragmentScope
    abstract fun payHHEmployeeSalaryFragmentInjector(): PayHHEmployeeSalaryFragment

    @ContributesAndroidInjector(modules = [EnterSalaryAmountModule::class])
    @FragmentScope
    abstract fun enterSalaryAmountFragmentInjector(): EnterSalaryAmountFragment

    @ContributesAndroidInjector(modules = [RecurringPaymentModule::class])
    @FragmentScope
    abstract fun recurringAmountFragmentInjector(): RecurringPaymentFragment

    @ContributesAndroidInjector(modules = [HHProfileModule::class])
    @FragmentScope
    abstract fun hhProfileFragmentInjector(): HHProfileFragment

    @ContributesAndroidInjector(modules = [HHIbanSendMoneyModule::class])
    abstract fun hHIbanSendMoneyFragmentInjector(): HHIbanSendMoneyFragment

    @ContributesAndroidInjector(modules = [HHIbanSendMoneyConfirmationModule::class])
    abstract fun hHIbanSendMoneyConfirmationFragmentInjector(): HHIbanSendMoneyConfirmationFragment

    @ContributesAndroidInjector(modules = [FuturePaymentModule::class])
    @FragmentScope
    abstract fun futurePaymentFragmentInjector(): FuturePaymentFragment

    @ContributesAndroidInjector(modules = [HouseHoldLandingModule::class])
    @FragmentScope
    abstract fun houseHoldLandingFragmentInjector(): HouseHoldLandingFragment

}