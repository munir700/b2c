package co.yap.modules.dashboard.store.young.confirmation

import co.yap.yapcore.dagger.di.InjectionViewModelProvider
import co.yap.yapcore.dagger.di.module.fragment.BaseFragmentModule
import co.yap.yapcore.dagger.di.qualifiers.FragmentScope
import co.yap.yapcore.dagger.di.qualifiers.ViewModelInjection
import dagger.Module
import dagger.Provides

@Module
class YoungPaymentConfirmationModule {}/*: BaseFragmentModule<YoungPaymentConfirmationFragment>() {
    @Provides
    @ViewModelInjection
    fun provideConfirmationSuccessVM(
        fragment: YoungPaymentConfirmationFragment,
        viewModelProvider: InjectionViewModelProvider<YoungPaymentConfirmationVM>
    ): YoungPaymentConfirmationVM = viewModelProvider.get(fragment, YoungPaymentConfirmationVM::class)

    @Provides
    @FragmentScope
    fun provideConfirmationSuccessState(): IYoungPaymentConfirmation.State =
        YoungPaymentConfirmationState()
}*/