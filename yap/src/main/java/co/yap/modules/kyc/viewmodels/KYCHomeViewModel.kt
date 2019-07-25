package co.yap.modules.kyc.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.modules.kyc.interfaces.IKYCHome
import co.yap.modules.kyc.states.KYCHomeState
import co.yap.yapcore.BaseViewModel

class KYCHomeViewModel(application: Application) : BaseViewModel<IKYCHome.State>(application), IKYCHome.ViewModel { 

    override val state: KYCHomeState = KYCHomeState()
    override val clickListener: MutableLiveData<Int> = MutableLiveData()

    override fun handlePressOnNextButton(id: Int) {
        clickListener.value = id
    }

    override fun handlePressOnScanCard(id: Int) {
        clickListener.value = id
    }

    override fun handlePressOnSkipButton(id: Int) {
        clickListener.value = id
    }
}