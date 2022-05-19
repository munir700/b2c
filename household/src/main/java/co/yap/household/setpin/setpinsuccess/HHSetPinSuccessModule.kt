package co.yap.household.setpin.setpinsuccess

import co.yap.yapcore.dagger.di.InjectionViewModelProvider
import co.yap.yapcore.dagger.di.module.fragment.BaseFragmentModule
import co.yap.yapcore.dagger.di.qualifiers.FragmentScope
import co.yap.yapcore.dagger.di.qualifiers.ViewModelInjection
import dagger.Module
import dagger.Provides

@Module
class HHSetPinSuccessModule/* : BaseFragmentModule<HHSetPinSuccessFragment>() {

    @Provides
    @ViewModelInjection
    fun provideHHSetPinSuccessVM(
        fragment: HHSetPinSuccessFragment,
        viewModelProvider: InjectionViewModelProvider<HHSetPinSuccessVM>
    ): HHSetPinSuccessVM = viewModelProvider.get(fragment, HHSetPinSuccessVM::class)

    @Provides
    @FragmentScope
    fun provideHHSetPinSuccessState(): IHHSetPinSuccess.State =
        HHSetPinSuccessState()


}*/