package co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.R
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.interfaces.IBeneficiaryAccountDetails
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.states.BeneficiaryAccountDetailsState
import co.yap.modules.dashboard.yapit.sendmoney.viewmodels.SendMoneyBaseViewModel
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.SingleLiveEvent
import co.yap.yapcore.enums.SendMoneyBeneficiaryType

class BeneficiaryAccountDetailsViewModel(application: Application) :
    SendMoneyBaseViewModel<IBeneficiaryAccountDetails.State>(application),
    IBeneficiaryAccountDetails.ViewModel,
    IRepositoryHolder<CustomersRepository> {

    override val backButtonPressEvent: SingleLiveEvent<Boolean> = SingleLiveEvent()
    override val success: MutableLiveData<Boolean> = MutableLiveData(false)
    override val state: BeneficiaryAccountDetailsState = BeneficiaryAccountDetailsState()
    override val repository: CustomersRepository = CustomersRepository
    override var clickEvent: SingleClickEvent = SingleClickEvent()

    override fun onCreate() {
        super.onCreate()
        parentViewModel?.beneficiary?.value?.beneficiaryType?.let { beneficiaryType ->
            if (beneficiaryType.isNotEmpty())
                when (SendMoneyBeneficiaryType.valueOf(beneficiaryType)) {
                    SendMoneyBeneficiaryType.SWIFT -> {
                        state.showlyIban.set(true)
                        //state.showlyConfirmIban.set(true)
                    }
                    SendMoneyBeneficiaryType.RMT -> {
                        state.showlyIban.set(true)
                        //state.showlyConfirmIban.set(true)
                    }
                    else -> {

                    }
                }
        }
        parentViewModel?.beneficiary?.value?.let {
            state.bankName = it.bankName ?: ""
            state.idCode = ""
            state.bankAddress = it.branchAddress ?: ""
            state.bankPhoneNumber = ""
        }
    }

    override fun handlePressOnAddBank(id: Int) {
        if (id == R.id.confirmButton) {
            parentViewModel?.beneficiary?.value?.beneficiaryType?.let { it ->
                if (it.isNotEmpty())
                    when (SendMoneyBeneficiaryType.valueOf(it)) {
                        SendMoneyBeneficiaryType.SWIFT -> {
                            parentViewModel?.beneficiary?.value?.accountNo = state.accountIban
                            createBeneficiaryRequest()
                        }
                        SendMoneyBeneficiaryType.RMT -> {
                            parentViewModel?.beneficiary?.value?.accountNo = state.accountIban
                            createBeneficiaryRequest()
                        }
                        else -> {
                            clickEvent.setValue(id)
                        }
                    }
            }
        } else
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
                        success.value = true
                    }

                    is RetroApiResponse.Error -> {
                        state.loading = false
                        state.toast = response.error.message
                        success.value = false
                    }
                }
            }
        }
    }

    override fun retry() {
    }
}