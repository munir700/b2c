package co.yap.modules.dashboard.yapit.sendmoney.editbeneficiary.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.modules.dashboard.yapit.sendmoney.editbeneficiary.interfaces.IEditBeneficiary
import co.yap.modules.dashboard.yapit.sendmoney.editbeneficiary.states.EditBeneficiaryStates
import co.yap.modules.dashboard.yapit.sendmoney.viewmodels.SendMoneyBaseViewModel
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.SingleClickEvent

class EditBeneficiaryViewModel(application: Application) :
    SendMoneyBaseViewModel<IEditBeneficiary.State>(application), IEditBeneficiary.ViewModel
, IRepositoryHolder<CustomersRepository> {
    override val repository: CustomersRepository = CustomersRepository
    override val state: EditBeneficiaryStates=EditBeneficiaryStates()

    override var clickEvent: SingleClickEvent? = SingleClickEvent()

    override fun handlePressOnConfirm(id: Int) {
        clickEvent?.setValue(id)
    }

    override var onUpdateSuccess: MutableLiveData<Boolean> = MutableLiveData(false)

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

    override fun getCurrenciesByCountryCode() {
        launch {
            when(val response = repository.getCurrenciesByCountryCode("PK"))
            {
                is RetroApiResponse.Success->
                {

                }
                is RetroApiResponse.Error -> {

                }
            }
        }
    }
}