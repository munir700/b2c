package co.yap.modules.location.fragments

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import co.yap.countryutils.country.Country
import co.yap.modules.location.CustomAutoCompleteAdapter
import co.yap.modules.location.interfaces.IPOBSelection
import co.yap.modules.location.viewmodels.POBSelectionViewModel
import co.yap.widgets.spinneradapter.searchable.IStatusListener
import co.yap.yapcore.BR
import co.yap.yapcore.R
import co.yap.yapcore.databinding.FragmentPlaceOfBirthSelectionBinding
import co.yap.yapcore.enums.AccountStatus
import co.yap.yapcore.helpers.extentions.afterTextChanged
import co.yap.yapcore.managers.MyUserManager
import java.util.*

class POBSelectionFragment : LocationChildFragment<IPOBSelection.ViewModel>(), IPOBSelection.View {
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_place_of_birth_selection
    override val viewModel: POBSelectionViewModel
        get() = ViewModelProviders.of(this).get(POBSelectionViewModel::class.java)

    private var mCustomAutoTextAdapter: CustomAutoCompleteAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().window
            ?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
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
        getBinding().bspinner.setStatusListener(object : IStatusListener {
            override fun spinnerIsOpening() {
            }

            override fun spinnerIsClosing() {
            }
        })
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
        setAutoCompleteText(it as ArrayList<Country>)
        if (viewModel.state.selectedCountry != null) {
            getBinding().bspinner.setSelectedItem(
                viewModel.parentViewModel?.countries?.indexOf(
                    viewModel.state.selectedCountry ?: Country()
                ) ?: 0
            )
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setAutoCompleteText(it: ArrayList<Country>) {
        mCustomAutoTextAdapter =
            CustomAutoCompleteAdapter(requireContext(), it)
        getBinding().bcountries.setAdapter(mCustomAutoTextAdapter)
        getBinding().bcountries.threshold = 0
        getBinding().bcountries.setOnTouchListener(touchListener)
        getBinding().bcountries.afterTextChanged { s ->
            if (s.length < 0)
                getBinding().bcountries.showDropDown()
            else if (s.isEmpty()) getBinding().bcountries.setCompoundDrawables(
                null,
                null,
                null,
                null
            )
        }
        getBinding().bcountries.onItemClickListener = itemClickListener
    }

    @SuppressLint("ClickableViewAccessibility")
    private val touchListener = View.OnTouchListener { _, _ ->
        getBinding().bcountries.showDropDown()
        false
    }

    private val itemClickListener =
        AdapterView.OnItemClickListener { adapter, _, position, _ ->
            val country: Country = adapter?.getItemAtPosition(position) as Country
            setTextSelection(country)
        }

    private fun setTextSelection(country: Country) {
        getBinding().bcountries.setText(country.getName())
        getBinding().bcountries.setSelection(country.getName().length)
        val drawable: Drawable? =
            requireActivity().getDrawable(country.getFlagDrawableResId(requireContext()))
        drawable?.setBounds(0, 0, 60, 60)
        getBinding().bcountries.setCompoundDrawables(
            drawable,
            null,
            null,
            requireActivity().getDrawable(R.drawable.iv_drown_down)
        )
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
