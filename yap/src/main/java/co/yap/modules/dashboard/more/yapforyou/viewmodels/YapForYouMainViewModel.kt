package co.yap.modules.dashboard.more.yapforyou.viewmodels

import android.app.Application
import co.yap.modules.dashboard.more.yapforyou.YapForYouManager
import co.yap.modules.dashboard.more.yapforyou.interfaces.IYapForYouMain
import co.yap.modules.dashboard.more.yapforyou.models.Y4YAchievementData
import co.yap.modules.dashboard.more.yapforyou.models.YapForYouDataModel
import co.yap.modules.dashboard.more.yapforyou.states.YapForYouMainState
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.enums.YFYAchievementType

class YapForYouMainViewModel(application: Application) :
    BaseViewModel<IYapForYouMain.State>(application),
    IYapForYouMain.ViewModel {

    override val state: YapForYouMainState = YapForYouMainState(application)
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override var achievement: Y4YAchievementData? = null
    override var selectedPosition: Int = 0
    override var achievements: MutableList<Y4YAchievementData> = mutableListOf()
    private val yapForYouManager: YapForYouManager = YapForYouManager(context)

    override fun handlePressButton(id: Int) {
        clickEvent.setValue(id)
    }

    override fun getDescriptionContent(tag: String): YapForYouDataModel? =
        yapForYouManager.getDescriptionData(tag = tag)


    override fun configureYFYManager(tag: String) {
        yapForYouManager.configure(tag = tag)
    }

    override fun getYfyTag(): String = YFYAchievementType.ADD_CARD.type
}
