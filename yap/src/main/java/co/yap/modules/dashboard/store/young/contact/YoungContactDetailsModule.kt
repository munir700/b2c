package co.yap.modules.dashboard.store.young.contact

import dagger.Module

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