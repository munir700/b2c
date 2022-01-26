package co.yap.modules.location.kyc_additional_info.birth_info

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import co.yap.countryutils.country.Country
import co.yap.modules.dummy.ActivityNavigator
import co.yap.modules.dummy.NavigatorProvider
import co.yap.modules.location.fragments.LocationChildFragment
import co.yap.translation.Strings
import co.yap.yapcore.BR
import co.yap.yapcore.R
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.databinding.FragmentPlaceOfBirthSelectionBinding
import co.yap.yapcore.firebase.FirebaseEvent
import co.yap.yapcore.firebase.trackEventWithScreenName
import co.yap.yapcore.helpers.extentions.launchBottomSheet
import co.yap.yapcore.interfaces.OnItemClickListener

class POBSelectionFragment : LocationChildFragment<IPOBSelection.ViewModel>(), IPOBSelection.View {
    private lateinit var mNavigator: ActivityNavigator
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_place_of_birth_selection
    override val viewModel: POBSelectionViewModel
        get() = ViewModelProvider(this).get(POBSelectionViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().window
            ?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        if (viewModel.canSkipFragment()) {
            skipPOBSelectionFragment()
        } else {
            addObservers()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getDataBindingView<FragmentPlaceOfBirthSelectionBinding>().lifecycleOwner = this
        viewModel.validator?.targetViewBinding =
            getDataBindingView<FragmentPlaceOfBirthSelectionBinding>()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mNavigator = (activity?.applicationContext as NavigatorProvider).provideNavigator()
    }

    override fun addObservers() {
        viewModel.clickEvent.observe(this, clickObserver)
        viewModel.state.dualNationalityOption.observe(this, Observer {
            getDataBindingView<FragmentPlaceOfBirthSelectionBinding>().optionsSpinner.setSelection(
                it
            )
        })
    }

    private val clickObserver = Observer<Int> {
        when (it) {
            R.id.nextButton -> {
                if (viewModel.state.selectedSecondCountry.get()?.isoCountryCode2Digit == "US") {
                    mNavigator.startDocumentDashboardActivity(
                        requireActivity()
                    )
                } else {
                    viewModel.saveDOBInfo {
                        trackEventWithScreenName(FirebaseEvent.BIRTH_LOCATION_SUBMIT)
                        if (viewModel.isFromAmendment()) {
                            navigateToAmendmentSuccess()
                        } else {
                            navigate(R.id.action_POBSelectionFragment_to_taxInfoFragment)
                        }
                    }
                }
            }
            R.id.tbBtnBack -> {
                activity?.onBackPressed()
            }
            R.id.bcountries -> {
                this.launchBottomSheet(
                    itemClickListener = selectCountryItemClickListener,
                    label = getString(Strings.screen_place_of_birth_display_text_select_country),
                    viewType = Constants.VIEW_WITH_FLAG,
                    countriesList = viewModel.parentViewModel?.countries
                )
            }
            R.id.bSecondcountry -> {
                this.launchBottomSheet(
                    itemClickListener = selectSecondCountryItemClickListener,
                    label = getString(Strings.screen_place_of_birth_display_text_add_second_country),
                    viewType = Constants.VIEW_WITH_FLAG,
                    countriesList = viewModel.populateSpinnerData.value
                )
            }
        }
    }

    private val selectCountryItemClickListener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            viewModel.state.selectedCountry.set(data as Country)
        }
    }
    private val selectSecondCountryItemClickListener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            viewModel.state.selectedSecondCountry.set(data as Country)
        }
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

    override fun removeObservers() {
        viewModel.clickEvent.removeObserver(clickObserver)
    }

    override fun onDestroy() {
        super.onDestroy()
        removeObservers()
    }

    private fun navigateToAmendmentSuccess() {
        viewModel.parentViewModel?.hideProgressToolbar?.value = true
        viewModel.parentViewModel?.amendmentMap?.let { amendmentMap ->
            val bundle = Bundle()
            bundle.putSerializable(
                Constants.CONFIRMATION_DESCRIPTION,
                Pair(
                    first = getString(if (amendmentMap.size == 1) R.string.screen_missing_info_confirmation_display_all_set_title else R.string.screen_missing_info_confirmation_display_step_step_completed_title),
                    second = getString(if (amendmentMap.size == 1) R.string.screen_missing_info_confirmation_display_all_set_description else R.string.screen_missing_info_confirmation_display_step_step_completed_description)
                )
            )
            bundle.putSerializable(
                Constants.KYC_AMENDMENT_MAP,
                viewModel.parentViewModel?.amendmentMap
            )
            navigate(
                R.id.action_POBSelectionFragment_to_missingInfoConfirmationFragment,
                bundle
            )
        }
    }
}
