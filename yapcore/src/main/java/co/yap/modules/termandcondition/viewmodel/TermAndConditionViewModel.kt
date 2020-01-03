package co.yap.modules.termandcondition.viewmodel

import android.app.Application
import co.yap.modules.termandcondition.interfaces.ITermAndCondition
import co.yap.modules.termandcondition.state.TermAndConditionState
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.yapcore.BaseViewModel

class TermAndConditionViewModel(application: Application) :
    BaseViewModel<ITermAndCondition.State>(application),
    ITermAndCondition.ViewModel,
    IRepositoryHolder<CustomersRepository> {

    override val state: TermAndConditionState = TermAndConditionState()

    override val repository: CustomersRepository = CustomersRepository
}