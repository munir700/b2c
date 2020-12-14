package co.yap.modules.dashboard.more.yapforyou.viewmodels

import android.app.Application
import co.yap.modules.dashboard.more.yapforyou.interfaces.IYapForYouMain
import co.yap.modules.dashboard.more.yapforyou.states.YapForYouMainState
import co.yap.networking.transactions.responsedtos.achievement.Achievement
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent

class YapForYouMainViewModel(application: Application) :
    BaseViewModel<IYapForYouMain.State>(application),
    IYapForYouMain.ViewModel {

    override val state: YapForYouMainState = YapForYouMainState(application)
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override var achievement: Achievement? = null
    override var selectedPosition: Int = 0
    override var achievements: MutableList<Achievement> = mutableListOf()

    override fun handlePressButton(id: Int) {
        clickEvent.setValue(id)
    }

    override fun getDescriptionContent(tag: String) {
        state.descriptionDataModel?.set(
            state.yapForYouManager.getDescriptionData(
                tag = tag
            )
        )
    }

    override fun getYapForYouContent(tag: String) {
        state.yapForYouManager.initializeYFYData(tag = tag)
    }
}
