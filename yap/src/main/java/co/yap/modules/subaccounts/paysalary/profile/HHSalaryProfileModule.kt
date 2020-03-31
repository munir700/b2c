package co.yap.modules.subaccounts.paysalary.profile

import co.yap.yapcore.dagger.di.InjectionViewModelProvider
import co.yap.yapcore.dagger.di.module.fragment.BaseFragmentModule
import co.yap.yapcore.dagger.di.qualifiers.FragmentScope
import co.yap.yapcore.dagger.di.qualifiers.ViewModelInjection
import dagger.Module
import dagger.Provides


@Module
class HHSalaryProfileModule : BaseFragmentModule<HHSalaryProfileFragment>() {

    @Provides
    @ViewModelInjection
    fun provideHHSalaryProfileVM(
        fragment: HHSalaryProfileFragment,
        viewModelProvider: InjectionViewModelProvider<HHSalaryProfileVM>
    ): HHSalaryProfileVM = viewModelProvider.get(fragment, HHSalaryProfileVM::class)

    @Provides
    @FragmentScope
    fun provideHHSalaryProfileState(): IHHSalaryProfile.State = HHSalaryProfileState()
}