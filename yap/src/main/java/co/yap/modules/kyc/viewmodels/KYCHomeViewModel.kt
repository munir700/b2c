package co.yap.modules.kyc.viewmodels

import android.app.Application
import co.yap.modules.kyc.interfaces.IKYCHome
import co.yap.modules.kyc.states.KYCHomeState
import co.yap.yapcore.BaseViewModel

class KYCHomeViewModel(application: Application) : BaseViewModel<IKYCHome.State>(application), IKYCHome.ViewModel {
    override val state: KYCHomeState = KYCHomeState()
}