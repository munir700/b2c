package co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.R
import co.yap.countryutils.country.Country
import co.yap.countryutils.country.utils.Currency
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.interfaces.ISelectCountry
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.states.SelectCountryState
import co.yap.modules.dashboard.yapit.sendmoney.viewmodels.SendMoneyBaseViewModel
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.enums.SendMoneyBeneficiaryType

class SelectCountryViewModel(application: Application) :
    SendMoneyBaseViewModel<ISelectCountry.State>(application), ISelectCountry.ViewModel,
    IRepositoryHolder<CustomersRepository> {

    override var populateSpinnerData: MutableLiveData<List<Country>> = MutableLiveData()
    override var countries: ArrayList<Country> = ArrayList()
    override val repository: CustomersRepository = CustomersRepository
    override val state: SelectCountryState = SelectCountryState(application)
    override var clickEvent: SingleClickEvent = SingleClickEvent()

    override fun onTransparentViewClick(id: Int) {
        clickEvent.setValue(id)
    }

    override fun handlePressOnSeclectCountry(id: Int) {
        if (id == R.id.nextButton) {
            parentViewModel?.selectedCountry?.value = state.selectedCountry
            parentViewModel?.selectedCountry?.value?.let { country ->
                country.cashPickUp?.let { it ->
                    if (!it) {
                        country.isoCountryCode2Digit?.let { code ->
                            if (code.equals("ae", true)) {
                                parentViewModel?.transferType?.value =
                                    (SendMoneyBeneficiaryType.DOMESTIC.name)
                                state.isDomestic.set(true)
                            } else {
                                state.isDomestic.set(false)
                                country.rmtCountry?.let { isRmt ->
                                    if (isRmt) {
                                        parentViewModel?.transferType?.value =
                                            (SendMoneyBeneficiaryType.RMT.name)
                                    } else {
                                        parentViewModel?.transferType?.value =
                                            (SendMoneyBeneficiaryType.SWIFT.name)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        clickEvent.setValue(id)
    }

    override fun onCreate() {
        super.onCreate()
        getAllCountries()
    }

    override fun onResume() {
        super.onResume()
        state.valid = false
        setToolBarTitle(getString(Strings.screen_add_beneficiary_display_text_title))
    }

    private fun getAllCountries() {

        if (!countries.isNullOrEmpty()) {
            populateSpinnerData.setValue(countries)
        } else {
            launch {
                state.loading = true
                when (val response = repository.getAllCountries()) {
                    is RetroApiResponse.Success -> {
                        response.data.data?.let { it ->
                            countries.clear()
                            countries.add(
                                0,
                                Country(name = getString(Strings.screen_add_beneficiary_display_text_select_country))
                            )
                            countries.addAll(it.map {
                                Country(
                                    id = it.id,
                                    isoCountryCode3Digit = it.isoCountryCode2Digit,
                                    cashPickUp = it.cashPickUp,
                                    rmtCountry = it.rmtCountry,
                                    isoCountryCode2Digit = it.isoCountryCode2Digit,
                                    supportedCurrencies = it.currencyList?.map { cur ->
                                        Currency(
                                            code = cur.code,
                                            default = cur.default,
                                            name = cur.name,
                                            active = cur.active
                                        )
                                    },
                                    active = it.active,
                                    isoNum = it.isoNum,
                                    signUpAllowed = it.signUpAllowed,
                                    cashPickUpAllowed = it.cashPickUp,
                                    name = it.name,
                                    currency = Currency(
                                        code = it.currencyList?.firstOrNull { cur -> cur.default!! }?.code,
                                        default = it.currencyList?.firstOrNull { cur -> cur.default!! }?.default,
                                        name = it.currencyList?.firstOrNull { cur -> cur.default!! }?.name,
                                        active = it.currencyList?.firstOrNull { cur -> cur.default!! }?.active
                                    )
                                )
                            })
                            populateSpinnerData.setValue(countries)
                        }
                        state.loading = false
                    }

                    is RetroApiResponse.Error -> {
                        state.loading = false
                        state.toast = response.error.message

                    }
                }
            }
        }
    }

    override fun onCountrySelected(pos: Int) {
        if (pos == 0) {
            state.selectedCountry = null
        } else {
            val country: Country = countries[pos]
            state.selectedCountry = country
        }
    }
}