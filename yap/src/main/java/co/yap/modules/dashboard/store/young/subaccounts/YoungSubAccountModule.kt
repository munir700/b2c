package co.yap.modules.dashboard.store.young.subaccounts

import co.yap.modules.subaccounts.account.card.*
import co.yap.yapcore.dagger.di.InjectionViewModelProvider
import co.yap.yapcore.dagger.di.module.fragment.BaseFragmentModule
import co.yap.yapcore.dagger.di.qualifiers.FragmentScope
import co.yap.yapcore.dagger.di.qualifiers.ViewModelInjection
import dagger.Module
import dagger.Provides

@Module
class YoungSubAccountModule: BaseFragmentModule<YoungSubAccountsFragment>() {

    @Provides
    @ViewModelInjection
    fun provideSubAccountCardVM(
        fragment: YoungSubAccountsFragment,
        viewModelProvider: InjectionViewModelProvider<YoungSubAccountsVM>
    ): YoungSubAccountsVM = viewModelProvider.get(fragment, YoungSubAccountsVM::class)

    @Provides
    @FragmentScope
    fun provideSubAccountCardState(): IYoungSubAccounts.State = YoungSubAccountState()

    @Provides
    fun provideSubAccountCardAdapter(fragment: SubAccountCardFragment) =
        SubAccountAdapter(
            ArrayList(),
            null
        )
}