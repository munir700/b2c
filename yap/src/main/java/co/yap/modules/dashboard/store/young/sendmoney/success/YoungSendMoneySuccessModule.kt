package co.yap.modules.dashboard.store.young.sendmoney.success

import co.yap.yapcore.dagger.di.InjectionViewModelProvider
import co.yap.yapcore.dagger.di.module.fragment.BaseFragmentModule
import co.yap.yapcore.dagger.di.qualifiers.FragmentScope
import co.yap.yapcore.dagger.di.qualifiers.ViewModelInjection
import dagger.Module
import dagger.Provides

@Module
class YoungSendMoneySuccessModule{}/* : BaseFragmentModule<YoungSendMoneySuccessFragment>() {

    @Provides
    @ViewModelInjection
    fun provideYoungSendMoneySuccessVM(
        fragment: YoungSendMoneySuccessFragment,
        viewModelProvider: InjectionViewModelProvider<YoungSendMoneySuccessVM>
    ): YoungSendMoneySuccessVM = viewModelProvider.get(fragment, YoungSendMoneySuccessVM::class)

    @Provides
    @FragmentScope
    fun provideYoungSendMoneySuccessState(): IYoungSendMoneySuccess.State =
        YoungSendMoneySuccessState()
}*/