package co.yap.modules.kyc.amendments.missinginfo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentMissinginfoBinding
import co.yap.modules.dashboard.main.activities.YapDashboardActivity
import co.yap.modules.kyc.activities.DocumentsDashboardActivity
import co.yap.modules.kyc.amendments.passportactivity.PassportActivity
import co.yap.modules.location.activities.LocationSelectionActivity
import co.yap.networking.cards.responsedtos.Address
import co.yap.networking.customers.responsedtos.AmendmentSection
import co.yap.translation.Strings
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.constants.RequestCodes
import co.yap.yapcore.helpers.extentions.launchActivity
import co.yap.yapcore.managers.SessionManager

class MissingInfoFragment :
    BaseBindingFragment<FragmentMissinginfoBinding, IMissingInfo.ViewModel>(), IMissingInfo.View {
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
                        launchActivity<DocumentsDashboardActivity>(requestCode = RequestCodes.REQUEST_KYC_DOCUMENTS) {
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
                    }
                    viewModel.missingInfoMap.value?.containsKey(AmendmentSection.BIRTH_INFO.value) == true || viewModel.missingInfoMap.value?.containsKey(
                        AmendmentSection.TAX_INFO.value
                    ) == true || viewModel.missingInfoMap.value?.containsKey(AmendmentSection.EMPLOYMENT_INFO.value) == true -> {
                        startActivityForResult(
                            LocationSelectionActivity.newIntent(
                                context = requireContext(),
                                address = SessionManager.userAddress ?: Address(),
                                headingTitle = getString(Strings.screen_meeting_location_display_text_add_new_address_title),
                                subHeadingTitle = getString(Strings.screen_meeting_location_display_text_subtitle),
                                onBoarding = true,
                                missingInfoMap = viewModel.missingInfoMap.value
                            ),
                            RequestCodes.REQUEST_KYC_DOCUMENTS
                        )
                    }
                    viewModel.missingInfoMap.value?.containsKey(AmendmentSection.PASSPORT_INFO.value) == true -> {
                        launchActivity<PassportActivity>(
                            requestCode = RequestCodes.REQUEST_KYC_DOCUMENTS,
                            options = bundleOf(Constants.KYC_AMENDMENT_MAP to viewModel.missingInfoMap.value)
                        ) {
                            putExtra(
                                Constants.KYC_AMENDMENT_MAP,
                                viewModel.missingInfoMap.value
                            )
                        }
                    }

                }
            }
            R.id.tvDoItLater -> {
                goToDashboard()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RequestCodes.REQUEST_KYC_DOCUMENTS && resultCode == Activity.RESULT_OK && data?.getBooleanExtra(
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