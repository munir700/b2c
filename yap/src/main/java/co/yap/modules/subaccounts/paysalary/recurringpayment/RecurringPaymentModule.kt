package co.yap.modules.subaccounts.paysalary.recurringpayment

import co.yap.yapcore.dagger.di.InjectionViewModelProvider
import co.yap.yapcore.dagger.di.module.fragment.BaseFragmentModule
import co.yap.yapcore.dagger.di.qualifiers.FragmentScope
import co.yap.yapcore.dagger.di.qualifiers.ViewModelInjection
import dagger.Module
import dagger.Provides

@Module
class RecurringPaymentModule : BaseFragmentModule<RecurringPaymentFragment>() {

    @Provides
    @ViewModelInjection
    fun provideSubAccountDashBoardVM(
        fragment: RecurringPaymentFragment,
        viewModelProvider: InjectionViewModelProvider<RecurringPaymentVM>
    ): RecurringPaymentVM = viewModelProvider.get(fragment, RecurringPaymentVM::class)

    @Provides
    @FragmentScope
    fun provideSubAccountDashBoardState(): IRecurringPayment.State = RecurringPaymentState()

}