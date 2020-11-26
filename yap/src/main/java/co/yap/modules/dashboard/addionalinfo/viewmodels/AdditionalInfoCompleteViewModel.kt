package co.yap.modules.dashboard.addionalinfo.viewmodels

import android.app.Application
import co.yap.modules.dashboard.addionalinfo.interfaces.IAdditionalInfoComplete
import co.yap.modules.dashboard.addionalinfo.states.AdditionalInfoCompleteState
import co.yap.yapcore.BaseViewModel

class AdditionalInfoCompleteViewModel(application: Application) :
    BaseViewModel<IAdditionalInfoComplete.State>(application = application),
    IAdditionalInfoComplete.ViewModel {
    override val state: IAdditionalInfoComplete.State = AdditionalInfoCompleteState(application)
}