package co.yap.household.onboarding.cardselection

import co.yap.networking.customers.responsedtos.HouseHoldCardsDesign
import co.yap.yapcore.dagger.di.InjectionViewModelProvider
import co.yap.yapcore.dagger.di.module.fragment.BaseFragmentModule
import co.yap.yapcore.dagger.di.qualifiers.FragmentScope
import co.yap.yapcore.dagger.di.qualifiers.ViewModelInjection
import dagger.Module
import dagger.Provides

@Module
class HHOnBoardingCardSelectionModule : BaseFragmentModule<HHOnBoardingCardSelectionFragment>() {

    @Provides
    @ViewModelInjection
    fun provideHHOnBoardingCardSelectionVM(
        fragment: HHOnBoardingCardSelectionFragment,
        viewModelProvider: InjectionViewModelProvider<HHOnBoardingCardSelectionVM>
    ): HHOnBoardingCardSelectionVM =
        viewModelProvider.get(fragment, HHOnBoardingCardSelectionVM::class)

    @Provides
    @FragmentScope
    fun provideHHOnBoardingCardSelectionState(): IHHOnBoardingCardSelection.State =
        HHOnBoardingCardSelectionState()

    @Provides
    fun provideCardSelectionAdapter() =
        CardSelectionAdapter(
            mutableListOf(),
            null
        )
}
