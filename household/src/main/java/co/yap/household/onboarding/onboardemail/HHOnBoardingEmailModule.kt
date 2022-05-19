package co.yap.household.onboarding.onboardemail

import co.yap.yapcore.dagger.di.InjectionViewModelProvider
import co.yap.yapcore.dagger.di.module.fragment.BaseFragmentModule
import co.yap.yapcore.dagger.di.qualifiers.FragmentScope
import co.yap.yapcore.dagger.di.qualifiers.ViewModelInjection
import dagger.Module
import dagger.Provides

@Module
class HHOnBoardingEmailModule {}/*: BaseFragmentModule<HHOnBoardingEmailFragment>() {

    @Provides
    @ViewModelInjection
    fun provideHHOnBoardingEmailVM(
        fragment: HHOnBoardingEmailFragment,
        viewModelProvider: InjectionViewModelProvider<HHOnBoardingEmailVM>
    ): HHOnBoardingEmailVM = viewModelProvider.get(fragment, HHOnBoardingEmailVM::class)

    @Provides
    @FragmentScope
    fun provideHHOnBoardingEmailState(): IHHOnBoardingEmail.State =
        HHOnBoardingEmailState()
}*/
