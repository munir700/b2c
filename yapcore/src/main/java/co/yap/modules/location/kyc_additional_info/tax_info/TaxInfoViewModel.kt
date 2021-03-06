package co.yap.modules.location.kyc_additional_info.tax_info

import android.app.Application
import android.view.View
import androidx.databinding.ObservableField
import co.yap.countryutils.country.Country
import co.yap.modules.location.viewmodels.LocationChildViewModel
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.requestdtos.TaxInfoDetailRequest
import co.yap.networking.customers.requestdtos.TaxInfoRequest
import co.yap.networking.customers.responsedtos.AmendmentSection
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.R
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.enums.AccountStatus
import co.yap.yapcore.firebase.FirebaseEvent
import co.yap.yapcore.firebase.trackEventWithScreenName
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.interfaces.OnItemClickListener
import co.yap.yapcore.managers.SessionManager

class TaxInfoViewModel(application: Application) :
    LocationChildViewModel<ITaxInfo.State>(application),
    ITaxInfo.ViewModel, IRepositoryHolder<CustomersRepository> {
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override val state: ITaxInfo.State =
        TaxInfoState()
    override var taxInfoList: MutableList<TaxModel> = mutableListOf()
    override var taxInfoAdaptor: TaxInfoAdaptor =
        TaxInfoAdaptor(
            taxInfoList,
            amendmentMap = parentViewModel?.amendmentMap)
    override val repository: CustomersRepository = CustomersRepository
    override var reasonsList: ArrayList<String> = arrayListOf()
    override var options = arrayListOf("No", "Yes")
    private var rowTitles = arrayListOf(
        "Country of tax residence",
        "Select a second country of tax residence",
        "Select a third country of tax residence"
    )

    override
    fun onCreate() {
        super.onCreate()
        getReasonsList()
        setupRecycleView()
        taxInfoAdaptor.amendmentMap = parentViewModel?.amendmentMap
    }

    override fun onResume() {
        super.onResume()
        if (parentViewModel?.isOnBoarding == true) {
            progressToolBarVisibility(true)
            setProgress(80)
        }
    }

    private fun setupRecycleView() {
        taxInfoAdaptor.setItemListener(listener)
    }

    override fun handleOnPressView(id: Int) {
        if (id == R.id.cbTermsAndConditions) {
            state.isAgreed.set(!(state.isAgreed.get() as Boolean))
            state.valid.set(isTaxInfoValid(taxInfoList))
        } else
            clickEvent.setValue(id)
    }

    override fun getReasonsList() {
        launch {
            state.loading = true
            when (val response = repository.getTaxReasons()) {
                is RetroApiResponse.Success -> {
                    reasonsList = response.data.reasons

                    if (!parentViewModel?.countries.isNullOrEmpty()) {
                        if (taxInfoList.size == 0) {
                            createModel(reasonsList, options, ObservableField(rowTitles[0]))
                        }
                        state.onSuccess.set(true)
                        state.loading = false

                    } else
                        getAllCountries {
                            parentViewModel?.countries = it
                            if (taxInfoList.size == 0) {
                                createModel(reasonsList, options, ObservableField(rowTitles[0]))
                            }
                            state.onSuccess.set(true)
                            state.loading = false
                        }
                }

                is RetroApiResponse.Error -> {
                    state.loading = false
                    state.onSuccess.set(false)
                    state.toast = response.error.message
                }
            }
        }
    }

    val listener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            when (view.id) {
                R.id.ivCross -> {
                    val index = taxInfoList.indexOf(data as TaxModel)
                    taxInfoList.removeAt(index)
                    taxInfoAdaptor.notifyItemRemoved(index)
                    taxInfoList.last().canAddMore.set(true)
                    taxInfoList.last().taxRowTitle.set(rowTitles[taxInfoList.size - 1])
                    state.valid.set(isTaxInfoValid(taxInfoList))
                }
                R.id.lyAddCountry -> {
                    trackEventWithScreenName(FirebaseEvent.ADD_TAX_COUNTRY)
                    createModel(
                        reasonsList,
                        options,
                        ObservableField(rowTitles[taxInfoList.size])
                    )
                    state.valid.set(isTaxInfoValid(taxInfoList))
                }
                R.id.etTinNumber -> { // on tin number change
                    state.valid.set(isTaxInfoValid(taxInfoList))
                }
                R.id.optionsSpinner -> { // on options selected
                    state.valid.set(isTaxInfoValid(taxInfoList))
                }
                R.id.reasonsSpinner -> { // on reason selected
                    state.valid.set(isTaxInfoValid(taxInfoList))
                }
                R.id.bcountries -> {
                    state.viewState.value =
                        ITaxInfo.CountryPicker(
                            view,
                            data,
                            pos
                        )
                }
            }
        }
    }

    override fun onCountryPicked(view: View, country: Country, itemModel: TaxModel, pos: Int) {
        taxInfoList[pos].selectedCountry = country
        taxInfoAdaptor.notifyItemChanged(pos)
        state.valid.set(isTaxInfoValid(taxInfoList))
    }

    override fun createModel(
        reasons: ArrayList<String>,
        options: ArrayList<String>, title: ObservableField<String>
    ) {
        taxInfoList.add(
            TaxModel(
                countries = parentViewModel?.countries ?: arrayListOf(),
                reasons = reasons,
                options = options,
                //CONDITION PERFORMED on the bases of Amendment data
                canAddMore = if (isFromAmendment()) ObservableField(false) else ObservableField(
                    taxInfoList.size in 0..1
                ),
                //CONDITION PERFORMED on the bases of Amendment data
                taxRowNumber = if (isFromAmendment()) ObservableField(false) else ObservableField(
                    taxInfoList.isNotEmpty()
                ),
                taxRowTitle = title,
                selectedCountry = if (taxInfoList.size in 0..0) parentViewModel?.countries?.first { country -> country.isoCountryCode2Digit == "AE" } else null
            )
        )
        taxInfoAdaptor.notifyItemInserted(taxInfoList.size)
    }

    private fun isTaxInfoValid(taxInfoList: MutableList<TaxModel>): Boolean {
        var valid = false
        for (taxInfo: TaxModel in taxInfoList) {
            valid = if (taxInfo.selectedCountry == null || taxInfo.selectedCountry?.getName()
                    .equals("Select country")
            ) {
                false
            } else {
                if (taxInfo.selectedOption.get().equals("No")) {
                    !taxInfo.selectedReason.isBlank()
                } else {
                    !taxInfo.tinNumber.get().isNullOrBlank()
                }
            }
            if (!valid) break
        }
        return valid && state.isAgreed.get() == true
    }

    override fun saveInfoDetails(isSubmit: Boolean, success: (pdfUrl: String?) -> Unit) {
        launch {
            state.loading = true
            when (val response = repository.saveTaxInfo(
                TaxInfoRequest(
                    usNationalForTax = !(state.isAgreed.get() ?: false),
                    submit = isSubmit,
                    taxInfoDetails = getTaxDetails(taxInfoList),
                    isAmendment = isFromAmendment()
                )
            )) {
                is RetroApiResponse.Success -> {
                    success.invoke(response.data.pdf)
                    state.loading = false
                }

                is RetroApiResponse.Error -> {
                    state.loading = false
                    state.toast = response.error.message
                }
            }
        }
    }

    private fun getTaxDetails(taxInfoList: MutableList<TaxModel>): ArrayList<TaxInfoDetailRequest> {
        val taxList: ArrayList<TaxInfoDetailRequest> = ArrayList()
        for (taxInfo: TaxModel in taxInfoList) {
            taxList.add(
                TaxInfoDetailRequest(
                    country = taxInfo.selectedCountry?.getName()?.trim() ?: "",
                    tinAvailable = taxInfo.selectedOption.get().equals("Yes"),
                    reasonInCaseNoTin = if (taxInfo.selectedOption.get()
                            .equals("Yes")
                    ) "" else taxInfo.selectedReason,
                    tinNumber = if (taxInfo.selectedOption.get()
                            .equals("Yes")
                    ) taxInfo.tinNumber.get()
                        ?: "" else ""
                )
            )
        }
        return taxList
    }

    override fun getAllCountries(success: (ArrayList<Country>) -> Unit) {
        if (!parentViewModel?.countries.isNullOrEmpty()) {
            success(parentViewModel?.countries ?: arrayListOf())
        } else {
            launch {
                when (val response = repository.getAllCountries()) {
                    is RetroApiResponse.Success -> {
                        success(
                            Utils.parseCountryList(
                                response.data.data,
                                addOIndex = false
                            ) as ArrayList<Country>
                        )
                        if (isFromAmendment()) getAmendmentsTaxInfo()
                    }
                    is RetroApiResponse.Error -> {
                        state.toast = response.error.message
                    }
                }
            }
        }
    }

    override fun canSkipFragment() =
        SessionManager.user?.notificationStatuses == AccountStatus.FATCA_GENERATED.name
                || (isFromAmendment() && parentViewModel?.amendmentMap?.contains(AmendmentSection.TAX_INFO.value) == false)

    //Fetch data from api for Amendment data
    override fun getAmendmentsTaxInfo() {
        launch {
            when (val response = repository.getAmendmentsTaxInfo(SessionManager.user?.uuid ?: "")) {
                is RetroApiResponse.Success -> {

                    var taxCountriesList = response.data.data?.taxInformationDetails
                    if (taxCountriesList != null) {
                        for (i in 0 until taxCountriesList!!.size) {
                            //set Amendment data into views

                            if (!taxCountriesList.get(i)?.country.equals("United Arab Emirates")) {
                                taxInfoList.add(
                                    TaxModel(
                                        countries = parentViewModel?.countries ?: arrayListOf(),
                                        reasons = reasonsList,
                                        options = options,
                                        canAddMore = ObservableField(false),
                                        taxRowNumber = ObservableField(false),
                                        taxRowTitle = ObservableField(rowTitles[i]),
                                        tinNumber = ObservableField(
                                            taxCountriesList.get(i)?.tinNumber ?: ""
                                        ),
                                        selectedCountry = parentViewModel?.countries?.find {
                                            it.getName()
                                                .equals(taxCountriesList.get(i)?.country ?: "")
                                        },
                                        previousTinNumber = ObservableField(
                                            taxCountriesList.get(i)?.tinNumber ?: ""
                                        ),
                                        previousCountry = ObservableField(
                                            taxCountriesList.get(i)?.country ?: ""
                                        ),
                                        tagOfTinNumber = when (i) {
                                            1 -> ObservableField("TINNumber1")
                                            2 -> ObservableField(
                                                "TINNumber2"
                                            )
                                            else -> ObservableField("")
                                        },
                                        tagOfCountry = when (i) {
                                            1 -> ObservableField("CountryofTaxResidence")
                                            2 -> ObservableField(
                                                "SecondCountryofTaxResidence"
                                            )
                                            else -> ObservableField("")
                                        },
                                        selectedOption = if (taxCountriesList.get(i)?.tinNumber.equals(
                                                ""
                                            )
                                        ) ObservableField("No") else ObservableField("Yes")
                                    )
                                )
                                taxInfoAdaptor.notifyItemInserted(taxInfoList.size)
                            }
                        }
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

    fun hasKeyInAmendmentMap(key: String?): Boolean {
        if (key != null && parentViewModel?.amendmentMap != null) {
            parentViewModel?.amendmentMap?.let { it ->
                it.values.toList().forEach { it ->
                    it?.forEach {
                        if (key == it) {
                            return true
                        }
                    }
                }
            }
        }
        return false
    }
}