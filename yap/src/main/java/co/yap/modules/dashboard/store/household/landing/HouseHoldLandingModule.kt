package co.yap.modules.dashboard.store.household.landing

import co.yap.yapcore.dagger.di.InjectionViewModelProvider
import co.yap.yapcore.dagger.di.module.fragment.BaseFragmentModule
import co.yap.yapcore.dagger.di.qualifiers.FragmentScope
import co.yap.yapcore.dagger.di.qualifiers.ViewModelInjection
import dagger.Module
import dagger.Provides

@Module
class HouseHoldLandingModule : BaseFragmentModule<HouseHoldLandingFragment>() {

/*    @Provides
    @ViewModelInjection
    fun provideHouseHoldLandingVM(
        fragment: HouseHoldLandingFragment,
        viewModelProvider: InjectionViewModelProvider<HouseHoldLandingVM>
    ): HouseHoldLandingVM = viewModelProvider.get(fragment, HouseHoldLandingVM::class)*/

 /*   @Provides
    @FragmentScope
    fun provideHouseholdHomeState(): IHouseHoldLanding.State = HouseHoldLandingState()*/
}
