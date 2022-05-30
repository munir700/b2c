package co.yap.modules.dashboard.store.young.subaccounts

import dagger.Module

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