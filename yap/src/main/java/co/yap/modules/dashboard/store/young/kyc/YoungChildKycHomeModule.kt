package co.yap.modules.dashboard.store.young.kyc

import co.yap.yapcore.dagger.di.InjectionViewModelProvider
import co.yap.yapcore.dagger.di.module.fragment.BaseFragmentModule
import co.yap.yapcore.dagger.di.qualifiers.FragmentScope
import co.yap.yapcore.dagger.di.qualifiers.ViewModelInjection
import dagger.Module
import dagger.Provides

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