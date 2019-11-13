package co.yap.modules.yapit.sendmoney.addbeneficiary.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.BR
import co.yap.R
import co.yap.modules.yapit.sendmoney.adapters.CountryAdapter
import co.yap.modules.yapit.sendmoney.addbeneficiary.interfaces.ISelectCountry
import co.yap.modules.yapit.sendmoney.addbeneficiary.viewmodels.SelectCountryViewModel
import co.yap.modules.yapit.sendmoney.fragments.SendMoneyBaseFragment
import co.yap.countryutils.country.Country
import co.yap.modules.kyc.fragments.AddressSelectionFragmentDirections
import kotlinx.android.synthetic.main.fragment_select_country.*


class SelectCountryFragment : SendMoneyBaseFragment<ISelectCountry.ViewModel>(),
    ISelectCountry.View {

    private var countryAdapter: CountryAdapter? = null
    private var countryArrayAdapter: ArrayAdapter<Country>? = null

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_select_country

    override val viewModel: ISelectCountry.ViewModel
        get() = ViewModelProviders.of(this).get(SelectCountryViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.clickEvent.observe(this, Observer {
            when (it) {
                R.id.nextButton ->
                    findNavController().navigate(R.id.action_selectCountryFragment_to_addBeneficiaryFragment)
            }
        })

        viewModel.populateSpinnerData.observe(this, Observer {
//              setUpSpinner()


//            getCountryAdapter()
            countriesSpinner.setAdapter(getCountryAdapter())

//            }
        })
    }

    override fun onPause() {
        viewModel.clickEvent.removeObservers(this)
        super.onPause()

    }

    override fun onResume() {
        super.onResume()

//        viewModel.clickEvent.observe(this, Observer {
//            when (it) {
//
//            }
//        })

    }

    override fun onBackPressed(): Boolean {

        return super.onBackPressed()
    }

    fun renderSpinner() {
//

//        getCountryAdapter().onItemClickListener({ view, pos ->
//            countriesSpinner.setSelection(pos.toInt())
//            viewModel.onCountrySelected(pos)
//        })

//        getCountryAdapter().onItemClickListener()
        // subscribe for updates
//        viewModel.getState().getCountriesLoadObservable()
//            .observe(this, { b -> getCountryAdapter().notifyDataSetChanged() })

//        mViewDataBinding.btnNext.setOnClickListener({ v -> viewModel.handlePressOnNextButton() })
//        view.findViewById(R.id.spinnerOverlay)
//            .setOnClickListener({ v -> countriesSpinner.performClick() })
//
//
    }

    fun getCountryAdapter(): CountryAdapter {
        if (countryAdapter == null)
            countryAdapter =
                context?.let { viewModel.countries?.let { it1 ->
                    CountryAdapter(it, R.layout.item_country,
                        it1
                    )
                } }
        return this!!.countryAdapter!!
    }

//    private var country_Spinner: Spinner? = null
//    private var countries: ArrayList<Country>? = null



     override fun setUpSpinner() {

//        countryArrayAdapter = ArrayAdapter<Country>(
//            activity!!.applicationContext,
//            R.layout.item_country,
//            viewModel.countries
//        )
//        countryArrayAdapter!!.setDropDownViewResource(R.layout.item_country)
//        countriesSpinner.setAdapter(countryArrayAdapter)
//        countriesSpinner.setOnItemSelectedListener(country_listener)


    }

    private val country_listener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
            if (position > 0) {
                val country = countriesSpinner.getItemAtPosition(position) as Country
                Log.d("SpinnerCountry", "onItemSelected: country: " + country.id)
//                val tempStates = ArrayList<State>()

//                tempStates.add(State(0, Country(0, "Choose a Country"), "Choose a State"))

            }
        }

        override fun onNothingSelected(parent: AdapterView<*>) {

        }
    }


//    private fun createLists() {
//        val country0 = Country(0, "Choose a Country")
//        val country1 = Country(1, "Country1")
//        val country2 = Country(2, "Country2")
//
//        countries.add(Country(0, "Choose a Country"))
//        countries.add(Country(1, "Country1"))
//        countries.add(Country(2, "Country2"))
//
//        //
//    }


}