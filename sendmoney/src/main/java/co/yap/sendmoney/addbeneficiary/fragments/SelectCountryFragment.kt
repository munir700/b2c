package co.yap.sendmoney.addbeneficiary.fragments

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import co.yap.countryutils.country.Country
import co.yap.countryutils.country.utils.Currency
import co.yap.countryutils.country.utils.CurrencyUtils
import co.yap.sendmoney.BR
import co.yap.sendmoney.R
import co.yap.sendmoney.adapters.CountryAdapter
import co.yap.sendmoney.addbeneficiary.interfaces.ISelectCountry
import co.yap.sendmoney.addbeneficiary.viewmodels.SelectCountryViewModel
import co.yap.sendmoney.fragments.SendMoneyBaseFragment
import co.yap.widgets.bottomsheet.CoreBottomSheet
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.enums.SendMoneyBeneficiaryType
import co.yap.yapcore.enums.SendMoneyTransferType
import co.yap.yapcore.helpers.extentions.getColors
import co.yap.yapcore.helpers.extentions.launchBottomSheet
import co.yap.yapcore.interfaces.OnItemClickListener
import co.yap.yapcore.managers.SessionManager
import kotlinx.android.synthetic.main.fragment_select_country.*

class SelectCountryFragment : SendMoneyBaseFragment<ISelectCountry.ViewModel>(),
    ISelectCountry.View {

    private var countryAdapter: CountryAdapter? = null

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_select_country

    override val viewModel: SelectCountryViewModel
        get() = ViewModelProviders.of(this).get(SelectCountryViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        when (viewModel.parentViewModel?.sendMoneyType) {
            SendMoneyTransferType.LOCAL.name -> skipToAddDomestic()
            SendMoneyTransferType.HOME_COUNTRY.name -> skipToAddBeneficiary()
        }
    }

    private fun skipToAddBeneficiary() {
        val homeCountry = SessionManager.getCountries()
            .find { it.isoCountryCode2Digit == SessionManager.user?.currentCustomer?.homeCountry ?: "" }
        viewModel.parentViewModel?.beneficiary?.value?.beneficiaryType =
            viewModel.getBeneficiaryTypeFromCurrency(homeCountry)
        viewModel.parentViewModel?.selectedCountry?.value = homeCountry
        viewModel.parentViewModel?.countriesList = SessionManager.getCountries()
        skipCountrySelectionFragment(R.id.action_selectCountryFragment_to_addBeneficiaryFragment)
    }

    private fun skipToAddDomestic() {
        viewModel.parentViewModel?.selectedCountry?.value = Country(
            isoCountryCode2Digit = "AE",
            name = "United Arab Emirates",
            currency = Currency(code = "AED")
        )
        viewModel.parentViewModel?.beneficiary?.value?.beneficiaryType =
            SendMoneyBeneficiaryType.DOMESTIC.name
        skipCountrySelectionFragment(R.id.action_selectCountryFragment_to_DomesticFragment)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.populateSpinnerData.observe(this, Observer {
            countriesSpinner.adapter = getCountryAdapter()
            countriesSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    viewModel.onCountrySelected(position)
                }
            }
        })
    }

    override fun onPause() {
        viewModel.clickEvent.removeObservers(this)
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        viewModel.clickEvent.observe(this, Observer {
            when (it) {
                R.id.nextButton -> {
                    if (!isDefaultCurrencyExist()) {
                        viewModel.state.selectedCountry?.getCurrency()?.let { it ->
                            if (viewModel.state.selectedCountry?.isoCountryCode2Digit == "AE") {
                                findNavController().navigate(R.id.action_selectCountryFragment_to_DomesticFragment)
                            } else {
                                it.cashPickUp?.let { cashPickup ->
                                    if (cashPickup) {
                                        moveToTransferType()
                                    } else {
                                        moveToAddBeneficiary()
                                    }
                                }
                            }
                        }
                    } else {
                        showToast("No active currencies found for selected country")
                    }
                }

                R.id.tvCountrySelect -> {
                    this.launchBottomSheet(
                        itemClickListener = itemListener,
                        label = "Select Country",
                        viewType = Constants.VIEW_WITH_FLAG,
                        countriesList = SessionManager.getCountries()
                            .filter { country -> country.isoCountryCode2Digit != "AE" }
                    )
                }
            }
        })
    }

    private val itemListener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, position: Int) {
            val country: Country = data as Country
            setTextSelection(country, position)
        }
    }

    private fun setTextSelection(country: Country, position: Int) {
        viewModel.onCountrySelected(position)
        tvCountrySelect.text = country.getName()
        tvCountrySelect.setTextColor(requireContext().getColors(R.color.colorPrimaryDark))
        tvHeadingDetail.setTextColor(requireContext().getColors(R.color.greyDark))
        val drawable: Drawable? =
            requireActivity().getDrawable(country.getFlagDrawableResId(requireContext()))
        drawable?.setBounds(0, 0, 60, 60)
        tvCountrySelect.setCompoundDrawables(
            drawable,
            null,
            requireActivity().getDrawable(co.yap.yapcore.R.drawable.iv_drown_down),
            null
        )

    }

    private fun isDefaultCurrencyExist(): Boolean {
        return null == viewModel.state.selectedCountry?.getCurrencySM()
    }

    private fun moveToAddBeneficiary() {
        findNavController().navigate(R.id.action_selectCountryFragment_to_addBeneficiaryFragment)
    }

    private fun moveToTransferType() {
        findNavController().navigate(R.id.action_selectCountryFragment_to_transferTypeFragment)
    }

    private fun getCountryAdapter(): CountryAdapter? {
        if (countryAdapter == null)
            countryAdapter =
                context?.let { CountryAdapter(it, viewModel.countries) }
        return countryAdapter
    }

    private fun skipCountrySelectionFragment(destinationId: Int) {
        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.selectCountryFragment, true) // starting destination skiped
            .build()

        findNavController().navigate(
            destinationId,
            null,
            navOptions
        )
    }


    private fun setupCountriesList() {
        val countries: java.util.ArrayList<Country> = SessionManager.getCountries()
        this.fragmentManager?.let {
            val coreBottomSheet = CoreBottomSheet(
                object :
                    OnItemClickListener {
                    override fun onItemClick(view: View, data: Any, pos: Int) {
                    }
                },
                bottomSheetItems = getCountries(countries).toMutableList(),
                headingLabel = "Change home country",
                viewType = Constants.VIEW_WITH_FLAG
            )
            coreBottomSheet.show(it, "")
        }
    }

    private fun getCountries(countries: java.util.ArrayList<Country>): java.util.ArrayList<Country> {
        countries.filter { it.isoCountryCode2Digit != "AE" }.forEach {
            it.subTitle = it.getName()
            it.sheetImage = CurrencyUtils.getFlagDrawable(
                requireContext(),
                it.isoCountryCode2Digit.toString()
            )
        }
        return countries
    }
}