package co.yap.modules.kyc.viewmodels

import android.app.Application
import co.yap.modules.kyc.interfaces.IConfirmCardName
import co.yap.modules.kyc.states.ConfirmCardNameState
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.requestdtos.CardNameRequest
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.managers.SessionManager

class ConfirmCardNameViewModel(application: Application) :
    KYCChildViewModel<IConfirmCardName.State>(application),
    IConfirmCardName.ViewModel {
    val repository: CustomersRepository = CustomersRepository
    override val state = ConfirmCardNameState()
    override var clickEvent = SingleClickEvent()
    override fun handlePressOnView(viewId: Int) {
        clickEvent.setValue(viewId)
    }

    fun postProfileInformation(success: (bool: Boolean) -> Unit) {
        launch {
            state.loading = true
            when (val response = repository.updateCardName(
                CardNameRequest(
                    customerIDNumber = SessionManager.user?.currentCustomer?.customerId,
                    customerNationality = SessionManager.user?.currentCustomer?.nationality,
                    customerIDFirstName = SessionManager.user?.currentCustomer?.firstName,
                    customerIDLastName = SessionManager.user?.currentCustomer?.lastName,
                    customerIDMiddleName = parentViewModel?.state?.middleName?.get()
                )
            )) {
                is RetroApiResponse.Success -> {
                    success(true)
                }
                is RetroApiResponse.Error -> {
                    success(false)
                }
            }
        }
    }
}
