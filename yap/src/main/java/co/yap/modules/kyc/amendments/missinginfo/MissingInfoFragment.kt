package co.yap.modules.kyc.amendments.missinginfo

import android.app.Activity
import android.content.Intent
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
import co.yap.yapcore.helpers.extentions.launchActivity
import co.yap.yapcore.helpers.extentions.launchActivityForActivityResult
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
                        goToDashboard()
                    }
                    viewModel.missingInfoMap.value?.containsKey(AmendmentSection.EID_INFO.value) == true -> {
                        // TODO Improve this for callback
                        val intent = Intent(context, DocumentsDashboardActivity::class.java).apply {
                            putExtra(
                                Constants.name,
                                SessionManager.user?.currentCustomer?.firstName.toString()
                            )
                            putExtra(
                                Constants.data,
                                true
                            )
                            putExtra(
                                Constants.KYC_AMENDMENT_MAP,
                                viewModel.missingInfoMap.value
                            )
                        }
                        startActivityForResult(intent, 1001)

                        /*launchActivityForActivityResult<DocumentsDashboardActivity> {
                            putExtra(
                                Constants.name,
                                SessionManager.user?.currentCustomer?.firstName.toString()
                            )
                            putExtra(
                                Constants.data,
                                true
                            ) // TODO make is true for real and false for mocking
                            putExtra(
                                Constants.KYC_AMENDMENT_MAP,
                                viewModel.missingInfoMap.value
                            )
                        }*/
                    }
                    else -> {
                        startActivityForResult(
                            LocationSelectionActivity.newIntent(
                                context = requireContext(),
                                address = SessionManager.userAddress ?: Address(),
                                headingTitle = getString(Strings.screen_meeting_location_display_text_add_new_address_title),
                                subHeadingTitle = getString(Strings.screen_meeting_location_display_text_subtitle),
                                onBoarding = true,
                                missingInfoMap = viewModel.missingInfoMap.value
                            ),
                            1000
                        )
                    }
                }
            }
            R.id.tvDoItLater -> {
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && data?.getBooleanExtra(
                Constants.KYC_AMENDMENT_SUCCESS, false
            ) == true
        ) {
            // Last missing section got updated, go to dashboard
            if (viewModel.missingInfoMap.value?.size == 1) {
                goToDashboard()
            } else {
                // Reload Data
                viewModel.getMissingInfoItems()
            }
        }
    }

    private fun goToDashboard() {
        launchActivity<YapDashboardActivity>()
        requireActivity().finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.onClickEvent.removeObserver(onClickView)
    }

}