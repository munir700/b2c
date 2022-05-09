package co.yap.modules.onboarding.interfaces

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import co.yap.modules.onboarding.enums.NotificationType
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IKfsNotification {
    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State> {
        var clickEvent: SingleClickEvent
        fun handleOnPressView(id: Int)

    }

    interface State : IBase.State {
        var valid: ObservableBoolean
        var notificationMap: MutableMap<NotificationType, Boolean?>
        var isNotificationSaved: MutableLiveData<Boolean>
    }
}