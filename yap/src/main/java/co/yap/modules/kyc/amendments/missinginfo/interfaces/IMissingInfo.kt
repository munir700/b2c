package co.yap.modules.kyc.amendments.missinginfo.interfaces

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.modules.dashboard.more.yapforyou.adapters.AchievementGoalAdaptor
import co.yap.modules.kyc.amendments.missinginfo.adapters.MissingInfoAdapter
import co.yap.yapcore.IBase
import java.util.*
import kotlin.collections.ArrayList

interface IMissingInfo {

    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State> {
        val adapter: ObservableField<MissingInfoAdapter>
        val onClickEvent: MutableLiveData<Int>
        val missingInfoItems: MutableLiveData<ArrayList<String>>
        fun getMissingInfoItems()
        fun handlePressView(id: Int)
    }

    interface State : IBase.State {
    }
}