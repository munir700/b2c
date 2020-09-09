package co.yap.modules.subaccounts.confirmation.confirmationsuccess

import co.yap.yapcore.dagger.di.InjectionViewModelProvider
import co.yap.yapcore.dagger.di.module.fragment.BaseFragmentModule
import co.yap.yapcore.dagger.di.qualifiers.FragmentScope
import co.yap.yapcore.dagger.di.qualifiers.ViewModelInjection
import dagger.Module
import dagger.Provides

@Module
class ConfirmationSuccessModule : BaseFragmentModule<ConfirmationSuccessFragment>() {
    @Provides
    @ViewModelInjection
    fun provideConfirmationSuccessVM(
        fragment: ConfirmationSuccessFragment,
        viewModelProvider: InjectionViewModelProvider<ConfirmationSuccessVM>
    ): ConfirmationSuccessVM = viewModelProvider.get(fragment, ConfirmationSuccessVM::class)

    @Provides
    @FragmentScope
    fun provideConfirmationSuccessState(): IConfirmationSuccess.State = ConfirmationSuccessState()
}