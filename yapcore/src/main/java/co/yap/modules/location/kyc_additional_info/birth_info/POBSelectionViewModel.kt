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
import co.yap.yapcore.interfaces.OnItemClickListener
import co.yap.yapcore.managers.SessionManager

class POBSelectionViewModel(application: Application) :
    LocationChildViewModel<IPOBSelection.State>(application),
    IPOBSelection.ViewModel, IRepositoryHolder<CustomersRepository> {
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override val state: IPOBSelection.State =
        POBSelectionState()
    override val dualNationalityQuestionOptions: ArrayList<String> = arrayListOf("No", "Yes")
    override var populateSpinnerData: MutableLiveData<ArrayList<Country>> = MutableLiveData()
    override val repository: CustomersRepository = CustomersRepository

    override fun handleOnPressView(id: Int) {
        clickEvent.setValue(id)
    }

    override fun onCreate() {
        super.onCreate()
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

    override val dualNatioanlitySpinnerItemClickListener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            if (data is String) {
                if (data.equals(dualNationalityQuestionOptions.get(0))) {
                    state.selectedSecondCountry.set(null)
                    state.isDualNational.set(false)
                    state.validate()
                } else {
                    state.isDualNational.set(true)
                    state.validate()
                }
            }
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
                || parentViewModel?.amendmentMap?.contains(AmendmentSection.BIRTH_INFO.value) == false

    override fun getAmendmentsBirthInfo() {
        launch {
            when (val response =
                repository.getAmendmentsBirthInfo(SessionManager.user?.uuid ?: "")) {
                is RetroApiResponse.Success -> {

                    val selectedCountry: Country? =
                        parentViewModel?.countries?.find { it.isoCountryCode2Digit == response.data.data?.countryOfBirth ?: "" }
                    state.selectedCountry.set(selectedCountry)

                    state.cityOfBirth.set(response.data.data?.cityOfBirth ?: "")
                    state.isDualNational.set(response.data.data?.isDualNationality ?: true)
                    if (!state.isDualNational.get()) {
                        state.selectedSecondCountry.set(null)
                        state.validate()
                    } else {
                        state.dualNationalityOption.value = 1
                        val selectedSecondCountry: Country? =
                            parentViewModel?.countries?.find { it.isoCountryCode2Digit == response.data.data?.dualNationality ?: "" }
                        state.selectedSecondCountry.set(selectedSecondCountry)
                        state.validate()
                    }
                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                }
            }
        }
    }

    //check if Amendment exist or not
    override fun isFromAmendment() = parentViewModel?.amendmentMap?.isNullOrEmpty() == false
}
