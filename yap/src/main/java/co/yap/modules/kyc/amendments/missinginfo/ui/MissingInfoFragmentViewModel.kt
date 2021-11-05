package co.yap.modules.kyc.amendments.missinginfo.ui

import android.app.Application
import co.yap.modules.kyc.amendments.missinginfo.interfaces.IMissingInfo
import co.yap.modules.kyc.amendments.missinginfo.states.MissingInfoState
import co.yap.yapcore.BaseViewModel

class MissingInfoFragmentViewModel(application: Application) :
    BaseViewModel<IMissingInfo.State>(application), IMissingInfo.ViewModel {

    override val state: IMissingInfo.State = MissingInfoState(application = application)

    override fun handlePressView(id: Int) {
    }
}