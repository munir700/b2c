package co.yap.modules.dashboard.store.young.landing

import co.yap.modules.dashboard.store.household.landing.HouseHoldLandingFragment
import co.yap.yapcore.dagger.di.InjectionViewModelProvider
import co.yap.yapcore.dagger.di.module.fragment.BaseFragmentModule
import co.yap.yapcore.dagger.di.qualifiers.FragmentScope
import co.yap.yapcore.dagger.di.qualifiers.ViewModelInjection
import dagger.Module
import dagger.Provides

@Module
class YoungLandingModule : BaseFragmentModule<YoungLandingFragment>() {

    @Provides
    @ViewModelInjection
    fun provideHouseHoldLandingVM(
        fragment: HouseHoldLandingFragment,
        viewModelProvider: InjectionViewModelProvider<YoungLandingVM>
    ): YoungLandingVM = viewModelProvider.get(fragment, YoungLandingVM::class)

    @Provides
    @FragmentScope
    fun provideHouseholdHomeState(): IYoungLanding.State = YoungLandingState()
}
