package co.yap.modules.dashboard.store.young.contact

import co.yap.yapcore.dagger.di.InjectionViewModelProvider
import co.yap.yapcore.dagger.di.module.fragment.BaseFragmentModule
import co.yap.yapcore.dagger.di.qualifiers.FragmentScope
import co.yap.yapcore.dagger.di.qualifiers.ViewModelInjection
import dagger.Module
import dagger.Provides

@Module
class YoungContactDetailsModule{}/* : BaseFragmentModule<YoungContactDetailsFragment>() {
    @Provides
    @ViewModelInjection
    fun provideContactDetailsVM(
        fragment: YoungContactDetailsFragment,
        viewModelProvider: InjectionViewModelProvider<YoungContactDetailsVM>
    ): YoungContactDetailsVM = viewModelProvider.get(fragment, YoungContactDetailsVM::class)

    @Provides
    @FragmentScope
    fun provideContactDetailsState(): IYoungContactDetails.State = YoungContactDetailsState()
}
*/