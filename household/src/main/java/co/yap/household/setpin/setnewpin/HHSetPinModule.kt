package co.yap.household.setpin.setnewpin

import co.yap.yapcore.dagger.di.InjectionViewModelProvider
import co.yap.yapcore.dagger.di.module.fragment.BaseFragmentModule
import co.yap.yapcore.dagger.di.qualifiers.FragmentScope
import co.yap.yapcore.dagger.di.qualifiers.ViewModelInjection
import dagger.Module
import dagger.Provides

@Module
class HHSetPinModule{}/* : BaseFragmentModule<HHSetPinFragment>() {
    @Provides
    @ViewModelInjection
    fun provideSetPinVM(
        fragment: HHSetPinFragment,
        viewModelProvider: InjectionViewModelProvider<HHSetPinVM>
    ): HHSetPinVM = viewModelProvider.get(fragment, HHSetPinVM::class)

    @Provides
    @FragmentScope
    fun provideHHSetPinState(): IHHSetPin.State =
        HHSetPinState()
}*/