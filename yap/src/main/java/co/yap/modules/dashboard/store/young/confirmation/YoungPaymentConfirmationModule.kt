package co.yap.modules.dashboard.store.young.confirmation

import dagger.Module

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