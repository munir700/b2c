package co.yap.modules.dashboard.store.household.userinfo

import co.yap.yapcore.dagger.di.InjectionViewModelProvider
import co.yap.yapcore.dagger.di.module.fragment.BaseFragmentModule
import co.yap.yapcore.dagger.di.qualifiers.FragmentScope
import co.yap.yapcore.dagger.di.qualifiers.ViewModelInjection
import dagger.Module
import dagger.Provides

@Module
class HHAddUserNameModule : BaseFragmentModule<HHAddUserNameFragment>() {
/*    @Provides
    @ViewModelInjection
    @FragmentScope
    fun provideAddHHGetUserNameVM(
        fragment: HHAddUserNameFragment,
        viewModelProvider: InjectionViewModelProvider<HHAddUserNameVM>
    ): HHAddUserNameVM = viewModelProvider.get(fragment, HHAddUserNameVM::class)

    @Provides
    @FragmentScope
    fun provideHHAddUserNameState(): IHHAddUserName.State = HHAddUserNameState()*/
}
