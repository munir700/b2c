package co.yap.modules.dashboard.store.young.card

import co.yap.yapcore.dagger.di.InjectionViewModelProvider
import co.yap.yapcore.dagger.di.module.fragment.BaseFragmentModule
import co.yap.yapcore.dagger.di.qualifiers.FragmentScope
import co.yap.yapcore.dagger.di.qualifiers.ViewModelInjection
import dagger.Module
import dagger.Provides

@Module
class YoungCardEditDetailsModule  : BaseFragmentModule<YoungCardEditDetailsFragment>(){
    @Provides
    @ViewModelInjection
    fun provideCardEditDetailsVM(
        fragment: YoungCardEditDetailsFragment,
        viewModelProvider: InjectionViewModelProvider<YoungCardEditDetailsVM>
    ): YoungCardEditDetailsVM = viewModelProvider.get(fragment, YoungCardEditDetailsVM::class)

    @Provides
    @FragmentScope
    fun provideCardEditDetailsState(): IYoungCardEditDetails.State = YoungCardEditDetailsState()

    @Provides
    fun provideCardEditDetailsAdapter() =
        YoungCardEditAdapter(
            mutableListOf(),
            null
        )
}