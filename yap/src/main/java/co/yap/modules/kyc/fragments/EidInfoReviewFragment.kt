package co.yap.modules.kyc.fragments

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.BR
import co.yap.R
import co.yap.modules.kyc.activities.DocumentsResponse
import co.yap.modules.kyc.viewmodels.EidInfoReviewViewModel
import co.yap.modules.onboarding.interfaces.IEidInfoReview
import co.yap.translation.Strings
import com.digitify.identityscanner.docscanner.activities.IdentityScannerActivity
import com.digitify.identityscanner.docscanner.enums.DocumentType
import kotlinx.android.synthetic.main.activity_eid_info_review.*


class EidInfoReviewFragment : KYCChildFragment<IEidInfoReview.ViewModel>(), IEidInfoReview.View {

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_eid_info_review

    override val viewModel: EidInfoReviewViewModel
        get() = ViewModelProviders.of(this).get(EidInfoReviewViewModel::class.java)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (viewModel.parentViewModel?.skipFirstScreen?.value == true) {
            openCardScanner()
            tbBtnBack.setOnClickListener {
                viewModel.parentViewModel?.finishKyc?.value = DocumentsResponse(false)
            }
        }
        addObservers()
    }

    private fun addObservers() {
        viewModel.clickEvent.observe(this, Observer {
            when (it) {
                viewModel.EVENT_ERROR_INVALID_EID -> showInvalidEidAlert()
                viewModel.EVENT_ERROR_EXPIRED_EID -> showExpiredEidAlert()
                viewModel.EVENT_ERROR_UNDER_AGE -> showUnderAgeAlert()
                viewModel.EVENT_ERROR_FROM_USA -> showUSACitizenAlert()
                viewModel.EVENT_RESCAN -> openCardScanner()
                viewModel.EVENT_NEXT -> {
                    viewModel.parentViewModel?.finishKyc?.value = DocumentsResponse(true)
                }

                viewModel.EVENT_ALREADY_USED_EID -> {
                    viewModel.parentViewModel?.finishKyc?.value = DocumentsResponse(false, "true")
                }

                viewModel.EVENT_NEXT_WITH_ERROR -> {
                    val action =
                        EidInfoReviewFragmentDirections.actionEidInfoReviewFragmentToInformationErrorFragment(
                            viewModel.sanctionedCountry
                        )
                    findNavController().navigate(action)
                }
                viewModel.EVENT_FINISH -> {
                    viewModel.parentViewModel?.finishKyc?.value = DocumentsResponse(false, "true")
                }
                viewModel.EVENT_EID_UPDATE ->{
                    viewModel.parentViewModel?.finishKyc?.value = DocumentsResponse(false)
                }
            }
        })
    }

    override fun onDestroyView() {
        viewModel.clickEvent.removeObservers(this)
        super.onDestroyView()
    }

    override fun showUnderAgeAlert() {
        AlertDialog.Builder(requireContext()).apply {
            setCancelable(false)
            setMessage(getString(Strings.screen_b2c_eid_info_review_display_text_error_under_age))
            setPositiveButton(getString(Strings.common_button_yes)) { _, which ->
                viewModel.handleUserAcceptance(
                    viewModel.EVENT_ERROR_UNDER_AGE
                )
            }
            setNegativeButton(getString(Strings.screen_b2c_eid_info_review_button_not_under_age)) { _, _ ->
                viewModel.handleUserRejection(
                    viewModel.EVENT_ERROR_UNDER_AGE
                )
            }
        }.create().show()
    }

    override fun showExpiredEidAlert() {
        AlertDialog.Builder(requireContext()).apply {
            setCancelable(false)
            setMessage(getString(Strings.screen_b2c_eid_info_review_display_text_error_expired_eid))
            setPositiveButton(getString(Strings.common_button_yes)) { dialog, which ->
                viewModel.handleUserAcceptance(
                    viewModel.EVENT_ERROR_EXPIRED_EID
                )
            }
            setNegativeButton(getString(Strings.screen_b2c_eid_info_review_button_valid_emirates_id)) { dialog, which ->
                viewModel.handleUserRejection(
                    viewModel.EVENT_ERROR_EXPIRED_EID
                )
            }
        }.create().show()
    }

    override fun showInvalidEidAlert() {
        AlertDialog.Builder(requireContext()).apply {
            setCancelable(true)
            setMessage(getString(Strings.idenetity_scanner_sdk_screen_review_info_display_text_error_not_readable))
            setPositiveButton(getString(R.string.ok)) { dialog, which ->
                viewModel.handleUserRejection(
                    viewModel.EVENT_ERROR_EXPIRED_EID
                )
            }
        }.create().show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null && viewModel.parentViewModel?.skipFirstScreen?.value == true) {
//            if (MyUserManager.eidStatus != EIDStatus.EXPIRED)
//                activity?.finish()
        }
        if (requestCode == IdentityScannerActivity.SCAN_EID_CAM && resultCode == Activity.RESULT_OK) {
            data?.let {
                viewModel.onEIDScanningComplete(it.getParcelableExtra(IdentityScannerActivity.SCAN_RESULT))
            }
        }else{
            viewModel.parentViewModel?.finishKyc?.value = DocumentsResponse(false)
        }
    }

    override fun showUSACitizenAlert() {
        val action =
            EidInfoReviewFragmentDirections.actionEidInfoReviewFragmentToInformationErrorFragment(
                viewModel.sanctionedCountry
            )
        findNavController().navigate(action)
    }

    override fun openCardScanner() {
        startActivityForResult(
            IdentityScannerActivity.getLaunchIntent(
                requireContext(),
                DocumentType.EID,
                IdentityScannerActivity.SCAN_FROM_CAMERA
            ),
            IdentityScannerActivity.SCAN_EID_CAM
        )
    }
}