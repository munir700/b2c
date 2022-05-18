package co.yap.modules.dashboard.store.young.landing

import co.yap.yapcore.dagger.di.InjectionViewModelProvider
import co.yap.yapcore.dagger.di.module.fragment.BaseFragmentModule
import co.yap.yapcore.dagger.di.qualifiers.FragmentScope
import co.yap.yapcore.dagger.di.qualifiers.ViewModelInjection
import dagger.Module
import dagger.Provides

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
