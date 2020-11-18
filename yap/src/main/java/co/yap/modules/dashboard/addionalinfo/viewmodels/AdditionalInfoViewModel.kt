package co.yap.modules.dashboard.addionalinfo.viewmodels

import android.app.Application
import co.yap.modules.dashboard.addionalinfo.interfaces.IAdditionalInfo
import co.yap.modules.dashboard.addionalinfo.states.AdditionalInfoState
import co.yap.yapcore.BaseViewModel

class AdditionalInfoViewModel(application: Application) :
    BaseViewModel<IAdditionalInfo.State>(application = application),
    IAdditionalInfo.ViewModel {
    override val stepList: ArrayList<String> = arrayListOf()
    override val state: IAdditionalInfo.State = AdditionalInfoState()

    override fun onCreate() {
        super.onCreate()
        stepList.add("")
        stepList.add("")
        stepList.add("")
    }
}