package co.yap.modules.dashboard.store.young.paymentselection

import co.yap.yapcore.dagger.di.InjectionViewModelProvider
import co.yap.yapcore.dagger.di.module.fragment.BaseFragmentModule
import co.yap.yapcore.dagger.di.qualifiers.FragmentScope
import co.yap.yapcore.dagger.di.qualifiers.ViewModelInjection
import dagger.Module
import dagger.Provides

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