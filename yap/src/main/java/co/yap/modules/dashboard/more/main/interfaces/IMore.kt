package co.yap.modules.dashboard.more.main.interfaces

import androidx.lifecycle.MutableLiveData
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleLiveEvent

interface IMore {
    interface State : IBase.State {
        var tootlBarTitle: String
        var tootlBarVisibility: Int
        var tootlBarBadgeVisibility: Boolean
    }

    interface ViewModel : IBase.ViewModel<State> {
        fun handlePressOnBackButton()
        fun handlePressOnTickButton()
        fun handlePressOnBadge()
        val backButtonPressEvent: SingleLiveEvent<Boolean>
        var preventTakeDeviceScreenShot: MutableLiveData<Boolean>
        var BadgeVisibility: Boolean
        val badgeButtonPressEvent: SingleLiveEvent<Boolean>
    }

    interface View : IBase.View<ViewModel>
}