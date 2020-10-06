package co.yap.modules.location.fragments

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import co.yap.countryutils.country.Country
import co.yap.modules.location.interfaces.IPOBSelection
import co.yap.modules.location.viewmodels.POBSelectionViewModel
import co.yap.widgets.spinneradapter.searchable.IStatusListener
import co.yap.widgets.spinneradapter.searchable.OnItemSelectedListener
import co.yap.widgets.spinneradapter.searchable.SimpleListAdapter
import co.yap.yapcore.BR
import co.yap.yapcore.R
import co.yap.yapcore.databinding.FragmentPlaceOfBirthSelectionBinding
import co.yap.yapcore.enums.AccountStatus
import co.yap.yapcore.helpers.extentions.toast
import co.yap.yapcore.interfaces.OnItemClickListener
import co.yap.yapcore.managers.MyUserManager
import kotlinx.android.synthetic.main.fragment_place_of_birth_selection.*

class POBSelectionFragment : LocationChildFragment<IPOBSelection.ViewModel>(), IPOBSelection.View {
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_place_of_birth_selection
    override val viewModel: POBSelectionViewModel
        get() = ViewModelProviders.of(this).get(POBSelectionViewModel::class.java)
    private lateinit var mSimpleListAdapter: SimpleListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        when (MyUserManager.user?.notificationStatuses) {
            AccountStatus.BIRTH_INFO_COLLECTED.name -> {
                skipPOBSelectionFragment()
            }
            else -> {
                addObservers()
            }
        }

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mSimpleListAdapter = SimpleListAdapter(requireContext(), getSpinnerList())
        bspinner?.setAdapter(mSimpleListAdapter)
        bspinner?.setOnItemSelectedListener(mOnItemSelectedListener)
        bspinner?.setStatusListener(object : IStatusListener
        {
            override fun spinnerIsOpening() {
            }

            override fun spinnerIsClosing() {
            }
        })
    }
    private var mOnItemSelectedListener: OnItemSelectedListener = object : OnItemSelectedListener {
        override fun onItemSelected(view: View?, position: Int, id: Long) {
            toast("Item on position " + position + " : " + mSimpleListAdapter?.getItem(position) + " Selected")
        }

        override fun onNothingSelected() {
            toast("Nothing Selected")
        }
    }
    private fun getSpinnerList(): ArrayList<String>? {
        val list: ArrayList<String> = ArrayList()
        list.add("Brigida Kurz")
        list.add("Tracy Mckim")
        list.add("Iesha Davids")
        list.add("Ozella Provenza")
        list.add("Florentina Carriere")
        list.add("Geri Eiler")
        list.add("Tammara Belgrave")
        list.add("Ashton Ridinger")
        list.add("Jodee Dawkins")
        list.add("Florine Cruzan")
        list.add("Latia Stead")
        list.add("Kai Urbain")
        list.add("Liza Chi")
        list.add("Clayton Laprade")
        list.add("Wilfredo Mooney")
        list.add("Roseline Cain")
        list.add("Chadwick Gauna")
        list.add("Carmela Bourn")
        list.add("Valeri Dedios")
        list.add("Calista Mcneese")
        list.add("Willard Cuccia")
        list.add("Ngan Blakey")
        list.add("Reina Medlen")
        list.add("Fabian Steenbergen")
        list.add("Edmond Pine")
        list.add("Teri Quesada")
        list.add("Vernetta Fulgham")
        list.add("Winnifred Kiefer")
        list.add("Chiquita Lichty")
        list.add("Elna Stiltner")
        return list
    }
    override fun addObservers() {
        viewModel.clickEvent.observe(this, clickObserver)
        viewModel.populateSpinnerData.observe(this, countriesListObserver)
    }

    private val clickObserver = Observer<Int> {
        when (it) {
            R.id.nextButton -> {
                viewModel.saveDOBInfo {
                    findNavController().navigate(R.id.action_POBSelectionFragment_to_taxInfoFragment)
                }
            }
            R.id.ivBackBtn -> {
                activity?.onBackPressed()
            }
        }
    }

    private val countriesListObserver = Observer<List<Country>> {
        getBinding().spinner.setItemSelectedListener(selectedItemListener)
        getBinding().spinner.setAdapter(it)
        if (viewModel.state.selectedCountry != null) {
            getBinding().spinner.setSelectedItem(
                viewModel.parentViewModel?.countries?.indexOf(
                    viewModel.state.selectedCountry ?: Country()
                ) ?: 0
            )
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

    private fun skipPOBSelectionFragment() {
        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.POBSelectionFragment, true) // starting destination skipped
            .build()

        findNavController().navigate(
            R.id.action_POBSelectionFragment_to_taxInfoFragment,
            null,
            navOptions
        )
    }

    override fun onBackPressed(): Boolean {
        return false
    }
}
