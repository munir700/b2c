package co.yap.modules.location.fragments

import android.graphics.drawable.Drawable
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
import co.yap.yapcore.BR
import co.yap.yapcore.R
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.databinding.FragmentPlaceOfBirthSelectionBinding
import co.yap.yapcore.enums.AccountStatus
import co.yap.yapcore.helpers.extentions.getColors
import co.yap.yapcore.helpers.extentions.launchBottomSheet
import co.yap.yapcore.interfaces.OnItemClickListener
import co.yap.yapcore.managers.SessionManager

class POBSelectionFragment : LocationChildFragment<IPOBSelection.ViewModel>(), IPOBSelection.View {
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_place_of_birth_selection
    override val viewModel: POBSelectionViewModel
        get() = ViewModelProviders.of(this).get(POBSelectionViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().window
            ?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        when (SessionManager.user?.notificationStatuses) {
            AccountStatus.BIRTH_INFO_COLLECTED.name -> {
                skipPOBSelectionFragment()
            }
            else -> {
                addObservers()
            }
        }
    }

    override fun addObservers() {
        viewModel.clickEvent.observe(this, clickObserver)
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
            R.id.bcountries -> {
                this.launchBottomSheet(
                    itemClickListener = itemListener,
                    label = "Select country",
                    viewType = Constants.VIEW_WITH_FLAG,
                    countriesList = viewModel.populateSpinnerData.value
                )
            }
        }
    }

    private val itemListener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            viewModel.state.selectedCountryIndex = pos
            val country: Country = data as Country
            setTextSelection(country)
        }
    }

    private fun setTextSelection(country: Country) {
        viewModel.state.selectedCountry = country
        getBinding().bcountries.text = country.getName()
        getBinding().bcountries.setTextColor(requireContext().getColors(R.color.colorPrimaryDark))
        val drawable: Drawable? =
            requireActivity().getDrawable(country.getFlagDrawableResId(requireContext()))
        drawable?.setBounds(0, 0, 60, 60)
        getBinding().bcountries.setCompoundDrawables(
            drawable,
            null,
            requireActivity().getDrawable(R.drawable.iv_drown_down),
            null
        )
    }

    override fun removeObservers() {
        viewModel.clickEvent.removeObserver(clickObserver)
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
