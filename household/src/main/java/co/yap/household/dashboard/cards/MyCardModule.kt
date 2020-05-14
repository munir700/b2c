package co.yap.household.dashboard.cards

import co.yap.yapcore.dagger.di.InjectionViewModelProvider
import co.yap.yapcore.dagger.di.module.fragment.BaseFragmentModule
import co.yap.yapcore.dagger.di.qualifiers.FragmentScope
import co.yap.yapcore.dagger.di.qualifiers.ViewModelInjection
import dagger.Module
import dagger.Provides

@Module
class MyCardModule : BaseFragmentModule<MyCardFragment>() {
    @Provides
    @ViewModelInjection
    fun provideMyCardVM(
        fragment: MyCardFragment,
        viewModelProvider: InjectionViewModelProvider<MyCardVM>
    ): MyCardVM = viewModelProvider.get(fragment, MyCardVM::class)

    @Provides
    @FragmentScope
    fun provideMyCardState(): IMyCard.State = MyCardState()

    @Provides
    fun provideMyCardTransactionsAdapter() =
        MyCardFragment.Adapter(
            mutableListOf(),
            null
        )
}