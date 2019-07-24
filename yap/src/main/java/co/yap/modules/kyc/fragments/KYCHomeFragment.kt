package co.yap.modules.kyc.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.R
import co.yap.modules.kyc.interfaces.IKYCHome
import co.yap.modules.kyc.viewmodels.KYCHomeViewModel
import co.yap.yapcore.BR
import co.yap.yapcore.BaseBindingFragment
import com.digitify.identityscanner.modules.docscanner.activities.IdentityScannerActivity
import com.digitify.identityscanner.modules.docscanner.enums.DocumentType
import com.digitify.identityscanner.modules.docscanner.models.IdentityScannerResult

private const val SCAN_EID_CAM = 12

class KYCHomeFragment : BaseBindingFragment<IKYCHome.ViewModel>(), IKYCHome.View {

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_kyc_home

    override val viewModel: IKYCHome.ViewModel
        get() = ViewModelProviders.of(this).get(KYCHomeViewModel::class.java)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.clickListener.observe(this, Observer {
            when (it) {
                R.id.cvCard -> openCardScanner()
                R.id.btnNext -> {
                }
                R.id.tvSkip -> {
                }
            }
        })
    }

    override fun onDestroyView() {
        viewModel.clickListener.removeObservers(this)
        super.onDestroyView()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SCAN_EID_CAM && resultCode == Activity.RESULT_OK) {
            data?.let {
                viewModel.onEIDScanningComplete(it.getParcelableExtra(IdentityScannerActivity.SCAN_RESULT))
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
            SCAN_EID_CAM
        )
    }

}