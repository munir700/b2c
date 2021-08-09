package co.yap.modules.kyc.viewmodels

import android.app.Application
import co.yap.modules.kyc.interfaces.IEditCardName
import co.yap.modules.kyc.states.EditCardNameState
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.yapcore.SingleClickEvent

class EditCardNameViewModel(application: Application) :
    KYCChildViewModel<IEditCardName.State>(application),
    IEditCardName.ViewModel, IRepositoryHolder<CustomersRepository> {
    override val state: IEditCardName.State = EditCardNameState()
    override val repository: CustomersRepository get() = CustomersRepository
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override fun handleOnPressView(id: Int) {
        clickEvent.setValue(id)
    }
}