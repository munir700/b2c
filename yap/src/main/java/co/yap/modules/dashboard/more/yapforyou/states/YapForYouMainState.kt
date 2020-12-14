package co.yap.modules.dashboard.more.yapforyou.states

import android.app.Application
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import co.yap.modules.dashboard.more.yapforyou.YapForYouManager
import co.yap.modules.dashboard.more.yapforyou.interfaces.IYapForYouMain
import co.yap.modules.dashboard.more.yapforyou.models.YapForYouDataModel
import co.yap.yapcore.BaseState

class YapForYouMainState(application: Application) : BaseState(), IYapForYouMain.State {
    override var leftIcon: ObservableField<Int> = ObservableField(-1)
    override var leftIconVisibility: ObservableBoolean = ObservableBoolean(false)
    override var descriptionDataModel: ObservableField<YapForYouDataModel>? = ObservableField()
    override var yapForYouManager: YapForYouManager = YapForYouManager(application.applicationContext)
    override var yfyFeatureTitle: ObservableField<String> = ObservableField("")
    override var toolbarVisibility: ObservableBoolean = ObservableBoolean(true)
}
