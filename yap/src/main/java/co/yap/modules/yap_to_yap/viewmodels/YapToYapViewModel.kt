package co.yap.modules.yap_to_yap.viewmodels

import android.app.Application
import co.yap.modules.yap_to_yap.interfaces.IYapToYap
import co.yap.modules.yap_to_yap.states.YapToYapState
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent

class YapToYapViewModel(application: Application) : BaseViewModel<IYapToYap.State>(application),
    IYapToYap.ViewModel {

    override val state: IYapToYap.State  = YapToYapState()
    override val clickEvent: SingleClickEvent = SingleClickEvent()

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }
}