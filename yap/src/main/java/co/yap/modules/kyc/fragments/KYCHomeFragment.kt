package co.yap.modules.kyc.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.Observable
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.R
import co.yap.modules.dashboard.more.profile.fragments.PersonalDetailsFragment.Companion.checkMore
import co.yap.modules.dashboard.more.profile.fragments.PersonalDetailsFragment.Companion.checkScanned
import co.yap.modules.kyc.activities.DocumentsDashboardActivity.Companion.isFromMoreSection
import co.yap.modules.kyc.enums.DocScanStatus
import co.yap.modules.kyc.interfaces.IKYCHome
import co.yap.modules.kyc.states.KYCHomeState
import co.yap.modules.kyc.viewmodels.KYCHomeViewModel
import co.yap.yapcore.BR
import com.digitify.identityscanner.docscanner.activities.IdentityScannerActivity
import com.digitify.identityscanner.docscanner.enums.DocumentType
import kotlinx.android.synthetic.main.fragment_kyc_home.*

class KYCHomeFragment : KYCChildFragment<IKYCHome.ViewModel>(), IKYCHome.View {

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_kyc_home

    override val viewModel: IKYCHome.ViewModel
        get() = ViewModelProviders.of(this).get(KYCHomeViewModel::class.java)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (isFromMoreSection) {
            tvSkip.visibility = View.GONE

            checkMore = true
            findNavController().navigate(R.id.action_KYCHomeFragment_to_eidInfoReviewFragment)

        } else {
            //todo need to verify that code
            //IdentityScannerActivity.CLOSE_SCANNER = false
            tvSkip.visibility = View.VISIBLE
        }
        if (checkMore && checkScanned) {
            activity!!.finish()
        }

        (viewModel.state as KYCHomeState).addOnPropertyChangedCallback(stateObserver)
        viewModel.clickEvent.observe(this, Observer {
            when (it) {
                R.id.cvCard -> openCardScanner()
                R.id.btnNext -> {
                    if (isFromMoreSection) {
                        activity!!.finish()
                    } else {
                        findNavController().navigate(R.id.action_KYCHomeFragment_to_AddressSelectionFragment)
                    }
                }
                R.id.tvSkip -> {
                    findNavController().navigate(R.id.action_goto_DashboardActivity)
                    activity?.finish()
                }
            }
        })

    }

    private val stateObserver = object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
            when (propertyId) {
                BR.eidScanStatus -> {
                    if (viewModel.state.eidScanStatus === DocScanStatus.SCAN_COMPLETED) {
                        findNavController().navigate(R.id.action_KYCHomeFragment_to_eidInfoReviewFragment)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        viewModel.clickEvent.removeObservers(this)
        (viewModel.state as KYCHomeState).removeOnPropertyChangedCallback(stateObserver)
        super.onDestroyView()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IdentityScannerActivity.SCAN_EID_CAM && resultCode == Activity.RESULT_OK) {
            data?.let {
                    viewModel.onEIDScanningComplete(it.getParcelableExtra(IdentityScannerActivity.SCAN_RESULT))
                    checkScanned = true

            }
        }
    }

    private fun openCardScanner() {
        startActivityForResult(
            IdentityScannerActivity.getLaunchIntent(
                requireContext(),
                DocumentType.EID,
                IdentityScannerActivity.SCAN_FROM_CAMERA
            ),
            IdentityScannerActivity.SCAN_EID_CAM
        )
    }

    override fun onResume() {
        super.onResume()
        if (checkMore) {
            activity!!.finish()
        }
    }


    override fun onBackPressed(): Boolean {
        return true
    }

}