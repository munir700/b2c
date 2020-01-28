package co.yap.modules.dashboard.more.profile.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.more.main.activities.MoreActivity
import co.yap.modules.dashboard.more.main.activities.MoreActivity.Companion.showExpiredIcon
import co.yap.modules.dashboard.more.main.fragments.MoreBaseFragment
import co.yap.modules.dashboard.more.profile.intefaces.IPersonalDetail
import co.yap.modules.dashboard.more.profile.viewmodels.PersonalDetailsViewModel
import co.yap.modules.dummy.ActivityNavigator
import co.yap.modules.dummy.NavigatorProvider
import co.yap.modules.kyc.activities.DocumentsDashboardActivity
import co.yap.modules.location.activities.LocationSelectionActivity
import co.yap.networking.cards.requestdtos.UpdateAddressRequest
import co.yap.networking.cards.responsedtos.Address
import co.yap.translation.Strings
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.constants.Constants.ADDRESS
import co.yap.yapcore.constants.RequestCodes
import co.yap.yapcore.helpers.extentions.launchActivity
import co.yap.yapcore.helpers.extentions.preventTakeScreenShot
import co.yap.yapcore.managers.MyUserManager
import kotlinx.android.synthetic.main.fragment_personal_detail.*


class PersonalDetailsFragment : MoreBaseFragment<IPersonalDetail.ViewModel>(),
    IPersonalDetail.View {

    private var changeAddress: Boolean = false
    private lateinit var mNavigator: ActivityNavigator

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_personal_detail

    override val viewModel: IPersonalDetail.ViewModel
        get() = ViewModelProviders.of(this).get(PersonalDetailsViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.onUpdateAddressSuccess.observe(this, onAddressSuccess)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (context is MoreActivity)
            (context as MoreActivity).visibleToolbar()
        viewModel.state.errorVisibility = showExpiredIcon
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mNavigator = (activity?.applicationContext as NavigatorProvider).provideNavigator()
    }

    override fun onResume() {
        super.onResume()
        viewModel.state.errorVisibility = showExpiredIcon
        viewModel.toggleToolBar(true)

        viewModel.clickEvent.observe(this, Observer {
            when (it) {

                R.id.tvEditPhoneNumber -> {
                    mNavigator.startVerifyPassCodePresenterActivity(requireActivity()) { resultCode, data ->
                        if (resultCode == Activity.RESULT_OK) {
                            preventTakeScreenShot(false)
                            findNavController().navigate(R.id.action_personalDetailsFragment_to_change_phone_number_navigation)
                        }
                    }
                    //startActivityForResult(Intent(context, VerifyPassCodePresenterActivity::class.java),VerifyPassCodePresenterActivity.START_REQUEST_CODE)
                    // findNavController().navigate(R.id.action_personalDetailsFragment_to_change_phone_number_navigation)
                }

                R.id.tvEditEmail -> {
                    findNavController().navigate(R.id.action_personalDetailsFragment_to_change_email_navigation)
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
                    if (viewModel.state.errorVisibility) {
//                        val action =
//                            PersonalDetailsFragmentDirections.actionPersonalDetailsFragmentToDocumentsDashboardActivity(
//                                viewModel.state.fullName, true
//                            )
//
//                        findNavController().navigate(action)

                        launchActivity<DocumentsDashboardActivity>(requestCode = RequestCodes.REQUEST_KYC_DOCUMENTS){
                            putExtra(Constants.name, MyUserManager.user?.currentCustomer?.firstName.toString())
                            putExtra(Constants.data, false)
                        }

//                        startActivityForResult(
//                            DocumentsDashboardActivity.getIntent(
//                                requireContext(),
//                                MyUserManager.user?.currentCustomer?.firstName.toString(),
//                                true
//                            ), RequestCodes.REQUEST_KYC_DOCUMENTS
//                        )
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


    private fun toggleAddressVisibility() {
        if (MyUserManager.userAddress == null) {
            llAddress.visibility = GONE
        } else {
            llAddress.visibility = VISIBLE
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.clickEvent.removeObservers(this)
        viewModel.onUpdateAddressSuccess.removeObservers(this)
        if (changeAddress) {
            viewModel.toggleToolBar(true)
            viewModel.updateToolBarText("")

            changeAddress = true
        }
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
                    val address: Address? =
                        data?.getParcelableExtra(ADDRESS)
                    val isUpdatedAddress = data?.getBooleanExtra(Constants.ADDRESS_SUCCESS, false)
                    if (isUpdatedAddress == true) {
                        address?.let {
                            MyUserManager.userAddress = it
                            updateUserAddress(it)
                        }
                    }

                }
            }
        }
    }

    private fun updateUserAddress(address: Address) {
        val updateAddressRequest = UpdateAddressRequest(
            address.address1,
            address.address2,
            address.latitude.toString(),
            address.longitude.toString()
        )
        viewModel.requestUpdateAddress(updateAddressRequest)
    }
}