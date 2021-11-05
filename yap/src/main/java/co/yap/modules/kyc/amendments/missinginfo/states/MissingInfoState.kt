package co.yap.modules.kyc.amendments.missinginfo.states

import android.app.Application
import android.content.Context
import co.yap.modules.kyc.amendments.missinginfo.interfaces.IMissingInfo
import co.yap.yapcore.BaseState

class MissingInfoState(application: Application) : BaseState(), IMissingInfo.State {
    var context: Context = application.applicationContext
}