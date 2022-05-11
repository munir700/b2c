package co.yap.modules.location.kyc_additional_info.birth_info

import android.app.Application
import android.view.View
import androidx.lifecycle.MutableLiveData
import co.yap.countryutils.country.Country
import co.yap.modules.location.viewmodels.LocationChildViewModel
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.requestdtos.BirthInfoRequest
import co.yap.networking.customers.responsedtos.AmendmentSection
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.Dispatcher
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.enums.AccountStatus
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.validation.IValidator
import co.yap.yapcore.helpers.validation.Validator
import co.yap.yapcore.interfaces.OnItemClickListener
import co.yap.yapcore.managers.SessionManager
import kotlinx.coroutines.delay

class POBSelectionViewModel(application: Application) :
    LocationChildViewModel<IPOBSelection.State>(application),
    IPOBSelection.ViewModel, IRepositoryHolder<CustomersRepository>, IValidator,
    Validator.ValidationListener {
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override val state: IPOBSelection.State =
        POBSelectionState()
    override val dualNationalityQuestionOptions: ArrayList<String> = arrayListOf("No", "Yes")
    override var populateSpinnerData: MutableLiveData<ArrayList<Country>> = MutableLiveData()
    override var populateCitiesSpinnerData: MutableLiveData<ArrayList<String>> = MutableLiveData()
    override val repository: CustomersRepository = CustomersRepository
    override var validator: Validator? = Validator(null)

    override fun handleOnPressView(id: Int) {
        clickEvent.setValue(id)
    }

    override fun onCreate() {
        super.onCreate()
        validator?.setValidationListener(this)
        getAllCountries()
        state.isDualNational.set(false)
    }

    override fun onResume() {
        super.onResume()
        if (parentViewModel?.isOnBoarding == true) {
            progressToolBarVisibility(true)
            setProgress(70)
        }
    }

    override fun getAllCountries() {
        if (!parentViewModel?.countries.isNullOrEmpty()) {
            populateSpinnerData.value = parentViewModel?.countries
            state.eidNationality.set(
                parentViewModel?.countries?.first { it.isoCountryCode2Digit == SessionManager.homeCountry2Digit }
                    ?.getName() ?: "")
            state.selectedCountry.set(parentViewModel?.countries?.first { it.isoCountryCode2Digit == SessionManager.homeCountry2Digit })
            if (isFromAmendment()) {
                state.previousEidNationality.set(parentViewModel?.countries?.first { it.isoCountryCode2Digit == SessionManager.homeCountry2Digit }
                    ?.getName())
            }
            populateSpinnerData.value =
                parentViewModel?.countries?.filter { it.isoCountryCode2Digit != SessionManager.homeCountry2Digit } as ArrayList<Country>
        } else {
            launch(Dispatcher.Background) {
                state.viewState.postValue(true)
                val response = repository.getAllCountries()
                launch {
                    when (response) {
                        is RetroApiResponse.Success -> {
                            populateSpinnerData.value =
                                Utils.parseCountryList(response.data.data, addOIndex = false)
                            parentViewModel?.countries =
                                populateSpinnerData.value as ArrayList<Country>
                            state.eidNationality.set(
                                parentViewModel?.countries?.first { it.isoCountryCode2Digit == SessionManager.homeCountry2Digit }
                                    ?.getName() ?: "")
                            state.selectedCountry.set(parentViewModel?.countries?.first { it.isoCountryCode2Digit == SessionManager.homeCountry2Digit })
                            if (isFromAmendment()) {
                                state.previousEidNationality.set(parentViewModel?.countries?.first { it.isoCountryCode2Digit == SessionManager.homeCountry2Digit }
                                    ?.getName())
                            }
                            populateSpinnerData.value =
                                parentViewModel?.countries?.filter { it.isoCountryCode2Digit != SessionManager.homeCountry2Digit } as ArrayList<Country>
                            state.viewState.value = false
                            if (isFromAmendment()) getAmendmentsBirthInfo()
                        }

                        is RetroApiResponse.Error -> {
                            state.viewState.value = false
                            state.toast = response.error.message
                        }
                    }
                }
            }
        }
    }

    override fun getAllCities(countryCode: String) {
        launch(Dispatcher.Background) {
            state.viewState.postValue(true)
            val response = repository.getAllCities(countryCode)
            launch {
                when (response) {
                    is RetroApiResponse.Success -> {
                        response.data.data?.let {
                            it.add(0,"Other")
                            populateCitiesSpinnerData.postValue(it)
                        }
                        state.viewState.value = false
                    }
                    is RetroApiResponse.Error -> {
                        state.viewState.value = false
                        state.toast = response.error.message
                    }
                }
            }
        }
    }

    override val dualNatioanlitySpinnerItemClickListener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            if (data is String) {
                if (data.equals(dualNationalityQuestionOptions.get(0))) {
                    state.selectedSecondCountry.set(null)
                    state.eidNationality.set(null)
                    state.previousEidNationality.set(null)
                    state.previousSelectedSecondCountry.set(null)
                    state.isDualNational.set(false)
                    validateForm()
                } else {
                    state.isDualNational.set(true)
                    state.eidNationality.set(parentViewModel?.countries?.first { it.isoCountryCode2Digit == SessionManager.homeCountry2Digit }
                        ?.getName())
                    validateForm()
                }
            }
        }
    }

    override fun validateForm() {
        launch {
            delay(500)
            state.validate()
            validator?.toValidate()
        }
    }

    override fun saveDOBInfo(success: () -> Unit) {
        launch {
            state.loading = true
            when (val response = repository.saveBirthInfo(
                BirthInfoRequest(
                    countryOfBirth = state.selectedCountry.get()?.getName()?.trim() ?: "",
                    cityOfBirth = state.cityOfBirth.get() ?: "",
                    isDualNationality = state.isDualNational.get(),
                    dualNationality = state.selectedSecondCountry.get()?.isoCountryCode2Digit ?: "",
                    isAmendment = isFromAmendment()
                )
            )) {
                is RetroApiResponse.Success -> {
                    success.invoke()
                    state.loading = false
                }

                is RetroApiResponse.Error -> {
                    state.loading = false
                    state.toast = response.error.message
                }
            }
        }
    }

    override fun canSkipFragment() =
        SessionManager.user?.notificationStatuses == AccountStatus.BIRTH_INFO_COLLECTED.name
                || (isFromAmendment() && (parentViewModel?.amendmentMap?.contains(AmendmentSection.BIRTH_INFO.value) == false))

    override fun getAmendmentsBirthInfo() {
        launch {
            when (val response =
                repository.getAmendmentsBirthInfo(SessionManager.user?.uuid ?: "")) {
                is RetroApiResponse.Success -> {

                    val selectedCountry: Country? =
                        parentViewModel?.countries?.find { it.isoCountryCode2Digit == response.data.data?.countryOfBirth ?: "" }
                    state.selectedCountry.set(selectedCountry)
                    selectedCountry?.let {
                        state.previousSelectedCountry.set(it.getName())
                    }
                    state.selectedCity.set(response.data.data?.cityOfBirth ?: "")
                    state.cityOfBirth.set(response.data.data?.cityOfBirth ?: "")
                    state.previousCityOfBirth.set(response.data.data?.cityOfBirth)
                    state.isDualNational.set(response.data.data?.isDualNationality ?: false)
                    if (!state.isDualNational.get()) {
                        state.selectedSecondCountry.set(null)
                        state.previousSelectedSecondCountry.set(null)
                    } else {
                        state.dualNationalityOption.value = 1
                        val selectedSecondCountry: Country? =
                            parentViewModel?.countries?.find { it.isoCountryCode2Digit == response.data.data?.dualNationality ?: "" }
                        state.selectedSecondCountry.set(selectedSecondCountry)
                        state.previousSelectedSecondCountry.set(selectedSecondCountry?.getName())
                    }
                    validateForm()
                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                }
            }
        }
    }

    //check if Amendment exist or not
    override fun isFromAmendment() = parentViewModel?.amendmentMap?.isNullOrEmpty() == false

    override fun onValidationError(validator: Validator) {
        super.onValidationError(validator)
        state.valid.set(false)
    }

    override fun onValidationSuccess(validator: Validator) {
        super.onValidationSuccess(validator)
        state.valid.set(true)
    }
}
