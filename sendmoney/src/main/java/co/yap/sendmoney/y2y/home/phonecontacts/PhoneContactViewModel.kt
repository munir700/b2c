package co.yap.sendmoney.y2y.home.phonecontacts

import android.app.Application
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.sendmoney.y2y.home.yapcontacts.YapContactsAdaptor
import co.yap.sendmoney.y2y.main.viewmodels.Y2YBaseViewModel
import co.yap.yapcore.SingleClickEvent

class PhoneContactViewModel(application: Application) :
    Y2YBaseViewModel<IPhoneContact.State>(application),
    IPhoneContact.ViewModel, IRepositoryHolder<CustomersRepository> {

    override val repository: CustomersRepository = CustomersRepository
    override val state: IPhoneContact.State = PhoneContactState()
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override var adaptor: YapContactsAdaptor = YapContactsAdaptor(arrayListOf())

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }
}
