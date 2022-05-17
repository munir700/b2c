package co.yap.modules.subaccounts.paysalary.entersalaryamount

import co.yap.yapcore.dagger.di.InjectionViewModelProvider
import co.yap.yapcore.dagger.di.module.fragment.BaseFragmentModule
import co.yap.yapcore.dagger.di.qualifiers.FragmentScope
import co.yap.yapcore.dagger.di.qualifiers.ViewModelInjection
import dagger.Module
import dagger.Provides

@Module
class EnterSalaryAmountModule {}/*: BaseFragmentModule<EnterSalaryAmountFragment>() {
    @Provides
    @ViewModelInjection
    fun provideSubAccountDashBoardVM(
        fragment: EnterSalaryAmountFragment,
        viewModelProvider: InjectionViewModelProvider<EnterSalaryAmountVM>
    ): EnterSalaryAmountVM = viewModelProvider.get(fragment, EnterSalaryAmountVM::class)

    @Provides
    @FragmentScope
    fun provideSubAccountDashBoardState(): IEnterSalaryAmount.State = EnterSalaryAmountState()
}*/
