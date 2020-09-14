package co.yap.modules.subaccounts.account.dashboard

import co.yap.modules.dashboard.store.young.subaccounts.IYoungSubAccounts
import co.yap.modules.dashboard.store.young.subaccounts.YoungSubAccountState
import co.yap.yapcore.adpters.SectionsPagerAdapter
import co.yap.yapcore.dagger.di.InjectionViewModelProvider
import co.yap.yapcore.dagger.di.module.fragment.BaseFragmentModule
import co.yap.yapcore.dagger.di.qualifiers.FragmentScope
import co.yap.yapcore.dagger.di.qualifiers.ViewModelInjection
import dagger.Module
import dagger.Provides

@Module
class SubAccountDashBoardModule : BaseFragmentModule<SubAccountDashBoardFragment>() {

    @Provides
    @ViewModelInjection
    fun provideSubAccountDashBoardVM(
        fragment: SubAccountDashBoardFragment,
        viewModelProvider: InjectionViewModelProvider<SubAccountDashBoardVM>
    ): SubAccountDashBoardVM = viewModelProvider.get(fragment, SubAccountDashBoardVM::class)

    @Provides
    @FragmentScope
    fun provideSubAccountDashBoardState(): IYoungSubAccounts.State = YoungSubAccountState()

    @Provides
    fun provideSubAccountDashBoardPagerAdapter(
        fragment: SubAccountDashBoardFragment
    ) = SectionsPagerAdapter(fragment.requireActivity(), fragment.childFragmentManager)

}