package co.yap.household.setpin.setnewpin

import dagger.Module

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