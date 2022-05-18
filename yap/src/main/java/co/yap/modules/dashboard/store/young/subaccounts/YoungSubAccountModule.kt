package co.yap.modules.dashboard.store.young.subaccounts

import co.yap.yapcore.adapters.SectionsPagerAdapter
import co.yap.yapcore.dagger.di.InjectionViewModelProvider
import co.yap.yapcore.dagger.di.module.fragment.BaseFragmentModule
import co.yap.yapcore.dagger.di.qualifiers.FragmentScope
import co.yap.yapcore.dagger.di.qualifiers.ViewModelInjection
import dagger.Module
import dagger.Provides

@Module
class YoungSubAccountModule {}/*: BaseFragmentModule<YoungSubAccountsFragment>() {

    @Provides
    @ViewModelInjection
    fun provideSubAccountVM(
        fragment: YoungSubAccountsFragment,
        viewModelProvider: InjectionViewModelProvider<YoungSubAccountsVM>
    ): YoungSubAccountsVM = viewModelProvider.get(fragment, YoungSubAccountsVM::class)

    @Provides
    @FragmentScope
    fun provideSubAccountState(): IYoungSubAccounts.State = YoungSubAccountState()

    @Provides
    fun provideSubAccountAdapter(fragment: YoungSubAccountsFragment) =
        SectionsPagerAdapter(fragment.requireActivity(), fragment.childFragmentManager)
}*/