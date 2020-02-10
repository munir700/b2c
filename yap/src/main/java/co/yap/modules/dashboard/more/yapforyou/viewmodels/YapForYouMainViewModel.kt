package co.yap.modules.dashboard.more.yapforyou.viewmodels

import android.app.Application
import co.yap.modules.dashboard.more.yapforyou.Achievements
import co.yap.modules.dashboard.more.yapforyou.interfaces.IYapForYouMain
import co.yap.modules.dashboard.more.yapforyou.states.YapForYouMainState
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent

class YapForYouMainViewModel(application: Application) :
    BaseViewModel<IYapForYouMain.State>(application),
    IYapForYouMain.ViewModel {

    override val state: YapForYouMainState = YapForYouMainState()
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override var achievement: Achievements? = null
    override var achievements: MutableList<Achievements> = mutableListOf()

    override fun handlePressButton(id: Int) {
        clickEvent.setValue(id)
    }
}