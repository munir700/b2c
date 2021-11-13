package co.yap.modules.kyc.amendments.missinginfo

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentMissinginfoBinding
import co.yap.modules.kyc.activities.DocumentsDashboardActivity
import co.yap.modules.location.activities.LocationSelectionActivity
import co.yap.networking.cards.responsedtos.Address
import co.yap.networking.customers.responsedtos.Section
import co.yap.translation.Strings
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.constants.RequestCodes
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
        getDataBindingView<FragmentMissinginfoBinding>().tvTitle.text =
            getString(Strings.screen_missing_info_title).format(
                SessionManager.user?.currentCustomer?.firstName
            )
    }

    private val onClickView = Observer<Int> {
        when (it) {
            R.id.btnGetStarted -> {
                if (viewModel.missingInfoMap.value?.get(Section.EID_INFO)?.isNotEmpty() == true) {
                    launchActivity<DocumentsDashboardActivity>(requestCode = RequestCodes.REQUEST_KYC_DOCUMENTS) {
                        putExtra(
                            Constants.name,
                            SessionManager.user?.currentCustomer?.firstName.toString()
                        )
                        putExtra(Constants.data, false)
                        putExtra(
                            Constants.KYC_AMENDMENT_MAP,
                            viewModel.missingInfoMap.value
                        )
                    }
                } else {
                    startActivity(
                        LocationSelectionActivity.newIntent(
                            context = requireContext(),
                            address = SessionManager.userAddress ?: Address(),
                            headingTitle = getString(Strings.screen_meeting_location_display_text_add_new_address_title),
                            subHeadingTitle = getString(Strings.screen_meeting_location_display_text_subtitle),
                            onBoarding = true,
                            map = viewModel.missingInfoMap.value
                        )
                    )
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