package co.yap.modules.dashboard.more.profile.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.VISIBLE
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentPersonalDetailBinding
import co.yap.modules.dashboard.main.activities.YapDashboardActivity
import co.yap.modules.dashboard.more.main.activities.MoreActivity
import co.yap.modules.dashboard.more.main.fragments.MoreBaseFragment
import co.yap.modules.dashboard.more.profile.intefaces.IPersonalDetail
import co.yap.modules.dashboard.more.profile.viewmodels.PersonalDetailsViewModel
import co.yap.modules.dummy.ActivityNavigator
import co.yap.modules.dummy.NavigatorProvider
import co.yap.modules.kyc.activities.DocumentsDashboardActivity
import co.yap.modules.location.activities.LocationSelectionActivity
import co.yap.modules.others.fragmentpresenter.activities.FragmentPresenterActivity
import co.yap.networking.cards.responsedtos.Address
import co.yap.translation.Strings
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.constants.Constants.ADDRESS
import co.yap.yapcore.constants.RequestCodes
import co.yap.yapcore.enums.EIDStatus
import co.yap.yapcore.enums.PartnerBankStatus
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.extentions.ExtraType
import co.yap.yapcore.helpers.extentions.getValue
import co.yap.yapcore.helpers.extentions.launchActivity
import co.yap.yapcore.helpers.extentions.preventTakeScreenShot
import co.yap.yapcore.managers.MyUserManager


class PersonalDetailsFragment : MoreBaseFragment<IPersonalDetail.ViewModel>(),
    IPersonalDetail.View {

    private var changeAddress: Boolean = false
    private lateinit var mNavigator: ActivityNavigator

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_personal_detail

    override val viewModel: PersonalDetailsViewModel
        get() = ViewModelProviders.of(this).get(PersonalDetailsViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.onUpdateAddressSuccess.observe(this, onAddressSuccess)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (context is MoreActivity)
            (context as MoreActivity).visibleToolbar()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mNavigator = (activity?.applicationContext as NavigatorProvider).provideNavigator()
    }

    override fun onResume() {
        super.onResume()
        viewModel.toggleToolBar(true)
        viewModel.orderCardSuccess.observe(this, onCardOrderSuccess)
        viewModel.clickEvent.observe(this, Observer {
            when (it) {
                R.id.tvEditPhoneNumber -> {
                    if (MyUserManager.user?.otpBlocked == true) {
                        showToast(Utils.getOtpBlockedMessage(requireContext()))
                    } else {
                        mNavigator.startVerifyPassCodePresenterActivity(
                            requireActivity(),
                            bundleOf(Constants.VERIFY_PASS_CODE_BTN_TEXT to getString(Strings.screen_verify_passcode_button_verify))
                        ) { resultCode, data ->
                            if (resultCode == Activity.RESULT_OK) {
                                preventTakeScreenShot(false)
                                findNavController().navigate(R.id.action_personalDetailsFragment_to_change_phone_number_navigation)
                            }
                        }
                    }
                }

                R.id.tvEditEmail -> {
                    if (MyUserManager.user?.otpBlocked == true) {
                        showToast(Utils.getOtpBlockedMessage(requireContext()))
                    } else {
                        viewModel.toggleToolBar(true)
                        viewModel.updateToolBarText("")
                        findNavController().navigate(R.id.action_personalDetailsFragment_to_change_email_navigation)
                    }
                }

                R.id.tvEditAddress -> {
                    viewModel.toggleToolBar(true)
                    changeAddress = true
                    startActivityForResult(
                        LocationSelectionActivity.newIntent(
                            context = requireContext(),
                            address = MyUserManager.userAddress ?: Address(),
                            headingTitle = getString(Strings.screen_meeting_location_display_text_add_new_address_title),
                            subHeadingTitle = getString(Strings.screen_meeting_location_display_text_subtitle)
                        ), RequestCodes.REQUEST_FOR_LOCATION
                    )

                }

                R.id.cvCard -> {
                    if (MyUserManager.user?.otpBlocked == true) {
                        if (MyUserManager.eidStatus == EIDStatus.NOT_SET &&
                            PartnerBankStatus.ACTIVATED.status != MyUserManager.user?.partnerBankStatus
                        ) {
                            launchActivity<DocumentsDashboardActivity>(requestCode = RequestCodes.REQUEST_KYC_DOCUMENTS) {
                                putExtra(
                                    Constants.name,
                                    MyUserManager.user?.currentCustomer?.firstName.toString()
                                )
                                putExtra(Constants.data, true)
                                putExtra("document", viewModel.parentViewModel?.document)
                            }
                        } else {
                            showToast(Utils.getOtpBlockedMessage(requireContext()))
                        }
                    } else {
                        if (canOpenEIDCard()) {
                            launchActivity<DocumentsDashboardActivity>(requestCode = RequestCodes.REQUEST_KYC_DOCUMENTS) {
                                putExtra(
                                    Constants.name,
                                    MyUserManager.user?.currentCustomer?.firstName.toString()
                                )
                                putExtra(Constants.data, true)
                                putExtra("document", viewModel.parentViewModel?.document)
                            }
                        }
                    }
                }

                viewModel.UPDATE_ADDRESS_UI -> {
                    toggleAddressVisibility()
                }
            }
        })

        viewModel.onUpdateAddressSuccess.observe(this, Observer {
            if (it) {
                val action =
                    PersonalDetailsFragmentDirections.actionPersonalDetailsFragmentToSuccessFragment(
                        getString(R.string.screen_address_success_display_text_sub_heading_update),
                        " "
                    )
                findNavController().navigate(action)
            }
        })

        toggleAddressVisibility()
    }

    private fun canOpenEIDCard(): Boolean {
        return when (MyUserManager.eidStatus) {
            EIDStatus.NOT_SET, EIDStatus.EXPIRED -> {
                true
            }
            EIDStatus.VALID -> MyUserManager.user?.partnerBankStatus.equals(PartnerBankStatus.ACTIVATED.status)
        }
    }

    private fun toggleAddressVisibility() {
        if (MyUserManager.userAddress == null) {
            getBinding().llAddress.visibility = View.GONE
        } else {
            getBinding().llAddress.visibility = VISIBLE
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.clickEvent.removeObservers(this)
        viewModel.onUpdateAddressSuccess.removeObservers(this)
        viewModel.orderCardSuccess.removeObserver(onCardOrderSuccess)
    }

    override fun onDestroy() {
        viewModel.clickEvent.removeObservers(this)
        viewModel.onUpdateAddressSuccess.removeObservers(this)
        super.onDestroy()
        if (changeAddress) {
            viewModel.toggleToolBar(true)
            changeAddress = true
        }
    }

    private val onCardOrderSuccess = Observer<Boolean> {
        if (it) {
            startActivityForResult(
                FragmentPresenterActivity.getIntent(
                    requireContext(),
                    Constants.MODE_MEETING_CONFORMATION,
                    null
                ), RequestCodes.REQUEST_MEETING_CONFIRMED
            )

        } else {

        }
    }

    private val onAddressSuccess = Observer<Boolean> {
        if (it) {
            val action =
                PersonalDetailsFragmentDirections.actionPersonalDetailsFragmentToSuccessFragment(
                    getString(R.string.screen_address_success_display_text_sub_heading_update),
                    " "
                )
            findNavController().navigate(action)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                RequestCodes.REQUEST_FOR_LOCATION -> {

                    val isUpdatedAddress = data?.getBooleanExtra(Constants.ADDRESS_SUCCESS, false)
                    if (isUpdatedAddress == true) {
                        val address: Address? = data.getParcelableExtra(ADDRESS)
                        address?.let {
                            MyUserManager.userAddress = it
                            updateUserAddress(it)
                        }
                    }
                }
                RequestCodes.REQUEST_KYC_DOCUMENTS -> handleKYCRequestResult(data)
                RequestCodes.REQUEST_LOCATION_FOR_KYC -> handleLocationRequestResult(data)
                RequestCodes.REQUEST_MEETING_CONFIRMED -> handleMeetingConfirmationRequest(data)

            }
        }
    }

    private fun handleKYCRequestResult(data: Intent?) {
        data?.let {
            val success =
                data.getValue(
                    Constants.result,
                    ExtraType.BOOLEAN.name
                ) as? Boolean
            val skipped =
                data.getValue(
                    Constants.skipped,
                    ExtraType.BOOLEAN.name
                ) as? Boolean

            success?.let {
                if (it) {
                    MyUserManager.eidStatus = EIDStatus.VALID
                    viewModel.setUpVerificationLayout()
                    startActivityForResult(
                        LocationSelectionActivity.newIntent(
                            context = requireContext(),
                            address = MyUserManager.userAddress ?: Address(),
                            headingTitle = getString(Strings.screen_meeting_location_display_text_add_new_address_title),
                            subHeadingTitle = getString(Strings.screen_meeting_location_display_text_subtitle),
                            onBoarding = true
                        ), RequestCodes.REQUEST_LOCATION_FOR_KYC
                    )
                } else {

                }
            }
        }
    }

    private fun handleLocationRequestResult(data: Intent?) {
        data?.let {
            val result = it.getBooleanExtra(Constants.ADDRESS_SUCCESS, false)
            if (result) {
                val address = it.getParcelableExtra<Address>(ADDRESS)
                MyUserManager.userAddress = address
                viewModel.requestOrderCard(address)
            }
        }
    }

    private fun handleMeetingConfirmationRequest(data: Intent?) {
        data?.let {
            //did'nt handle intent data for now
            startActivity(Intent(requireContext(), YapDashboardActivity::class.java))
            activity?.finish()
        }
    }

    private fun updateUserAddress(address: Address) {
        viewModel.requestUpdateAddress(address)
    }

    private fun getBinding(): FragmentPersonalDetailBinding {
        return (viewDataBinding as FragmentPersonalDetailBinding)
    }
}