package co.yap.modules.di.module.fragment


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
import co.yap.modules.subaccounts.paysalary.profile.HHSalaryProfileFragment
import co.yap.modules.subaccounts.paysalary.profile.HHSalaryProfileModule
import co.yap.modules.subaccounts.paysalary.subscription.SubscriptionFragment
import co.yap.modules.subaccounts.paysalary.subscription.SubscriptionModule
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
}