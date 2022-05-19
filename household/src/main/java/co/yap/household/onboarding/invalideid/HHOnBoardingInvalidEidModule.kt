package co.yap.household.onboarding.invalideid

import co.yap.yapcore.dagger.di.InjectionViewModelProvider
import co.yap.yapcore.dagger.di.module.fragment.BaseFragmentModule
import co.yap.yapcore.dagger.di.qualifiers.FragmentScope
import co.yap.yapcore.dagger.di.qualifiers.ViewModelInjection
import dagger.Module
import dagger.Provides

@Module
class HHOnBoardingInvalidEidModule {}/*: BaseFragmentModule<HHOnBoardingInvalidEidFragment>() {

    @Provides
    @ViewModelInjection
    fun provideHHOnBoardingInvalidEidVM(
        fragment: HHOnBoardingInvalidEidFragment,
        viewModelProvider: InjectionViewModelProvider<HHOnBoardingInvalidEidVM>
    ): HHOnBoardingInvalidEidVM = viewModelProvider.get(fragment, HHOnBoardingInvalidEidVM::class)

    @Provides
    @FragmentScope
    fun provideHHOnBoardingInvalidEidState(): IHHOnBoardingInvalidEid.State =
        HHOnBoardingInvalidEidState()
}
*/