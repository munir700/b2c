package co.yap.modules.dashboard.addionalinfo.viewmodels

import android.app.Application
import co.yap.modules.dashboard.addionalinfo.interfaces.IAdditionalInfoEmployment
import co.yap.modules.dashboard.addionalinfo.states.AdditionalInfoEmploymentState

class AdditionalInfoEmploymentViewModel(application: Application) :
    AdditionalInfoBaseViewModel<IAdditionalInfoEmployment.State>(application),
    IAdditionalInfoEmployment.ViewModel {
    override fun moveToNext() {
        moveStep()
    }

    override val state: IAdditionalInfoEmployment.State = AdditionalInfoEmploymentState(application)


}