package co.yap.modules.kyc.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.modules.kyc.enums.DocScanStatus
import co.yap.modules.kyc.interfaces.IKYCHome
import co.yap.modules.kyc.states.KYCHomeState
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent
import com.digitify.identityscanner.modules.docscanner.models.IdentityScannerResult

class KYCHomeViewModel(application: Application) : KYCChildViewModel<IKYCHome.State>(application), IKYCHome.ViewModel,
    IRepositoryHolder<CustomersRepository> {

    override val repository: CustomersRepository
        get() = CustomersRepository
    override val state: KYCHomeState = KYCHomeState()
    override val clickEvent: SingleClickEvent = SingleClickEvent()

    override fun onCreate() {
        super.onCreate()
        state.name[0] = parentViewModel?.name
    }

    override fun onResume() {
        super.onResume()
        requestDocuments()
    }

    override fun handlePressOnNextButton(id: Int) {
        clickEvent.setValue(id)
    }

    override fun handlePressOnScanCard(id: Int) {
        if (state.eidScanStatus != DocScanStatus.DOCS_UPLOADED && state.eidScanStatus != DocScanStatus.SCAN_COMPLETED) {
            clickEvent.setValue(id)
        }
    }

    override fun handlePressOnSkipButton(id: Int) {
        clickEvent.setValue(id)
    }

    override fun onEIDScanningComplete(result: IdentityScannerResult) {
        parentViewModel?.identity = result
        state.eidScanStatus = DocScanStatus.SCAN_COMPLETED
    }

    override fun requestDocuments() {
        launch {
            state.loading = true
            when (val response = repository.getDocuments()) {

                is RetroApiResponse.Success -> {
                    if (response.data.data.isNotEmpty()) {
                        state.eidScanStatus = DocScanStatus.DOCS_UPLOADED
                    }
                }
                is RetroApiResponse.Error -> {state.toast = response.error.message}
            }
            state.loading = false
        }
    }
}