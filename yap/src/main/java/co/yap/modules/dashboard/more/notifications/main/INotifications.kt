package co.yap.modules.dashboard.more.notifications.main

import android.database.Observable
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import co.yap.modules.yapnotification.models.Notification
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface INotifications {

    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State> {

    }

    interface State : IBase.State {

    }
}