package co.yap.household.setpin.setpinstart

import co.yap.yapcore.dagger.di.InjectionViewModelProvider
import co.yap.yapcore.dagger.di.module.fragment.BaseFragmentModule
import co.yap.yapcore.dagger.di.qualifiers.FragmentScope
import co.yap.yapcore.dagger.di.qualifiers.ViewModelInjection
import dagger.Module
import dagger.Provides

@Module
class HHSetPinCardReviewModule {}/*: BaseFragmentModule<HHSetPinCardReviewFragment>() {

    @Provides
    @ViewModelInjection
    fun provideHHSetPinCardReviewVM(
        fragment: HHSetPinCardReviewFragment,
        viewModelProvider: InjectionViewModelProvider<HHSetPinCardReviewVM>
    ): HHSetPinCardReviewVM = viewModelProvider.get(fragment, HHSetPinCardReviewVM::class)

    @Provides
    @FragmentScope
    fun provideHHSetPinCardReviewState(): IHHSetPinCardReview.State =
        HHSetPinCardReviewState()


}*/