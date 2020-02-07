package co.yap.modules.dashboard.more.yapforyou.states

import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import co.yap.modules.dashboard.more.yapforyou.interfaces.IYAPForYou
import co.yap.yapcore.BaseState

class YAPForYouState : BaseState(), IYAPForYou.State {

    @get:Bindable
    override var selectedAchievementTitle: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.selectedAchievementTitle)

        }


    @get:Bindable
    override var selectedAchievementPercentage: String? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.selectedAchievementPercentage)

        }


}