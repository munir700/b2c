package co.yap.sendmoney.editbeneficiary.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.sendmoney.editbeneficiary.interfaces.IEditBeneficiary
import co.yap.sendmoney.editbeneficiary.states.EditBeneficiaryStates
import co.yap.sendmoney.viewmodels.SendMoneyBaseViewModel
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.SingleClickEvent

class EditBeneficiaryViewModel(application: Application) :
    SendMoneyBaseViewModel<IEditBeneficiary.State>(application), IEditBeneficiary.ViewModel
    , IRepositoryHolder<CustomersRepository> {
    override val repository: CustomersRepository = CustomersRepository
    override val state: EditBeneficiaryStates = EditBeneficiaryStates(application)

    override var clickEvent: SingleClickEvent? = SingleClickEvent()

    override fun handlePressOnConfirm(id: Int) {
        clickEvent?.setValue(id)
    }

    override var onUpdateSuccess: MutableLiveData<Boolean> = MutableLiveData()

    override fun requestUpdateBeneficiary() {
        launch {
            state.loading = true
            when (val response = repository.editBeneficiary(state.beneficiary)) {
                is RetroApiResponse.Success -> {
                    state.loading = false
                    onUpdateSuccess.value = true
                    // state.toast = response.data.toString()
                }

                is RetroApiResponse.Error -> {
                    state.loading = false
                    onUpdateSuccess.value = false
                    state.toast = response.error.message
                }
            }
        }
    }

    override fun requestCountryInfo() {
        launch {
            state.loading = true
            when (val response =
                repository.getCountryDataWithISODigit(
                    state.beneficiary?.country ?: ""
                )) {
                is RetroApiResponse.Success -> {
                    state.loading = false
                    state.needIban = response.data.ibanMandatory
                }
                is RetroApiResponse.Error -> {
                    state.loading = false
                    state.toast = response.error.message
                }

            }
        }
    }
}