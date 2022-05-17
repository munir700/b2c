package co.yap.modules.subaccounts.paysalary.transfer

import co.yap.yapcore.dagger.di.InjectionViewModelProvider
import co.yap.yapcore.dagger.di.module.fragment.BaseFragmentModule
import co.yap.yapcore.dagger.di.qualifiers.ViewModelInjection
import dagger.Module
import dagger.Provides

@Module
class HHIbanSendMoneyModule{} /*: BaseFragmentModule<HHIbanSendMoneyFragment>() {

    @Provides
    @ViewModelInjection
    fun provideHHIbanSendMoneyVM(
        fragment: HHIbanSendMoneyFragment,
        viewModelProvider: InjectionViewModelProvider<HHIbanSendMoneyVM>
    ): HHIbanSendMoneyVM = viewModelProvider.get(fragment, HHIbanSendMoneyVM::class)

    @Provides
    fun provideHHIbanSendMoneyState(): IHHIbanSendMoney.State = HHIbanSendMoneyState()
}*/