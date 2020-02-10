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
import co.yap.modules.kyc.activities.DocumentsDashboardActivity
import co.yap.modules.kyc.viewmodels.EidInfoReviewViewModel
import co.yap.modules.onboarding.interfaces.IEidInfoReview
import co.yap.translation.Strings
import co.yap.translation.Translator
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.helpers.SharedPreferenceManager
import com.digitify.identityscanner.docscanner.activities.IdentityScannerActivity
import com.digitify.identityscanner.docscanner.enums.DocumentType
import kotlinx.android.synthetic.main.activity_eid_info_review.*


class EidInfoReviewFragment : KYCChildFragment<IEidInfoReview.ViewModel>(), IEidInfoReview.View {

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int {
        if (getAppliedAppTheme()) return R.layout.activity_eid_info_review_house_hold
        else return R.layout.activity_eid_info_review
    }

    fun getAppliedAppTheme(): Boolean {
        return SharedPreferenceManager(activity!!).getThemeValue().equals(Constants.THEME_HOUSEHOLD)
    }

    override val viewModel: EidInfoReviewViewModel
        get() = ViewModelProviders.of(this).get(EidInfoReviewViewModel::class.java)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (viewModel.parentViewModel?.allowSkip?.value == true) {
            openCardScanner()
            tbBtnBack.setOnClickListener {
                if (activity is DocumentsDashboardActivity)
                    (activity as DocumentsDashboardActivity).goToDashBoard(
                        success = false,
                        skippedPress = true
                    )
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
                    if (activity is DocumentsDashboardActivity)
                        (activity as DocumentsDashboardActivity).goToDashBoard(
                            success = true,
                            skippedPress = false
                        )
                }
                viewModel.EVENT_ALREADY_USED_EID -> {
                    if (activity is DocumentsDashboardActivity)
                        (activity as DocumentsDashboardActivity).goToDashBoard(
                            success = false,
                            skippedPress = false, error = true
                        )
                }
                viewModel.EVENT_NEXT_WITH_ERROR -> {
                    val action =
                        EidInfoReviewFragmentDirections.actionEidInfoReviewFragmentToInformationErrorFragment(
                            viewModel.sanctionedCountry
                        )
                    findNavController().navigate(action)
                }
                viewModel.EVENT_FINISH -> {
                    if (activity is DocumentsDashboardActivity)
                        (activity as DocumentsDashboardActivity).goToDashBoard(
                            success = false,
                            skippedPress = false, error = true
                        )
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
        if (data == null && viewModel.parentViewModel?.allowSkip?.value == true) {
            activity?.finish()
        }
        if (requestCode == IdentityScannerActivity.SCAN_EID_CAM && resultCode == Activity.RESULT_OK) {
            data?.let {
                viewModel.onEIDScanningComplete(it.getParcelableExtra(IdentityScannerActivity.SCAN_RESULT))
            }
        }
    }

    override fun showUSACitizenAlert() {

        val title = Translator.getString(
            requireContext(),
            Strings.screen_b2c_eid_info_review_display_text_error_from_usa,
            viewModel.sanctionedCountry
        )

        val sanctionedNationality = Translator.getString(
            requireContext(),
            Strings.screen_b2c_eid_info_review_button_not_from_usa,
            viewModel.sanctionedNationality
        )

        //

        AlertDialog.Builder(requireContext()).apply {
            setCancelable(false)
            setMessage(title)
            setPositiveButton(getString(Strings.common_button_yes)) { dialog, which ->
                viewModel.handleUserAcceptance(
                    viewModel.EVENT_ERROR_FROM_USA
                )
            }
            setNegativeButton(sanctionedNationality) { dialog, which ->
                viewModel.handleUserRejection(
                    viewModel.EVENT_ERROR_FROM_USA
                )
            }
        }.create().show()
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