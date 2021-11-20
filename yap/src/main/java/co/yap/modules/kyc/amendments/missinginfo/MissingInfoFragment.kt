package co.yap.modules.kyc.amendments.missinginfo

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.main.activities.YapDashboardActivity
import co.yap.modules.kyc.activities.DocumentsDashboardActivity
import co.yap.modules.location.activities.LocationSelectionActivity
import co.yap.networking.cards.responsedtos.Address
import co.yap.networking.customers.responsedtos.AmendmentSection
import co.yap.translation.Strings
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.constants.RequestCodes
import co.yap.yapcore.enums.FeatureSet
import co.yap.yapcore.helpers.extentions.launchActivity
import co.yap.yapcore.managers.SessionManager

class MissingInfoFragment : BaseBindingFragment<IMissingInfo.ViewModel>(), IMissingInfo.View {
    override fun getBindingVariable() = BR.viewModel
    override fun getLayoutId() = R.layout.fragment_missinginfo

    override val viewModel: IMissingInfo.ViewModel
        get() = ViewModelProvider(this).get(MissingInfoFragmentViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.adapter.set(MissingInfoAdapter(mutableListOf(), null))
        viewModel.onClickEvent.observe(viewLifecycleOwner, onClickView)
    }

    private val onClickView = Observer<Int> {
        when (it) {
            R.id.btnGetStarted -> {
                when {
                    viewModel.missingInfoMap.value?.isEmpty() == true -> {
                        launchActivity<YapDashboardActivity>()
                        requireActivity().finish()
                    }
                    viewModel.missingInfoMap.value?.containsKey(AmendmentSection.EID_INFO.value) == true -> {
                        launchActivity<DocumentsDashboardActivity>(
                            requestCode = RequestCodes.REQUEST_KYC_DOCUMENTS,
                            type = FeatureSet.UPDATE_EID
                        ) {
                            putExtra(
                                Constants.name,
                                SessionManager.user?.currentCustomer?.firstName.toString()
                            )
                            putExtra(Constants.data, false) // TODO make is true for real and false for mocking
                            putExtra(
                                Constants.KYC_AMENDMENT_MAP,
                                viewModel.missingInfoMap.value
                            )
                        }
                    }
                    else -> {
                        startActivity(
                            LocationSelectionActivity.newIntent(
                                context = requireContext(),
                                address = SessionManager.userAddress ?: Address(),
                                headingTitle = getString(Strings.screen_meeting_location_display_text_add_new_address_title),
                                subHeadingTitle = getString(Strings.screen_meeting_location_display_text_subtitle),
                                onBoarding = true,
                                missingInfoMap = viewModel.missingInfoMap.value
                            )
                        )
                    }
                }
            }
            R.id.tvDoItLater -> {
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.onClickEvent.removeObserver(onClickView)
    }

}