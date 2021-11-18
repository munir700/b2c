package co.yap.modules.kyc.viewmodels

import android.app.Application
import co.yap.modules.kyc.interfaces.IEditCardName
import co.yap.modules.kyc.states.EditCardNameState
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.requestdtos.CardNameRequest
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.managers.SessionManager

class EditCardNameViewModel(application: Application) :
    KYCChildViewModel<IEditCardName.State>(application),
    IEditCardName.ViewModel, IRepositoryHolder<CustomersRepository> {
    override val state: IEditCardName.State = EditCardNameState()
    override val repository: CustomersRepository get() = CustomersRepository
    override var clickEvent: SingleClickEvent = SingleClickEvent()

    override fun handleOnPressView(id: Int) {
        clickEvent.setValue(id)
    }

    fun postProfileInformation(success: (bool: Boolean) -> Unit) {
        launch {
            state.loading = true
            when (val response = repository.updateCardName(
                CardNameRequest(
                    customerIDNumber = parentViewModel?.identity?.citizenNumber,
                    customerNationality = SessionManager.user?.currentCustomer?.nationality,
                    customerIDFirstName = parentViewModel?.state?.firstName?.get(),
                    customerIDLastName = parentViewModel?.state?.lastName?.get(),
                    customerIDMiddleName = parentViewModel?.state?.middleName?.get(),
                    displayCardName = state.fullName.get(),
                    cardSerialNumber = SessionManager.card.value?.cardSerialNumber
                )
            )) {
                is RetroApiResponse.Success -> {
                    SessionManager.getDebitCard{
                        success(true)
                        state.loading = false
                    }

                }
                is RetroApiResponse.Error -> {
                    success(false)
                    state.loading = false
                    showToast(response.error.message)
                }
            }
        }
    }

}