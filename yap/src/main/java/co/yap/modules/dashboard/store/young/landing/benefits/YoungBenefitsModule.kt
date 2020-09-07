package co.yap.modules.dashboard.store.young.landing.benefits

import co.yap.modules.dashboard.store.young.landing.YoungLandingFragment
import co.yap.yapcore.dagger.di.InjectionViewModelProvider
import co.yap.yapcore.dagger.di.module.fragment.BaseFragmentModule
import co.yap.yapcore.dagger.di.qualifiers.FragmentScope
import co.yap.yapcore.dagger.di.qualifiers.ViewModelInjection
import dagger.Provides

class YoungBenefitsModule : BaseFragmentModule<YoungLandingFragment>(){
    @Provides
    @ViewModelInjection
    fun provideYoungBenefitsVM(
        fragment: YoungBenefitsFragment,
        viewModelProvider: InjectionViewModelProvider<YoungBenefitsVM>
    ): YoungBenefitsVM = viewModelProvider.get(fragment, YoungBenefitsVM::class)

    @Provides
    @FragmentScope
    fun provideYoungBenefitsState(): IYoungBenefits.State = YoungBenefitsState()
}