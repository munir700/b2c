package co.yap.household.onboarding.onboardmobile

import co.yap.yapcore.dagger.di.InjectionViewModelProvider
import co.yap.yapcore.dagger.di.module.fragment.BaseFragmentModule
import co.yap.yapcore.dagger.di.qualifiers.FragmentScope
import co.yap.yapcore.dagger.di.qualifiers.ViewModelInjection
import dagger.Module
import dagger.Provides

@Module
class HHOnBoardingMobileModule{} /*: BaseFragmentModule<HHOnBoardingMobileFragment>() {

    @Provides
    @ViewModelInjection
    fun provideHHOnboardingMobileVM(
        fragment: HHOnBoardingMobileFragment,
        viewModelProvider: InjectionViewModelProvider<HHOnBoardingMobileVM>
    ): HHOnBoardingMobileVM = viewModelProvider.get(fragment, HHOnBoardingMobileVM::class)

    @Provides
    @FragmentScope
    fun provideHHOnBoardingMobileState(): IHHOnBoardingMobile.State =
        HHOnBoardingMobileState()
}
*/