package co.yap.modules.dashboard.store.young.kyc

import dagger.Module

@Module
class YoungChildKycHomeModule {}/*: BaseFragmentModule<YoungChildKycHomeFragment>() {

    @Provides
    @ViewModelInjection
    fun provideYoungChildKycHomeVM(
        fragment: YoungChildKycHomeFragment,
        viewModelProvider: InjectionViewModelProvider<YoungChildKycHomeVM>
    ) = viewModelProvider.get(fragment, YoungChildKycHomeVM::class)

    @Provides
    @FragmentScope
    fun provideYoungChildKycState(): IYoungChildKycHome.State = YoungChildKycHomeState()
}*/