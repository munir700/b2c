package co.yap.modules.di.module.fragment

import co.yap.modules.dashboard.store.household.contact.HHAddUserContactFragment
import co.yap.modules.dashboard.store.household.contact.HHAddUserContactModule
import co.yap.modules.dashboard.store.household.landing.HouseHoldLandingFragment
import co.yap.modules.dashboard.store.household.landing.HouseHoldLandingModule
import co.yap.modules.dashboard.store.household.success.HHAddUserSuccessFragment
import co.yap.modules.dashboard.store.household.success.HHAddUserSuccessModule
import co.yap.modules.dashboard.store.household.userinfo.HHAddUserNameFragment
import co.yap.modules.dashboard.store.household.userinfo.HHAddUserNameModule
import co.yap.modules.dashboard.store.yapstore.YapStoreFragment
import co.yap.modules.dashboard.store.yapstore.YapStoreModule
import co.yap.modules.dashboard.store.young.benefits.YoungBenefitsFragment
import co.yap.modules.dashboard.store.young.benefits.YoungBenefitsModule
import co.yap.modules.dashboard.store.young.card.YoungCardEditDetailsFragment
import co.yap.modules.dashboard.store.young.card.YoungCardEditDetailsModule
import co.yap.modules.dashboard.store.young.cardsuccess.YoungCardSuccessFragment
import co.yap.modules.dashboard.store.young.cardsuccess.YoungCardSuccessModule
import co.yap.modules.dashboard.store.young.confirmation.YoungPaymentConfirmationFragment
import co.yap.modules.dashboard.store.young.confirmation.YoungPaymentConfirmationModule
import co.yap.modules.dashboard.store.young.confirmrelationship.YoungConfirmRelationshipFragment
import co.yap.modules.dashboard.store.young.confirmrelationship.YoungConfirmRelationshipModule
import co.yap.modules.dashboard.store.young.contact.YoungContactDetailsFragment
import co.yap.modules.dashboard.store.young.contact.YoungContactDetailsModule
import co.yap.modules.dashboard.store.young.kyc.YoungChildKycHomeFragment
import co.yap.modules.dashboard.store.young.kyc.YoungChildKycHomeModule
import co.yap.modules.dashboard.store.young.landing.YoungLandingFragment
import co.yap.modules.dashboard.store.young.landing.YoungLandingModule
import co.yap.modules.dashboard.store.young.paymentselection.YoungPaymentSelectionFragment
import co.yap.modules.dashboard.store.young.paymentselection.YoungPaymentSelectionModule
import co.yap.modules.dashboard.store.young.pincode.YoungCreatePinCodeFragment
import co.yap.modules.dashboard.store.young.pincode.YoungCreatePinCodeModule
import co.yap.modules.dashboard.store.young.sendmoney.YoungSendMoneyFragment
import co.yap.modules.dashboard.store.young.sendmoney.YoungSendMoneyModule
import co.yap.modules.dashboard.store.young.sendmoney.success.YoungSendMoneySuccessFragment
import co.yap.modules.dashboard.store.young.sendmoney.success.YoungSendMoneySuccessModule
import co.yap.modules.dashboard.store.young.subaccounts.YoungSubAccountModule
import co.yap.modules.dashboard.store.young.subaccounts.YoungSubAccountsFragment
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
import co.yap.modules.subaccounts.paysalary.future.edit.EditFuturePaymentFragment
import co.yap.modules.subaccounts.paysalary.future.edit.EditFuturePaymentModule
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
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reactivex.Single

@Module
@InstallIn(SingletonComponent::class)
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

    @ContributesAndroidInjector(modules = [EditFuturePaymentModule::class])
    @FragmentScope
    abstract fun editFuturePaymentFragmentInjector(): EditFuturePaymentFragment

    @ContributesAndroidInjector(modules = [HouseHoldLandingModule::class])
    @FragmentScope
    abstract fun houseHoldLandingFragmentInjector(): HouseHoldLandingFragment

    @ContributesAndroidInjector(modules = [HHAddUserNameModule::class])
    @FragmentScope
    abstract fun hhAddUserNameFragmentInjector(): HHAddUserNameFragment

    @ContributesAndroidInjector(modules = [HHAddUserSuccessModule::class])
    @FragmentScope
    abstract fun hHAddUserSuccessFragmentInjector(): HHAddUserSuccessFragment

    @ContributesAndroidInjector(modules = [HHAddUserContactModule::class])
    @FragmentScope
    abstract fun hHAddUserContactFragmentInjector(): HHAddUserContactFragment

    @ContributesAndroidInjector(modules = [YapStoreModule::class])
    @FragmentScope
    abstract fun yapStoreFragmentInjector(): YapStoreFragment

    @ContributesAndroidInjector(modules = [YoungLandingModule::class])
    @FragmentScope
    abstract fun youngLandingFragmentInjector(): YoungLandingFragment

    @ContributesAndroidInjector(modules = [YoungPaymentSelectionModule::class])
    @FragmentScope
    abstract fun youngPaymentSelectionFragmentInjector(): YoungPaymentSelectionFragment

    @ContributesAndroidInjector(modules = [YoungBenefitsModule::class])
    @FragmentScope
    abstract fun youngBenefitsFragmentInjector(): YoungBenefitsFragment

    @ContributesAndroidInjector(modules = [YoungPaymentConfirmationModule::class])
    @FragmentScope
    abstract fun youngConfirmationSuccessInjector(): YoungPaymentConfirmationFragment

    @ContributesAndroidInjector(modules = [YoungContactDetailsModule::class])
    @FragmentScope
    abstract fun youngContactDetailsInjector(): YoungContactDetailsFragment

    @ContributesAndroidInjector(modules = [YoungConfirmRelationshipModule::class])
    @FragmentScope
    abstract fun youngConfirmRelationshipInjector(): YoungConfirmRelationshipFragment

    @ContributesAndroidInjector(modules = [YoungCardEditDetailsModule::class])
    @FragmentScope
    abstract fun youngCardEditDetailsInjector(): YoungCardEditDetailsFragment

    @ContributesAndroidInjector(modules = [YoungCardSuccessModule::class])
    @FragmentScope
    abstract fun youngCardSuccessInjector(): YoungCardSuccessFragment

    @ContributesAndroidInjector(modules = [YoungChildKycHomeModule::class])
    @FragmentScope
    abstract fun youngChildKycHomeFragmentInjector(): YoungChildKycHomeFragment

    @ContributesAndroidInjector(modules = [YoungSendMoneyModule::class])
    @FragmentScope
    abstract fun youngSendMoneyInjector(): YoungSendMoneyFragment

    @ContributesAndroidInjector(modules = [YoungSendMoneySuccessModule::class])
    @FragmentScope
    abstract fun youngSendMoneySuccessFragmentInjector(): YoungSendMoneySuccessFragment

    @ContributesAndroidInjector(modules = [YoungSubAccountModule::class])
    @FragmentScope
    abstract fun youngSubAccountFragmentInjector(): YoungSubAccountsFragment

    @ContributesAndroidInjector(modules = [YoungCreatePinCodeModule::class])
    @FragmentScope
    abstract fun youngCreatePinCodeFragmentInjector(): YoungCreatePinCodeFragment
}
