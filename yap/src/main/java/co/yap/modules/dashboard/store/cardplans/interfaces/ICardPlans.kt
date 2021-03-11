package co.yap.modules.dashboard.store.cardplans.interfaces

import android.widget.VideoView
import androidx.constraintlayout.widget.ConstraintLayout
import co.yap.databinding.FragmentCardPlansBinding
import co.yap.modules.dashboard.more.yapforyou.adapters.AchievementGoalAdaptor
import co.yap.modules.dashboard.store.cardplans.adaptors.CardPlansAdapter
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface ICardPlans {
    interface View : IBase.View<ViewModel>{
        fun getBindings(): FragmentCardPlansBinding
        fun navigateToFragment(data: String)
        fun setObservers()
        fun removeObservers()
    }
    interface ViewModel : IBase.ViewModel<State>{
        var cardAdapter : CardPlansAdapter
        var clickEvent: SingleClickEvent
        fun handlePressOnView(id: Int)
        fun iniVideoView(video: VideoView)
    }
    interface State : IBase.State
}