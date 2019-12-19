package co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.R
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.interfaces.IBankDetails
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.states.BankDetailsState
import co.yap.modules.dashboard.yapit.sendmoney.viewmodels.SendMoneyBaseViewModel
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.requestdtos.OtherBankQuery
import co.yap.networking.customers.responsedtos.beneficiary.BankParams
import co.yap.networking.customers.responsedtos.sendmoney.Bank
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.enums.SendMoneyBeneficiaryType

class BankDetailsViewModel(application: Application) :
    SendMoneyBaseViewModel<IBankDetails.State>(application), IBankDetails.ViewModel,
    IRepositoryHolder<CustomersRepository> {

    override var bankParams: MutableLiveData<List<BankParams>> = MutableLiveData()
    override var bankList: MutableLiveData<ArrayList<Bank>> = MutableLiveData()
    override val repository: CustomersRepository = CustomersRepository
    override val state: BankDetailsState = BankDetailsState()
    override var clickEvent: SingleClickEvent = SingleClickEvent()

    override fun onCreate() {
        super.onCreate()
        parentViewModel?.beneficiary?.value?.beneficiaryType?.let {
            if (it.isNotEmpty())
                when (SendMoneyBeneficiaryType.valueOf(it)) {
                    SendMoneyBeneficiaryType.RMT -> {
                        state.isRmt.set(true)
                        state.buttonText = "Find Bank"
                        state.hideSwiftSection = false
                        parentViewModel?.selectedCountry?.value?.isoCountryCode2Digit?.let { code ->
                            getOtherBankParams(code)
                        }
                    }
                    SendMoneyBeneficiaryType.SWIFT -> {
                        state.isRmt.set(false)
                        state.buttonText = "Next"
                        state.hideSwiftSection = true
                        //searchRMTBanks()
                        //Swift changes
                    }
                    else -> {
                        state.isRmt.set(false)
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

            parentViewModel?.beneficiary?.value?.beneficiaryType?.let {
                if (it.isNotEmpty())
                    when (SendMoneyBeneficiaryType.valueOf(it)) {
                        SendMoneyBeneficiaryType.RMT -> {
                            clickEvent.setValue(id)
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

    override fun onResume() {
        super.onResume()
        setToolBarTitle(getString(Strings.screen_add_beneficiary_display_text_title))
    }

    override fun searchRMTBanks(otherBankQuery: OtherBankQuery) {
        parentViewModel?.beneficiary?.value?.let {
            launch {
                state.loading = true
                when (val response = repository.findOtherBank(otherBankQuery)) {
                    is RetroApiResponse.Success -> {
                        state.loading = false
                        bankList.value = response.data.data?.banks
                    }

                    is RetroApiResponse.Error -> {
                        state.loading = false
                        state.toast = response.error.message
                        bankList.value = ArrayList()
                    }
                }
            }
        }
    }

    private fun getOtherBankParams(countryCode: String) {
        launch {
            state.loading = true
            when (val response = repository.getOtherBankParams(countryCode)) {
                is RetroApiResponse.Success -> {
                    state.loading = false
                    state.isRmt.set(true)
                    bankParams.value = response.data.data?.params
                }

                is RetroApiResponse.Error -> {
                    state.loading = false
                    state.toast = response.error.message
                }
            }
        }
    }

    override fun retry() {
    }

    fun updateBeneficiaryBankData(data: Bank) {
        parentViewModel?.beneficiary?.value?.also {
            it.bankName = data.other_bank_name
            it.identifierCode1 = data.identifier_code1
            it.identifierCode2 = data.identifier_code2
            it.branchAddress = data.other_branch_name
        }
    }
}