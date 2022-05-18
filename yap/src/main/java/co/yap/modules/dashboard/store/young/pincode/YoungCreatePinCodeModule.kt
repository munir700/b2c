package co.yap.modules.dashboard.store.young.pincode

import co.yap.yapcore.dagger.di.InjectionViewModelProvider
import co.yap.yapcore.dagger.di.module.fragment.BaseFragmentModule
import co.yap.yapcore.dagger.di.qualifiers.FragmentScope
import co.yap.yapcore.dagger.di.qualifiers.ViewModelInjection
import dagger.Module
import dagger.Provides

@Module
class YoungCreatePinCodeModule{}/*: BaseFragmentModule<YoungCreatePinCodeFragment>() {
    @Provides
    @ViewModelInjection
    fun provideYoungCreatePinCodeVM(
        fragment: YoungCreatePinCodeFragment,
        viewModelProvider: InjectionViewModelProvider<YoungCreatePinCodeVM>
    ): YoungCreatePinCodeVM = viewModelProvider.get(fragment, YoungCreatePinCodeVM::class)

    @Provides
    @FragmentScope
    fun provideCreatePinCodeState(): IYoungPinCode.State = YoungCreatePinCodeState()
}*/