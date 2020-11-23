package co.yap.modules.dashboard.addionalinfo.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.modules.dashboard.addionalinfo.interfaces.IAdditionalInfo
import co.yap.modules.dashboard.addionalinfo.states.AdditionalInfoState
import co.yap.yapcore.BaseViewModel

class AdditionalInfoViewModel(application: Application) :
    BaseViewModel<IAdditionalInfo.State>(application = application),
    IAdditionalInfo.ViewModel {
    override val stepCount: MutableLiveData<Int> = MutableLiveData(0)
    override val state: IAdditionalInfo.State = AdditionalInfoState()

    override fun onCreate() {
        super.onCreate()
    }
}