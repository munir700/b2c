package co.yap.modules.onboarding.interfaces

import androidx.lifecycle.MutableLiveData
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IKfsNotification {
    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State> {
        var clickEvent: SingleClickEvent
        fun handleOnPressView(id: Int)

    }

    interface State : IBase.State {
        var allNotificationAccepted: MutableLiveData<Boolean>
        var smsNotificationAccepted: MutableLiveData<Boolean>
        var inappNotificationAccepted: MutableLiveData<Boolean>
        var emailNotificationAccepted: MutableLiveData<Boolean>
        var noNotificationAccepted: MutableLiveData<Boolean>
    }
}