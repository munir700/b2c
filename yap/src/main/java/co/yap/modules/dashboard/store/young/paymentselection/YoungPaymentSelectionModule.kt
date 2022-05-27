package co.yap.modules.dashboard.store.young.paymentselection

import dagger.Module

@Module
class YoungPaymentSelectionModule {}/*: BaseFragmentModule<YoungPaymentSelectionFragment>() {
    @Provides
    @ViewModelInjection
    fun provideYoungPaymentSelectionVM(
        fragment: YoungPaymentSelectionFragment,
        viewModelProvider: InjectionViewModelProvider<YoungPaymentSelectionVM>
    ): YoungPaymentSelectionVM = viewModelProvider.get(fragment, YoungPaymentSelectionVM::class)

    @Provides
    @FragmentScope
    fun provideYoungPaymentSelectionState(): IYoungPaymentSelection.State =
        YoungPaymentSelectionState()
}
*/