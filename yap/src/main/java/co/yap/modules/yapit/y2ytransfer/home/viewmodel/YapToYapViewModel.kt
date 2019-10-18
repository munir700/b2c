package co.yap.modules.yapit.y2ytransfer.home.viewmodel

import android.app.Application
import co.yap.modules.yapit.y2ytransfer.home.interfaces.IYapToYap
import co.yap.modules.yapit.y2ytransfer.home.states.YapToYapState
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent

class YapToYapViewModel(application: Application) : BaseViewModel<IYapToYap.State>(application),
    IYapToYap.ViewModel {
    override val state: IYapToYap.State  =
        YapToYapState()
    override val clickEvent: SingleClickEvent = SingleClickEvent()

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }
}