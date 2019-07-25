package co.yap.modules.kyc.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.modules.kyc.enums.DocScanStatus
import co.yap.modules.kyc.interfaces.IKYCHome
import co.yap.modules.kyc.states.KYCHomeState
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent
import com.digitify.identityscanner.modules.docscanner.models.IdentityScannerResult

class KYCHomeViewModel(application: Application) : BaseViewModel<IKYCHome.State>(application), IKYCHome.ViewModel {

    override val state: KYCHomeState = KYCHomeState()
    override val clickEvent: SingleClickEvent = SingleClickEvent()

    override fun handlePressOnNextButton(id: Int) {
        clickEvent.setValue(id)
    }

    override fun handlePressOnScanCard(id: Int) {
        clickEvent.setValue(id)
    }

    override fun handlePressOnSkipButton(id: Int) {
        clickEvent.setValue(id)
    }

    override fun onEIDScanningComplete(result: IdentityScannerResult) {
        state.eidScanStatus = DocScanStatus.SCAN_COMPLETED
    }
}