package co.yap.modules.subaccounts.paysalary.profile

import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import co.yap.modules.subaccounts.account.card.SubAccountCardFragment
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

    @Provides
    fun provideHHPaySalaryAdapter(fragment: HHSalaryProfileFragment) =
        HHSalaryProfileTransfersAdapter(
            ArrayList(),
            null
        )
}