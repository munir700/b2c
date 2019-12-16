package co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.viewmodels

import android.app.Application
import co.yap.R
import co.yap.countryutils.country.Country
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.interfaces.IBankDetails
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.states.BankDetailsState
import co.yap.modules.dashboard.yapit.sendmoney.viewmodels.SendMoneyBaseViewModel
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.requestdtos.OtherBankQuery
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
            if (it.isNotEmpty())
                when (SendMoneyBeneficiaryType.valueOf(it)) {
                    SendMoneyBeneficiaryType.RMT -> {
                        state.buttonText = "Find Bank"
                        state.hideSwiftSection = false
                        getOtherBankParams(parentViewModel?.selectedCountry?.value)
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

            parentViewModel?.transferType?.value?.let {
                if (it.isNotEmpty())
                    when (SendMoneyBeneficiaryType.valueOf(it)) {
                        SendMoneyBeneficiaryType.RMT -> {
                            state.buttonText = "Find Bank"
                            state.hideSwiftSection = false
                            //searchRMTBanks(otherSearchParams(parentViewModel?.selectedCountry?.value))
                        }
                        SendMoneyBeneficiaryType.SWIFT -> {
                            clickEvent.setValue(id)
                            //Swift changes
                        }
                        else -> {

                        }
                    }
            }
        } else {
            clickEvent.setValue(id)
        }
    }

    private fun otherSearchParams(country: Country?): Any {
        val query = OtherBankQuery()
        parentViewModel?.selectedCountry?.value?.let {
            query.max_records = 10
            query.other_bank_country = it.getCurrency()?.name
        }

//        val bankName = OtherBankQuery.Params()
//        bankName.id = ""
//        query.params?.add(bankName)
//
//        val branchName = OtherBankQuery.Params()
//        query.params?.add(branchName)
//
//        val bankCity = OtherBankQuery.Params()
//        query.params?.add(bankCity)

        return query
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
                when (val response = repository.findOtherBank(OtherBankQuery())) {
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

    private fun getOtherBankParams(country: Country?) {
        country?.let {
            launch {
                state.loading = true
                when (val response = repository.getOtherBankParams(it.getName())) {
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