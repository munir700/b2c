package co.yap.modules.dashboard.viewmodels

import android.app.Application
import co.yap.modules.dashboard.interfaces.IYapMore
import co.yap.modules.dashboard.states.YapMoreState
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent

class YapMoreViewModel(application: Application) : BaseViewModel<IYapMore.State>(application),
    IYapMore.ViewModel {

    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val state: YapMoreState = YapMoreState()

}