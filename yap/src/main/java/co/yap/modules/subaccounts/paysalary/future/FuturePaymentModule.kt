package co.yap.modules.subaccounts.paysalary.future

import co.yap.yapcore.dagger.di.InjectionViewModelProvider
import co.yap.yapcore.dagger.di.module.fragment.BaseFragmentModule
import co.yap.yapcore.dagger.di.qualifiers.FragmentScope
import co.yap.yapcore.dagger.di.qualifiers.ViewModelInjection
import dagger.Module
import dagger.Provides

@Module
class FuturePaymentModule {/*: BaseFragmentModule<FuturePaymentFragment>() {

    @Provides
    @ViewModelInjection
    fun provideFuturePaymentVM(
        fragment: FuturePaymentFragment,
        viewModelProvider: InjectionViewModelProvider<FuturePaymentVM>
    ): FuturePaymentVM {
        val vm = viewModelProvider.get(fragment, FuturePaymentVM::class)
        vm.fragmentManager = fragment.childFragmentManager
        return vm
    }

    @Provides
    @FragmentScope
    fun provideFuturePaymentState(): IFuturePayment.State = FuturePaymentState()*/
}