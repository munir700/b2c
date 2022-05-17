package co.yap.modules.subaccounts.paysalary.future.edit

import co.yap.modules.subaccounts.paysalary.future.FuturePaymentState
import co.yap.modules.subaccounts.paysalary.future.FuturePaymentVM
import co.yap.modules.subaccounts.paysalary.future.IFuturePayment
import co.yap.yapcore.dagger.di.InjectionViewModelProvider
import co.yap.yapcore.dagger.di.module.fragment.BaseFragmentModule
import co.yap.yapcore.dagger.di.qualifiers.FragmentScope
import co.yap.yapcore.dagger.di.qualifiers.ViewModelInjection
import dagger.Module
import dagger.Provides

@Module
class EditFuturePaymentModule {/*: BaseFragmentModule<EditFuturePaymentFragment>() {
    @Provides
    @ViewModelInjection
    fun provideEditFuturePaymentVM(
        fragment: EditFuturePaymentFragment,
        viewModelProvider: InjectionViewModelProvider<FuturePaymentVM>
    ): FuturePaymentVM {
        val vm = viewModelProvider.get(fragment, FuturePaymentVM::class)
        vm.fragmentManager = fragment.childFragmentManager
        return vm
    }

    @Provides
    @FragmentScope
    fun provideEditFuturePaymentState(): IFuturePayment.State = FuturePaymentState()*/
}