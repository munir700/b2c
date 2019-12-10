package co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.viewmodels

import android.app.Application
import co.yap.R
import co.yap.countryutils.country.Country
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.interfaces.IAddBeneficiary
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.models.AddBeneficiaryData
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.states.AddBeneficiaryStates
import co.yap.modules.dashboard.yapit.sendmoney.viewmodels.SendMoneyBaseViewModel
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.responsedtos.sendmoney.Beneficiary
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.SingleLiveEvent
import co.yap.yapcore.constants.Constants

class AddBeneficiaryViewModel(application: Application) :
    SendMoneyBaseViewModel<IAddBeneficiary.State>(application), IAddBeneficiary.ViewModel,
    IRepositoryHolder<CustomersRepository> {

    override var addBeneficiaryData: AddBeneficiaryData = AddBeneficiaryData()

    override val repository: CustomersRepository = CustomersRepository

    override val state: AddBeneficiaryStates = AddBeneficiaryStates(getApplication())

    override var clickEvent: SingleClickEvent = SingleClickEvent()

    override fun onCreate() {
        super.onCreate()
        parentViewModel?.selectedCountry?.value?.let {
            state.country = it.getName()
            state.flagDrawableResId =
                Country(isoCountryCode2Digit = it.isoCountryCode2Digit).getFlagDrawableResId()
            state.transferType = parentViewModel?.transferType?.value ?: ""
            state.currency = it.getCurrency()?.code ?: ""
        }
    }

    override fun handlePressOnAddNow(id: Int) {
        if (id == R.id.confirmButton) {
            parentViewModel?.beneficiary?.value?.bankName = state.nickName
            parentViewModel?.beneficiary?.value?.firstName = state.firstName
            parentViewModel?.beneficiary?.value?.lastName = state.lastName
            parentViewModel?.beneficiary?.value?.mobileNo = state.phoneNumber
            parentViewModel?.beneficiary?.value?.currency = state.phoneNumber
            parentViewModel?.selectedCountry?.value?.let {
                parentViewModel?.beneficiary?.value?.currency = it.getName()
                parentViewModel?.beneficiary?.value?.country = it.isoCountryCode2Digit
            }
        }
        clickEvent.setValue(id)
    }

    override fun handlePressOnAddDomestic(id: Int) {
        clickEvent.setValue(id)

    }

    override val backButtonPressEvent: SingleLiveEvent<Boolean> = SingleLiveEvent()


    override fun onResume() {
        super.onResume()
        setToolBarTitle(getString(Strings.screen_add_beneficiary_display_text_title))
        ///toggleAddButtonVisibility(false)
    }

    fun requestAddBeneficiary(beneficiary: Beneficiary) {
        var beneficiary: Beneficiary = Beneficiary()

        launch {
            state.loading = true
            when (val response = repository.addBeneficiary(beneficiary)) {
                is RetroApiResponse.Success -> {
                    state.loading = false
                }

                is RetroApiResponse.Error -> {
                    state.loading = false
                    state.toast = response.error.message

                }
            }
        }
    }

    override fun generateCashPayoutBeneficiaryRequestDTO() {
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