package co.yap.modules.kyc.fragments

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.BR
import co.yap.R
import co.yap.modules.kyc.activities.DocumentsDashboardActivity
import co.yap.modules.kyc.activities.DocumentsDashboardActivity.Companion.hasStartedScanner
import co.yap.modules.kyc.viewmodels.EidInfoReviewViewModel
import co.yap.modules.onboarding.interfaces.IEidInfoReview
import co.yap.translation.Strings
import com.digitify.identityscanner.docscanner.activities.IdentityScannerActivity
import com.digitify.identityscanner.docscanner.enums.DocumentType
import kotlinx.android.synthetic.main.activity_eid_info_review.*


class EidInfoReviewFragment : KYCChildFragment<IEidInfoReview.ViewModel>(), IEidInfoReview.View {

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_eid_info_review

    override val viewModel: IEidInfoReview.ViewModel
        get() = ViewModelProviders.of(this).get(EidInfoReviewViewModel::class.java)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (DocumentsDashboardActivity.isFromMoreSection) {
            //tvNoThanks.visibility = GONE
            eidInfoReviewtoolBarLayout.visibility = VISIBLE
            openCardScanner()

            tbBtnBack.setOnClickListener { activity!!.finish() }

        } else {
            tvNoThanks.visibility = VISIBLE
            eidInfoReviewtoolBarLayout.visibility = GONE
        }

        viewModel.clickEvent.observe(this, Observer {
            when (it) {
                viewModel.EVENT_ERROR_INVALID_EID->showInvalidEidAlert()
                viewModel.EVENT_ERROR_EXPIRED_EID -> showExpiredEidAlert()
                viewModel.EVENT_ERROR_UNDER_AGE -> showUnderAgeAlert()
                viewModel.EVENT_ERROR_FROM_USA -> showUSACitizenAlert()
                viewModel.EVENT_RESCAN -> openCardScanner()
                viewModel.EVENT_NEXT_WITH_ERROR -> findNavController().navigate(R.id.action_eidInfoReviewFragment_to_informationErrorFragment)
                viewModel.EVENT_NEXT -> findNavController().popBackStack()
                viewModel.EVENT_FINISH -> onBackPressed()
            }
        })
    }

    fun enableBtn() {

        if (viewModel.state.fullNameValid && viewModel.state.nationalityValid && viewModel.state.dateOfBirthValid && viewModel.state.genderValid && viewModel.state.expiryDateValid) {
            btnTouchId.enableButton(true)
        } else {
            btnTouchId.enableButton(false)
        }

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
        if (data == null && DocumentsDashboardActivity.isFromMoreSection) {
            activity!!.finish()
        }
        if (requestCode == IdentityScannerActivity.SCAN_EID_CAM && resultCode == Activity.RESULT_OK) {
            hasStartedScanner = false
            data?.let {
                viewModel.onEIDScanningComplete(it.getParcelableExtra(IdentityScannerActivity.SCAN_RESULT))
            }
        }
    }

    override fun showUSACitizenAlert() {
        AlertDialog.Builder(requireContext()).apply {
            setCancelable(false)
            setMessage(getString(Strings.screen_b2c_eid_info_review_display_text_error_from_usa))
            setPositiveButton(getString(Strings.common_button_yes)) { dialog, which ->
                viewModel.handleUserAcceptance(
                    viewModel.EVENT_ERROR_FROM_USA
                )
            }
            setNegativeButton(getString(Strings.screen_b2c_eid_info_review_button_not_from_usa)) { dialog, which ->
                viewModel.handleUserRejection(
                    viewModel.EVENT_ERROR_FROM_USA
                )
            }
        }.create().show()
    }

    override fun openCardScanner() {
        //clear existing state
//        viewModel.clearData()
        if (DocumentsDashboardActivity.isFromMoreSection) {
            hasStartedScanner = true
        }
        startActivityForResult(
            IdentityScannerActivity.getLaunchIntent(
                requireContext(),
                DocumentType.EID,
                IdentityScannerActivity.SCAN_FROM_CAMERA
            ),
            IdentityScannerActivity.SCAN_EID_CAM
        )
    }

    override fun onBackPressed(): Boolean {
        if (DocumentsDashboardActivity.isFromMoreSection) {
            hasStartedScanner = false
            activity!!.finish()
        }
        return false

    }
}