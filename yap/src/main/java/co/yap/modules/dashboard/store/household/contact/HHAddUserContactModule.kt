package co.yap.modules.dashboard.store.household.contact

import co.yap.yapcore.dagger.di.InjectionViewModelProvider
import co.yap.yapcore.dagger.di.module.fragment.BaseFragmentModule
import co.yap.yapcore.dagger.di.qualifiers.FragmentScope
import co.yap.yapcore.dagger.di.qualifiers.ViewModelInjection
import dagger.Module
import dagger.Provides

@Module
class HHAddUserContactModule /*: BaseFragmentModule<HHAddUserContactFragment>()*/ {

   /* @Provides
    @ViewModelInjection
    @FragmentScope
    fun provideHHAddUserContactVM(
        fragment: HHAddUserContactFragment,
        viewModelProvider: InjectionViewModelProvider<HHAddUserContactVM>
    ): HHAddUserContactVM = viewModelProvider.get(fragment, HHAddUserContactVM::class)

    @Provides
    @FragmentScope
    fun provideHHAddUserNameState(): IHHAddUserContact.State = HHAddUserContactState()*/
}
