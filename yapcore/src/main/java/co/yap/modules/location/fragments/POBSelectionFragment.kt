package co.yap.modules.location.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.countryutils.country.Country
import co.yap.modules.location.interfaces.IPOBSelection
import co.yap.modules.location.viewmodels.POBSelectionViewModel
import co.yap.yapcore.BR
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.R
import co.yap.yapcore.databinding.FragmentPlaceOfBirthSelectionBinding
import co.yap.yapcore.interfaces.OnItemClickListener

class POBSelectionFragment : BaseBindingFragment<IPOBSelection.ViewModel>(), IPOBSelection.View {
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

    //    private val clickObserver = Observer<Int> {
//        when (it) {
//            R.id.nextButton -> {
//                startFragment(
//                    fragmentName = TaxInfoFragment::class.java.name, bundle = bundleOf(
//                        "countries" to viewModel.populateSpinnerData.value
//                    ), showToolBar = false
//                )
//            }
//        }
//    }
    private val clickObserver = Observer<Int> {
        when (it) {
            R.id.nextButton -> {
                findNavController().navigate(R.id.action_POBSelectionFragment_to_taxInfoFragment)
            }
        }
    }

    private val countriesListObserver = Observer<List<Country>> {
        getBinding().spinner.setItemSelectedListener(selectedItemListener)
        getBinding().spinner.setAdapter(it)
        if (viewModel.state.selectedCountry != null) {
            getBinding().spinner.setSelectedItem(viewModel.countries.indexOf(viewModel.state.selectedCountry!!))
        }
    }

    private val selectedItemListener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            if (data is Country) {
                viewModel.state.selectedCountry = data
            }
        }
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
