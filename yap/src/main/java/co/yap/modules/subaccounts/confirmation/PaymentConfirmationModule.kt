package co.yap.modules.subaccounts.confirmation

import co.yap.yapcore.dagger.di.InjectionViewModelProvider
import co.yap.yapcore.dagger.di.module.fragment.BaseFragmentModule
import co.yap.yapcore.dagger.di.qualifiers.FragmentScope
import co.yap.yapcore.dagger.di.qualifiers.ViewModelInjection
import dagger.Module
import dagger.Provides


@Module
class PaymentConfirmationModule{/*:BaseFragmentModule<PaymentConfirmationFragment>() {

    @Provides
    @ViewModelInjection
    fun providePaymentConfirmationVM(
        fragment: PaymentConfirmationFragment,
        viewModelProvider: InjectionViewModelProvider<PaymentConfirmationVM>
    ): PaymentConfirmationVM = viewModelProvider.get(fragment, PaymentConfirmationVM::class)

    @Provides
    @FragmentScope
    fun providePaymentConfirmationState(): IPaymentConfirmation.State = PaymentConfirmationState()*/
}