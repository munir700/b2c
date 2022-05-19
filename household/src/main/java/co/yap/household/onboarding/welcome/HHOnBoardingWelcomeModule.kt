package co.yap.household.onboarding.welcome

import co.yap.yapcore.dagger.di.InjectionViewModelProvider
import co.yap.yapcore.dagger.di.module.fragment.BaseFragmentModule
import co.yap.yapcore.dagger.di.qualifiers.FragmentScope
import co.yap.yapcore.dagger.di.qualifiers.ViewModelInjection
import dagger.Module
import dagger.Provides

@Module
class HHOnBoardingWelcomeModule {}/*: BaseFragmentModule<HHOnBoardingWelcomeFragment>() {

    @Provides
    @ViewModelInjection
    fun provideHHOnBoardingWelcomeVM(
        fragment: HHOnBoardingWelcomeFragment,
        viewModelProvider: InjectionViewModelProvider<HHOnBoardingWelcomeVM>
    ): HHOnBoardingWelcomeVM = viewModelProvider.get(fragment, HHOnBoardingWelcomeVM::class)

    @Provides
    @FragmentScope
    fun provideHHOnBoardingSuccessState(): IHHOnBoardingWelcome.State =
        HHOnBoardingWelcomeState()
}*/

