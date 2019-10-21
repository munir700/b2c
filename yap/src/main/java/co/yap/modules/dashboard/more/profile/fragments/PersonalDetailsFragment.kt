package co.yap.modules.dashboard.more.profile.fragments

import android.Manifest
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.annotation.NonNull
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.more.activities.MoreActivity
import co.yap.modules.dashboard.more.activities.MoreActivity.Companion.showExpiredIcon
import co.yap.modules.dashboard.more.fragments.MoreBaseFragment
import co.yap.modules.dashboard.more.profile.intefaces.IPersonalDetail
import co.yap.modules.dashboard.more.profile.viewmodels.PersonalDetailsViewModel
import co.yap.yapcore.helpers.PermissionHelper
import co.yap.yapcore.managers.MyUserManager
import kotlinx.android.synthetic.main.fragment_personal_detail.*


class PersonalDetailsFragment : MoreBaseFragment<IPersonalDetail.ViewModel>(),
    IPersonalDetail.View {
    companion object {
        var checkMore: Boolean = false
        var checkScanned: Boolean = false

    }

    lateinit var permissionHelper: PermissionHelper
    var changeAddress: Boolean = false

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_personal_detail

    override val viewModel: IPersonalDetail.ViewModel
        get() = ViewModelProviders.of(this).get(PersonalDetailsViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val showExpiredIcon =
//            arguments?.let { PersonalDetailsFragmentArgs.fromBundle(it).showExpired }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (context as MoreActivity).visibleToolbar()

//        viewModel.state.errorVisibility =
//            arguments?.let { PersonalDetailsFragmentArgs.fromBundle(it).showExpired } as Boolean


        viewModel.state.errorVisibility = showExpiredIcon
        if (MoreActivity.isDocumentRequired) {

        }

        /* if (MyUserManager.user!!.documentInformation == null && viewModel.state.errorVisibility) {
             cvCard.visibility = VISIBLE
         } else {
             cvCard.visibility = GONE
         }*/

    }

    override fun onResume() {
        super.onResume()
        viewModel.state.errorVisibility = showExpiredIcon
        viewModel.toggleToolBar(true)

        viewModel.clickEvent.observe(this, Observer {
            when (it) {

                R.id.tvEditPhoneNumber -> {
                    findNavController().navigate(R.id.action_personalDetailsFragment_to_change_phone_number_navigation)
                }

                R.id.tvEditEmail -> {
                    findNavController().navigate(R.id.action_personalDetailsFragment_to_change_email_navigation)
                }

                R.id.tvEditAddress -> {
                    viewModel.toggleToolBar(true)

                    changeAddress = true
                    val action =
                        PersonalDetailsFragmentDirections.actionPersonalDetailsFragmentToAddressSelectionFragment(
                            false, false, true
                        )
                    findNavController().navigate(action)

                }

                R.id.cvCard -> {
                    if (viewModel.state.errorVisibility) {
                        proceedWithPermissions()
                    }

                }

                viewModel.UPDATE_ADDRESS_UI -> {
                    toggleAddressVisiblity()
                }
            }
        })

        toggleAddressVisiblity()
    }

    private fun proceedWithPermissions() {
        permissionHelper = PermissionHelper(
            this, arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.ACCESS_FINE_LOCATION
            ), 100
        )
        if (permissionHelper.hasPermission()) {

            val action =
                PersonalDetailsFragmentDirections.actionPersonalDetailsFragmentToDocumentsDashboardActivity(
                    viewModel.state.fullName, true
                )
            findNavController().navigate(action)
        } else {
            permissionHelper.request(object :
                PermissionHelper.PermissionCallback {
                override fun onPermissionGranted() {
                    val action =
                        PersonalDetailsFragmentDirections.actionPersonalDetailsFragmentToDocumentsDashboardActivity(
                            viewModel.state.fullName, true
                        )
                    findNavController().navigate(action)
                }

                override fun onIndividualPermissionGranted(grantedPermission: Array<String>) {
                    showToast("All permissions needed to proceed")
                }

                override fun onPermissionDenied() {
                    showToast("Can't proceed with permissions")
                }

                override fun onPermissionDeniedBySystem() {
                    permissionHelper.openAppDetailsActivity()
                }
            })
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, @NonNull permissions: Array<String>, @NonNull grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (permissionHelper != null) {
            permissionHelper!!.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    private fun toggleAddressVisiblity() {
        if (MyUserManager.userAddress == null) {
            llAddress.visibility = GONE
        } else {
            llAddress.visibility = VISIBLE
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.clickEvent.removeObservers(this)
        if (changeAddress) {
            viewModel.toggleToolBar(true)
            viewModel.updateToolBarText("")

            changeAddress = true
        }
    }

    override fun onDestroy() {
        viewModel.clickEvent.removeObservers(this)
        super.onDestroy()
        if (changeAddress) {
            viewModel.toggleToolBar(true)
            changeAddress = true
        }

    }
}