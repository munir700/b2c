package co.yap.modules.dashboard.home.states

import androidx.databinding.Bindable
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.BR
import co.yap.modules.dashboard.home.interfaces.IYapHome
import co.yap.networking.notification.HomeNotification
import co.yap.yapcore.BaseState

class YapHomeState : BaseState(), IYapHome.State {

    @get:Bindable
    override var availableBalance: String = "0.00"
        set(value) {
            field = value
            notifyPropertyChanged(BR.availableBalance)
        }
    override var filterCount: ObservableField<Int> = ObservableField()
    override var isTransEmpty: ObservableField<Boolean> = ObservableField(true)
    override var notificationList: MutableLiveData<MutableList<HomeNotification>> =
        MutableLiveData()
}