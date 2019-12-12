package co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.interfaces.IBeneficiaryAccountDetails
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.states.BeneficiaryAccountDetailsState
import co.yap.modules.dashboard.yapit.sendmoney.viewmodels.SendMoneyBaseViewModel
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.SingleLiveEvent

class BeneficiaryAccountDetailsViewModel(application: Application) :
    SendMoneyBaseViewModel<IBeneficiaryAccountDetails.State>(application),
    IBeneficiaryAccountDetails.ViewModel,
    IRepositoryHolder<CustomersRepository> {

    override val backButtonPressEvent: SingleLiveEvent<Boolean> = SingleLiveEvent()
    override val state: BeneficiaryAccountDetailsState = BeneficiaryAccountDetailsState()
    override val repository: CustomersRepository = CustomersRepository
    override var clickEvent: SingleClickEvent = SingleClickEvent()

    override fun handlePressOnAddBank(id: Int) {
        clickEvent.setValue(id)
    }

    override fun onResume() {
        super.onResume()
        setToolBarTitle(getString(Strings.screen_add_beneficiary_display_text_title))
        //toggleAddButtonVisibility(false)
    }

    override fun createBeneficiaryRequest() {
        parentViewModel?.beneficiary?.value?.let {
            launch {
                state.loading = true
                when (val response = repository.addBeneficiary(it)) {
                    is RetroApiResponse.Success -> {
                        state.loading = false
                        state.toast = response.data.toString()
                    }

                    is RetroApiResponse.Error -> {
                        state.loading = false
                        state.toast = response.error.message
                    }
                }
            }
        }
    }
}