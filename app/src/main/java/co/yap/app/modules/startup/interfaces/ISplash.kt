package co.yap.app.modules.startup.interfaces

import androidx.lifecycle.MutableLiveData
import co.yap.networking.customers.responsedtos.AppUpdate
import co.yap.networking.customers.responsedtos.AppUpdateResponse
import co.yap.networking.customers.responsedtos.sendmoney.Country
import co.yap.networking.messages.responsedtos.DownTime
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleLiveEvent

interface ISplash {

    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State> {
        val splashComplete: SingleLiveEvent<Boolean>
        var appUpdate: SingleLiveEvent<AppUpdate?>
        var countriesList:ArrayList<Country>
        fun getAppUpdate()
        fun getAppConfigurations()
    }

    interface State : IBase.State {
        val downTime: MutableLiveData<DownTime>
    }
}