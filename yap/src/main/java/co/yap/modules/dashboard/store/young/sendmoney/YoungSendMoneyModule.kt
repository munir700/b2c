package co.yap.modules.dashboard.store.young.sendmoney

import co.yap.yapcore.dagger.di.InjectionViewModelProvider
import co.yap.yapcore.dagger.di.module.fragment.BaseFragmentModule
import co.yap.yapcore.dagger.di.qualifiers.FragmentScope
import co.yap.yapcore.dagger.di.qualifiers.ViewModelInjection
import dagger.Module
import dagger.Provides

@Module
class YoungSendMoneyModule {}/* : BaseFragmentModule<YoungSendMoneyFragment>() {
    @Provides
    @ViewModelInjection
    fun provideYoungSendMoneyVM(
        fragment: YoungSendMoneyFragment,
        viewModelProvider: InjectionViewModelProvider<YoungSendMoneyVM>
    ): YoungSendMoneyVM = viewModelProvider.get(fragment, YoungSendMoneyVM::class)

    @Provides
    @FragmentScope
    fun provideYoungSendMoneyState(): IYoungSendMoney.State =
        YoungSendMoneyState()
}*/
