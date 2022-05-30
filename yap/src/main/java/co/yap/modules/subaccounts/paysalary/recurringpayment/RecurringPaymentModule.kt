package co.yap.modules.subaccounts.paysalary.recurringpayment

import dagger.Module

@Module
class RecurringPaymentModule {}/*: BaseFragmentModule<RecurringPaymentFragment>() {

    @Provides
    @ViewModelInjection
    fun provideSubAccountDashBoardVM(
        fragment: RecurringPaymentFragment,
        viewModelProvider: InjectionViewModelProvider<RecurringPaymentVM>
    ): RecurringPaymentVM {
        val vm = viewModelProvider.get(fragment, RecurringPaymentVM::class)
        vm.fragmentManager = fragment.childFragmentManager
        return vm
    }

    @Provides
    @FragmentScope
    fun provideSubAccountDashBoardState(): IRecurringPayment.State = RecurringPaymentState()
}*/