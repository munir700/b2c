package co.yap.modules.kyc.cardontheway

import android.app.Application
import co.yap.modules.kyc.viewmodels.KYCChildViewModel

class CardOnTheWayViewModel(application: Application) :
    KYCChildViewModel<ICardOnTheWay.State>(application),
    ICardOnTheWay.ViewModel {
    override val state: ICardOnTheWay.State = CardOnTheWayState()
}