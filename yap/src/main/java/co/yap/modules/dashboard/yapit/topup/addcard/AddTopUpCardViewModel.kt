package co.yap.modules.dashboard.yapit.topup.addcard

import android.app.Application
import co.yap.yapcore.BaseViewModel

class AddTopUpCardViewModel(application: Application) :
    BaseViewModel<IAddTopUpCard.State>(application), IAddTopUpCard.ViewModel {
    override val state: AddTopUpCardState =
        AddTopUpCardState()
}