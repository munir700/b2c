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

    override fun onCreate() {
        super.onCreate()
        setProgress(25)
        state.fullName.set(formatedUserName())
    }

    fun postProfileInformation(success: (bool: Boolean) -> Unit) {
        launch {
            state.loading = true
            when (val response = repository.updateCardName(
                CardNameRequest(
                    customerIDNumber = SessionManager.user?.currentCustomer?.customerId,
                    customerNationality = parentViewModel?.state?.nationality?.get(),
                    customerIDFirstName = parentViewModel?.state?.firstName?.get(),
                    customerIDLastName = parentViewModel?.state?.lastName?.get(),
                    customerIDMiddleName = parentViewModel?.state?.middleName?.get()
                )
            )) {
                is RetroApiResponse.Success -> {
                    state.loading = false

                    success(true)
                }
                is RetroApiResponse.Error -> {
                    success(false)
                    state.loading = false
                    showToast(response.error.message)
                }
            }
        }
    }

    private fun formatedUserName(): String {
        val first_lastName =
            "${parentViewModel?.state?.firstName?.get()} ${parentViewModel?.state?.lastName?.get()}"
        val firstMiddleLastName = parentViewModel?.state?.middleName?.get()?.let { middleName ->
            "${parentViewModel?.state?.firstName?.get()} ${middleName} ${parentViewModel?.state?.lastName?.get()}"
        } ?: first_lastName
        val formatedName = "${
            parentViewModel?.state?.firstName?.get()?.take(1)
        }. ${parentViewModel?.state?.lastName?.get()}"
        return when {
            firstMiddleLastName.length > 26 -> {
                parentViewModel?.state?.middleName?.get()?.let {
                    if (first_lastName.length > 26) {
                        formatedName
                    } else first_lastName
                } ?: formatedName
            }
            else -> firstMiddleLastName
        }
    }
}
