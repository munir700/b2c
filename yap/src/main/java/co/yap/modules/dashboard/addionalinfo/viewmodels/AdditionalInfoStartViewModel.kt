package co.yap.modules.dashboard.addionalinfo.viewmodels

import android.app.Application
import co.yap.modules.dashboard.addionalinfo.interfaces.IAdditionalInfoComplete
import co.yap.modules.dashboard.addionalinfo.interfaces.IAdditionalInfoStart
import co.yap.modules.dashboard.addionalinfo.states.AdditionalInfoCompleteState
import co.yap.modules.dashboard.addionalinfo.states.AdditionalInfoStartState
import co.yap.yapcore.BaseViewModel

class AdditionalInfoStartViewModel(application: Application) :
    BaseViewModel<IAdditionalInfoStart.State>(application = application),
    IAdditionalInfoStart.ViewModel {
    override val state: IAdditionalInfoStart.State = AdditionalInfoStartState(application)
}