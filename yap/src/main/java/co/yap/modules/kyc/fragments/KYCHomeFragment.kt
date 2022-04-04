package co.yap.modules.kyc.fragments

import android.os.Bundle
import androidx.databinding.Observable
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import co.yap.R
import co.yap.modules.kyc.activities.DocumentsResponse
import co.yap.modules.kyc.enums.DocScanStatus
import co.yap.modules.kyc.interfaces.IKYCHome
import co.yap.modules.kyc.viewmodels.KYCHomeViewModel
import co.yap.translation.Strings
import co.yap.yapcore.enums.AccountStatus
import co.yap.yapcore.firebase.FirebaseEvent
import co.yap.yapcore.firebase.trackEventWithScreenName
import co.yap.yapcore.managers.SessionManager
import java.io.File

class KYCHomeFragment : KYCChildFragment<IKYCHome.ViewModel>(), IKYCHome.View {

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_kyc_home

    override val viewModel: KYCHomeViewModel
        get() = ViewModelProvider(this).get(KYCHomeViewModel::class.java)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        trackEventWithScreenName(FirebaseEvent.SCAN_ID)
//        requireActivity().firebaseTagManagerEvent(FirebaseTagManagerModel(action = FirebaseEvents.SCAN_ID.event))
        shouldSkipScreen()
        addObservers()
    }


    private fun addObservers() {
        viewModel.parentViewModel?.accountStatus?.value = SessionManager.user?.notificationStatuses
        viewModel.state.addOnPropertyChangedCallback(stateObserver)
        viewModel.clickEvent.observe(this, Observer {
            when (it) {
                R.id.cvCard -> {
                    viewModel.parentViewModel?.uqudoManager?.resetData()
                    navigate(viewModel.navigateTo(viewModel.isFromAmendment()))
                }
                R.id.btnNext -> {
                    if (viewModel.parentViewModel?.accountStatus?.value == AccountStatus.CAPTURED_EID.name) {
                        viewModel.requestDocumentsInformation {
                            navigate(R.id.action_KYCHomeFragment_to_confirmCardNameFragment)
                        }
                    } else {
                        viewModel.parentViewModel?.finishKyc?.value = DocumentsResponse(true)
                    }
                }
                R.id.tvSkip -> {
                    trackEventWithScreenName(FirebaseEvent.CLICK_SKIP_EID)
                    viewModel.parentViewModel?.finishKyc?.value = DocumentsResponse(false)
                }
            }
        })
    }

    private fun shouldSkipScreen() {
        viewModel.parentViewModel?.skipFirstScreen?.value?.let {
            if (it) {
                if (viewModel.parentViewModel?.uqudoManager?.getUqudoIdentity()?.isAmendment == true) {
                    requireActivity().finish()
                } else {
                    if (viewModel.parentViewModel?.comingFrom?.value.isNullOrBlank().not()) {
                        viewModel.parentViewModel?.uqudoManager?.resetData()
                        findNavController().navigate(R.id.action_KYCHomeFragment_to_eidInfoReviewFragment)
                    } else {
                        navigateWithPopup(
                            viewModel.navigateTo(viewModel.isFromAmendment()),
                            R.id.KYCHomeFragment
                        )
                    }
                }
            } else if (requireActivity().intent?.getBooleanExtra("GO_ERROR", false) == true) {
                navigateToInformationErrorFragment()
            } else {
                viewModel.state.eidScanStatus = DocScanStatus.SCAN_PENDING
            }
        } ?: if (requireActivity().intent?.getBooleanExtra("GO_ERROR", false) == true) {
            navigateToInformationErrorFragment()
        }
    }

    private fun navigateToInformationErrorFragment() {
        val action =
            KYCHomeFragmentDirections.actionKYCHomeFragmentToInformationErrorFragment(
                getString(Strings.screen_kyc_information_error_display_text_title_from_us),
                getString(Strings.screen_kyc_information_error_text_description_from_us)
            )
        navigate(action)
    }

    private val stateObserver = object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
            when (propertyId) {
                BR.eidScanStatus -> {
                    if (viewModel.state.eidScanStatus === DocScanStatus.SCAN_COMPLETED) {
                        //TODO Uqudo Review FLOW WILL BE Handled here
                        //  findNavController().navigate(if (viewModel.isFromAmendment()) R.id.action_KYCHomeFragment_to_eidInfoReviewAmendmentFragment else R.id.action_KYCHomeFragment_to_eidInfoReviewFragment)
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

    override fun onDestroy() {
        viewModel.parentViewModel?.paths?.forEach { filePath ->
            File(filePath).deleteRecursively()
        }
        super.onDestroy()
    }

    override fun onBackPressed(): Boolean {
        return true
    }

}