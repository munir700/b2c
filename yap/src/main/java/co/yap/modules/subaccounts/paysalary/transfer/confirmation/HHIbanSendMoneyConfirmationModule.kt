package co.yap.modules.subaccounts.paysalary.transfer.confirmation

import co.yap.yapcore.dagger.di.InjectionViewModelProvider
import co.yap.yapcore.dagger.di.module.fragment.BaseFragmentModule
import co.yap.yapcore.dagger.di.qualifiers.ViewModelInjection
import dagger.Module
import dagger.Provides

@Module
class HHIbanSendMoneyConfirmationModule{}/*: BaseFragmentModule<HHIbanSendMoneyConfirmationFragment>(){

    @Provides
    @ViewModelInjection
    fun provideHHIbanSendMoneyConfirmationVM(
        fragment: HHIbanSendMoneyConfirmationFragment,
        viewModelProvider: InjectionViewModelProvider<HHIbanSendMoneyConfirmationVM>
    ): HHIbanSendMoneyConfirmationVM = viewModelProvider.get(fragment, HHIbanSendMoneyConfirmationVM::class)

    @Provides
    fun provideHHIbanSendMoneyConfirmationState(): IHHIbanSendMoneyConfirmation.State = HHIbanSendMoneyConfirmationState()
}*/