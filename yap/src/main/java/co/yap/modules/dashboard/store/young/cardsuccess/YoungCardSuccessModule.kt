package co.yap.modules.dashboard.store.young.cardsuccess

import co.yap.modules.dashboard.store.young.benefits.adapter.YoungBenefitsAdapter
import co.yap.modules.dashboard.store.young.benefits.adapter.YoungBenefitsModel
import co.yap.yapcore.dagger.di.InjectionViewModelProvider
import co.yap.yapcore.dagger.di.module.fragment.BaseFragmentModule
import co.yap.yapcore.dagger.di.qualifiers.FragmentScope
import co.yap.yapcore.dagger.di.qualifiers.ViewModelInjection
import dagger.Module
import dagger.Provides

@Module
class YoungCardSuccessModule {}/*: BaseFragmentModule<YoungCardSuccessFragment>() {
    @Provides
    @ViewModelInjection
    fun provideCardSuccessVM(
        fragment: YoungCardSuccessFragment,
        viewModelProvider: InjectionViewModelProvider<YoungCardSuccessVM>
    ): YoungCardSuccessVM = viewModelProvider.get(fragment, YoungCardSuccessVM::class)

    @Provides
    @FragmentScope
    fun provideCardSuccessState(): IYoungCardSuccess.State = YoungCardSuccessState()
}*/