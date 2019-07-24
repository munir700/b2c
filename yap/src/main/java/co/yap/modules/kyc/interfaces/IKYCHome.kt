package co.yap.modules.kyc.interfaces

import androidx.lifecycle.MutableLiveData
import co.yap.modules.kyc.fragments.DocScanStatus
import co.yap.yapcore.IBase
import com.digitify.identityscanner.modules.docscanner.models.IdentityScannerResult

interface IKYCHome {
    interface State : IBase.State {
        var valid: Boolean
        var eidScanStatus: DocScanStatus
    }

    interface ViewModel : IBase.ViewModel<State> {
        val clickListener: MutableLiveData<Int>
        fun handlePressOnScanCard(id: Int)
        fun handlePressOnNextButton(id: Int)
        fun handlePressOnSkipButton(id: Int)
        fun onEIDScanningComplete(result: IdentityScannerResult)
    }

    interface View : IBase.View<ViewModel>
}