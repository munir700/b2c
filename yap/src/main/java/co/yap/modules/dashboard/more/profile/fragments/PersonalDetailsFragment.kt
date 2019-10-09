package co.yap.modules.dashboard.more.profile.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.more.activities.MoreActivity
import co.yap.modules.dashboard.more.fragments.MoreBaseFragment
import co.yap.modules.dashboard.more.profile.intefaces.IPersonalDetail
import co.yap.modules.dashboard.more.profile.viewmodels.PersonalDetailsViewModel
import co.yap.modules.kyc.activities.DocumentsDashboardActivity
 import com.digitify.identityscanner.modules.docscanner.activities.IdentityScannerActivity
import com.digitify.identityscanner.modules.docscanner.enums.DocumentType

private const val SCAN_EID_CAM = 12

class PersonalDetailsFragment : MoreBaseFragment<IPersonalDetail.ViewModel>(),
    IPersonalDetail.View {
companion object{
    var checkMore:Boolean=false

}


    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_personal_detail

    override val viewModel: IPersonalDetail.ViewModel
        get() = ViewModelProviders.of(this).get(PersonalDetailsViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val isFromBlockCardsScreen =
            arguments?.let { PersonalDetailsFragmentArgs.fromBundle(it).showExpired }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (context as MoreActivity).visibleToolbar()

        viewModel.state.errorVisibility =
            arguments?.let { PersonalDetailsFragmentArgs.fromBundle(it).showExpired } as Boolean

         viewModel.clickEvent.observe(this, Observer {
            when (it) {

                R.id.tvEditPhoneNumber -> {
                    findNavController().navigate(R.id.action_personalDetailsFragment_to_change_phone_number_navigation)
                }

                R.id.tvEditEmail -> {
                    findNavController().navigate(R.id.action_personalDetailsFragment_to_change_email_navigation)
                }

                R.id.tvEditAddress -> {

                }

                R.id.cvCard -> {
                    if (viewModel.state.errorVisibility) {

//                        openCardScanner()

                        val action =
                            PersonalDetailsFragmentDirections.actionPersonalDetailsFragmentToDocumentsDashboardActivity(
                                viewModel.state.fullName, true
                            )
                        findNavController().navigate(action)
                    }

                }
            }
        })
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SCAN_EID_CAM && resultCode == Activity.RESULT_OK) {
//            checkMore = DocumentsDashboardActivity.isFromMoreSection
            data?.let {
                viewModel.onEIDScanningComplete(it.getParcelableExtra(IdentityScannerActivity.SCAN_RESULT))
            }
        }
    }

    private fun openCardScanner() {
//        checkMore = DocumentsDashboardActivity.isFromMoreSection

        startActivityForResult(
            IdentityScannerActivity.getLaunchIntent(
                requireContext(),
                DocumentType.EID,
                IdentityScannerActivity.SCAN_FROM_CAMERA
            ),
            SCAN_EID_CAM
        )
    }
    override fun onPause() {
        super.onPause()
        viewModel.clickEvent.removeObservers(this)
    }

    override fun onDestroy() {
        viewModel.clickEvent.removeObservers(this)
        super.onDestroy()
    }
}