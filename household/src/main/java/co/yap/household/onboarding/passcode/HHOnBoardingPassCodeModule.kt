package co.yap.household.onboarding.passcode

import co.yap.yapcore.dagger.di.InjectionViewModelProvider
import co.yap.yapcore.dagger.di.module.fragment.BaseFragmentModule
import co.yap.yapcore.dagger.di.qualifiers.FragmentScope
import co.yap.yapcore.dagger.di.qualifiers.ViewModelInjection
import dagger.Module
import dagger.Provides

@Module
class HHOnBoardingPassCodeModule/* : BaseFragmentModule<HHOnBoardingPassCodeFragment>() {
    @Provides
    @ViewModelInjection
    fun provideHHOnBoardingWelcomeVM(
        fragment: HHOnBoardingPassCodeFragment,
        viewModelProvider: InjectionViewModelProvider<HHOnBoardingPassCodeVM>
    ): HHOnBoardingPassCodeVM = viewModelProvider.get(fragment, HHOnBoardingPassCodeVM::class)

    @Provides
    @FragmentScope
    fun provideHHOnBoardingSuccessState(): IHHOnBoardingPassCode.State =
        HHOnBoardingPassCodeState()
}*/