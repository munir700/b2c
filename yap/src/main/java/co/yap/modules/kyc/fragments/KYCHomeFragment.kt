package co.yap.modules.kyc.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.databinding.Observable
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.R
import co.yap.modules.kyc.activities.DocumentsDashboardActivity
import co.yap.modules.kyc.enums.DocScanStatus
import co.yap.modules.kyc.interfaces.IKYCHome
import co.yap.modules.kyc.viewmodels.KYCHomeViewModel
import com.digitify.identityscanner.docscanner.activities.IdentityScannerActivity
import com.digitify.identityscanner.docscanner.enums.DocumentType

class KYCHomeFragment : KYCChildFragment<IKYCHome.ViewModel>(), IKYCHome.View {

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_kyc_home

    override val viewModel: KYCHomeViewModel
        get() = ViewModelProviders.of(this).get(KYCHomeViewModel::class.java)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        showHideOptionsOnFlags()
        addObservers()
    }

    private fun addObservers() {
        viewModel.state.addOnPropertyChangedCallback(stateObserver)
        viewModel.clickEvent.observe(this, Observer {
            when (it) {
                R.id.cvCard -> openCardScanner()
                R.id.btnNext -> {
                    if (viewModel.parentViewModel?.allowSkip?.value == false) {
                        activity?.finish()
                    } else {
                        findNavController().navigate(R.id.action_KYCHomeFragment_to_AddressSelectionFragment)
                    }
                }
                R.id.tvSkip -> {
                    if (activity is DocumentsDashboardActivity)
                        (activity as DocumentsDashboardActivity).goToDashBoard()
                }
            }
        })
    }

    private fun showHideOptionsOnFlags() {
        viewModel.parentViewModel?.allowSkip?.value?.let {
            if (it) {
                viewModel.parentViewModel?.allowSkip?.value = !it
                findNavController().navigate(R.id.action_KYCHomeFragment_to_eidInfoReviewFragment)
            } else {
                viewModel.state.eidScanStatus = DocScanStatus.SCAN_PENDING
                viewModel.parentViewModel?.allowSkip?.value = it
            }
        }
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
        viewModel.state.removeOnPropertyChangedCallback(stateObserver)
        super.onDestroyView()
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IdentityScannerActivity.SCAN_EID_CAM && resultCode == Activity.RESULT_OK) {
            data?.let {
                viewModel.onEIDScanningComplete(it.getParcelableExtra(IdentityScannerActivity.SCAN_RESULT))
            }
        }
    }

    override fun onBackPressed(): Boolean {
        return true
    }

}