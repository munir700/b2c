package co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.viewmodels

import android.app.Application
import co.yap.R
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.interfaces.IBankDetails
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.states.BankDetailsState
import co.yap.modules.dashboard.yapit.sendmoney.viewmodels.SendMoneyBaseViewModel
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.enums.SendMoneyBeneficiaryType

class BankDetailsViewModel(application: Application) :
    SendMoneyBaseViewModel<IBankDetails.State>(application), IBankDetails.ViewModel,
    IRepositoryHolder<CustomersRepository> {

    override val repository: CustomersRepository = CustomersRepository
    override val state: BankDetailsState = BankDetailsState()
    override var clickEvent: SingleClickEvent = SingleClickEvent()

    override fun onCreate() {
        super.onCreate()
        parentViewModel?.transferType?.value?.let {
            when (SendMoneyBeneficiaryType.valueOf(it)) {
                SendMoneyBeneficiaryType.RMT -> {
                    searchRMTBanks()
                }
                SendMoneyBeneficiaryType.SWIFT -> {
                    //searchRMTBanks()
                    //Swift changes
                }
                else -> {

                }
            }
        }
    }

    override fun handlePressOnView(id: Int) {
        if (id == R.id.confirmButton) {
            parentViewModel?.beneficiary?.value?.bankName = state.bankName
            parentViewModel?.beneficiary?.value?.branchName = state.bankBranch
            parentViewModel?.beneficiary?.value?.bankCity = state.bankCity
            parentViewModel?.beneficiary?.value?.swiftCode = state.swiftCode
        }
        clickEvent.setValue(id)
    }

    override fun onResume() {
        super.onResume()
        setToolBarTitle(getString(Strings.screen_add_beneficiary_display_text_title))
        //toggleAddButtonVisibility(false)
    }

//    override fun createBeneficiaryRequest() {
//        parentViewModel?.beneficiary?.value?.let {
//            launch {
//                state.loading = true
//                when (val response = repository.addBeneficiary(it)) {
//                    is RetroApiResponse.Success -> {
//                        state.loading = false
//                        state.toast = response.data.toString()
//                        clickEvent.postValue(Constants.ADD_CASH_PICK_UP_SUCCESS)
//                    }
//
//                    is RetroApiResponse.Error -> {
//                        state.loading = false
//                        state.toast = response.error.message
//                        clickEvent.postValue(Constants.ADD_CASH_PICK_UP_SUCCESS)
//
//                    }
//                }
//            }
//        }
//    }

    override fun searchRMTBanks() {
        parentViewModel?.beneficiary?.value?.let {
            launch {
                state.loading = true
                when (val response = repository.addBeneficiary(it)) {
                    is RetroApiResponse.Success -> {
                        state.loading = false
                        state.toast = response.data.toString()
                        clickEvent.postValue(Constants.ADD_CASH_PICK_UP_SUCCESS)
                    }

                    is RetroApiResponse.Error -> {
                        state.loading = false
                        state.toast = response.error.message
                        clickEvent.postValue(Constants.ADD_CASH_PICK_UP_SUCCESS)

                    }
                }
            }
        }
    }
}