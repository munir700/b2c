package co.yap.modules.dashboard.store.young.landing

import dagger.Module

@Module
class YoungLandingModule{}/* : BaseFragmentModule<YoungLandingFragment>() {

    @Provides
    @ViewModelInjection
    fun provideYoungLandingVM(
        fragment: YoungLandingFragment,
        viewModelProvider: InjectionViewModelProvider<YoungLandingVM>
    ): YoungLandingVM = viewModelProvider.get(fragment, YoungLandingVM::class)

    @Provides
    @FragmentScope
    fun provideYoungLandingState(): IYoungLanding.State = YoungLandingState()
}*/
