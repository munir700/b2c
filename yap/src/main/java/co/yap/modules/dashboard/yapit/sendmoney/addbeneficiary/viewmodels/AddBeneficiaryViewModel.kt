package co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.R
import co.yap.countryutils.country.Country
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.interfaces.IAddBeneficiary
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.states.AddBeneficiaryStates
import co.yap.modules.dashboard.yapit.sendmoney.viewmodels.SendMoneyBaseViewModel
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.responsedtos.sendmoney.Beneficiary
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.enums.SendMoneyBeneficiaryType
import co.yap.yapcore.helpers.Utils

class AddBeneficiaryViewModel(application: Application) :
    SendMoneyBaseViewModel<IAddBeneficiary.State>(application), IAddBeneficiary.ViewModel,
    IRepositoryHolder<CustomersRepository> {

    override val repository: CustomersRepository = CustomersRepository
    override val state: AddBeneficiaryStates = AddBeneficiaryStates()
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override var addBeneficiarySuccess: MutableLiveData<Boolean> = MutableLiveData(false)
    override var beneficiary: Beneficiary? = Beneficiary()

    override fun onCreate() {
        super.onCreate()
        state.selectedBeneficiaryType = parentViewModel?.beneficiary?.value?.beneficiaryType

        parentViewModel?.selectedCountry?.value?.let {
            state.country = it.getName()
            state.country2DigitIsoCode = it.isoCountryCode2Digit ?: "AE"
            state.flagDrawableResId =
                Country(isoCountryCode2Digit = it.isoCountryCode2Digit).getFlagDrawableResId()

            parentViewModel?.beneficiary?.value?.beneficiaryType?.let { beneficiaryType ->
                if (beneficiaryType.isEmpty()) return@let
                when (SendMoneyBeneficiaryType.valueOf(beneficiaryType)) {
                    SendMoneyBeneficiaryType.CASHPAYOUT -> {
                        state.transferType = "Cash Pickup"
                    }
                    else -> {
                        state.transferType = "Bank Transfer"
                    }
                }
            }
            it.isoCountryCode2Digit?.let {
                state.countryCode = Utils.getCountryCodeFormString(it)
            }
            state.currency = it.getCurrency()?.code ?: ""
        }
    }

    override fun handlePressOnAddNow(id: Int) {
        if (id == R.id.confirmButton) {
            setBeneficiaryDetail()
            when (state.transferType) {
                "Cash Pickup" -> {
                    parentViewModel?.beneficiary?.value?.currency = null
                    addCashPickupBeneficiary()
                }
                else -> {
                }
            }
            clickEvent.setValue(id)
        } else {
            clickEvent.setValue(id)
        }
    }

    private fun setBeneficiaryDetail() {
        parentViewModel?.beneficiary?.value?.title = state.nickName
        parentViewModel?.beneficiary?.value?.firstName = state.firstName
        parentViewModel?.beneficiary?.value?.lastName = state.lastName
        parentViewModel?.beneficiary?.value?.mobileNo = state.mobileNo
        parentViewModel?.selectedCountry?.value?.let {
            parentViewModel?.beneficiary?.value?.currency = it.getCurrency()?.code
            parentViewModel?.beneficiary?.value?.country = it.isoCountryCode2Digit
        }
    }

    override fun handlePressOnAddDomestic(id: Int) {
        clickEvent.setValue(id)
    }

    override fun addCashPickupBeneficiary() {
        parentViewModel?.beneficiary?.value?.let {
            launch {
                state.loading = true
                when (val response = repository.addBeneficiary(it)) {
                    is RetroApiResponse.Success -> {
                        state.loading = false
                        beneficiary = response.data.data
                        addBeneficiarySuccess.value = true
                    }

                    is RetroApiResponse.Error -> {
                        state.loading = false
                        state.toast = response.error.message
                    }
                }
            }
        }
    }

    override fun addDomesticBeneficiary(objBeneficiary: Beneficiary?) {
        objBeneficiary?.let {
            launch {
                state.loading = true
                when (val response = repository.addBeneficiary(it)) {
                    is RetroApiResponse.Success -> {
                        state.loading = false
                        beneficiary = response.data.data
                        addBeneficiarySuccess.value = true
                    }

                    is RetroApiResponse.Error -> {
                        state.loading = false
                        state.toast = response.error.message
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        setToolBarTitle(getString(Strings.screen_add_beneficiary_display_text_title))
    }
}