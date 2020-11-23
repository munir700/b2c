package co.yap.modules.dashboard.cards.addpaymentcard.spare.virtual

import android.app.Application
import co.yap.modules.dashboard.cards.addpaymentcard.main.viewmodels.AddPaymentChildViewModel
import co.yap.networking.cards.CardsRepository
import co.yap.networking.interfaces.IRepositoryHolder

class AddVirtualCardViewModel(application: Application) :
    AddPaymentChildViewModel<IAddVirtualCard.State>(application), IAddVirtualCard.ViewModel,
    IRepositoryHolder<CardsRepository> {
    override val state: IAddVirtualCard.State
        get() = TODO("Not yet implemented")
    override val repository: CardsRepository
        get() = TODO("Not yet implemented")
}