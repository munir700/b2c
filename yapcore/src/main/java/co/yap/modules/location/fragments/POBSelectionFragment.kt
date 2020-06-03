package co.yap.modules.location.fragments

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.modules.location.POBCountry
import co.yap.modules.location.POBCountryAdapter
import co.yap.modules.location.interfaces.IPOBSelection
import co.yap.modules.location.viewmodels.POBSelectionViewModel
import co.yap.translation.Strings
import co.yap.yapcore.BR
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.R
import co.yap.yapcore.databinding.FragmentPlaceOfBirthSelectionBinding

class POBSelectionFragment : BaseBindingFragment<IPOBSelection.ViewModel>(), IPOBSelection.View {
    private var countryAdapter: POBCountryAdapter? = null

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_place_of_birth_selection
    override val viewModel: IPOBSelection.ViewModel
        get() = ViewModelProviders.of(this).get(POBSelectionViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addObservers()
    }

    override fun addObservers() {
        viewModel.clickEvent.observe(this, clickObserver)
        viewModel.populateSpinnerData.observe(this, countriesListObserver)
    }

    private val clickObserver = Observer<Int> {}

    private val countriesListObserver = Observer<List<POBCountry>> {
        getBinding().countriesSpinner.adapter = getCountryAdapter()
        getBinding().countriesSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    viewModel.selectedCountry = viewModel.countries[position]
                    viewModel.state.valid =
                        !viewModel.selectedCountry?.equals(getString(Strings.screen_place_of_birth_display_text_select_country))!!
                }
            }
    }

    private fun getCountryAdapter(): POBCountryAdapter? {
        if (countryAdapter == null)
            countryAdapter =
                context?.let { POBCountryAdapter(it, viewModel.countries) }
        return countryAdapter
    }

    override fun removeObservers() {
        viewModel.clickEvent.removeObserver(clickObserver)
        viewModel.populateSpinnerData.removeObserver(countriesListObserver)
    }

    override fun onDestroy() {
        super.onDestroy()
        removeObservers()
    }

    override fun getBinding(): FragmentPlaceOfBirthSelectionBinding {
        return (viewDataBinding as FragmentPlaceOfBirthSelectionBinding)
    }

}
