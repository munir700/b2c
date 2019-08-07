package co.yap.modules.setcardpin.viewmodels

import android.app.Application
import co.yap.networking.cards.CardsRepository
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.yapcore.SingleClickEvent

open class ConfirmCardPinViewModel(application: Application) : SetCardPinViewModel(application),
    IRepositoryHolder<CardsRepository> {

    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val repository: CardsRepository = CardsRepository

    override fun handlePressOnNextButton(id: Int) {
        clickEvent.setValue(id)
    }
}