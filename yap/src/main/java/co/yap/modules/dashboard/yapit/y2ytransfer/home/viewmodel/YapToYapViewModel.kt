package co.yap.modules.dashboard.yapit.y2ytransfer.home.viewmodel

import android.app.Application
import co.yap.modules.dashboard.yapit.y2ytransfer.home.interfaces.IYapToYap
import co.yap.modules.dashboard.yapit.y2ytransfer.home.states.YapToYapState
import co.yap.modules.dashboard.yapit.y2ytransfer.viewmodels.Y2YBaseViewModel
import co.yap.yapcore.SingleClickEvent

class YapToYapViewModel(application: Application) : Y2YBaseViewModel<IYapToYap.State>(application),
    IYapToYap.ViewModel {

    override val state: IYapToYap.State = YapToYapState()
    override val clickEvent: SingleClickEvent = SingleClickEvent()

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

    override fun onResume() {
        super.onResume()
        toggleToolBarVisibility(true)
        setToolBarTitle("YAP to YAP")
    }
}