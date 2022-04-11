package co.yap.modules.kyc.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentOnboardingConfirmCardNameBinding
import co.yap.modules.kyc.activities.DocumentsResponse
import co.yap.modules.kyc.interfaces.IConfirmCardName
import co.yap.modules.kyc.viewmodels.ConfirmCardNameViewModel
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.helpers.SharedPreferenceManager

class ConfirmCardNameFragment : KYCChildFragment<FragmentOnboardingConfirmCardNameBinding,IConfirmCardName.ViewModel>(),
    IConfirmCardName.View {
    override val viewModel: ConfirmCardNameViewModel by viewModels()

    override fun getBindingVariable() = BR.viewModel
    override fun getLayoutId() = R.layout.fragment_onboarding_confirm_card_name

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setObservers()
    }


    private fun setObservers() {
//        viewModel.parentViewModel?.showProgressBar?.value = true
        viewModel.clickEvent.observe(this, onClickObserver)
    }

    val onClickObserver = Observer<Int> {
        when (it) {
            R.id.btnNameFine -> {
                viewModel.postProfileInformation {
                    if (it) {
                        viewModel.parentViewModel?.finishKyc?.value =
                            DocumentsResponse(true)
                        SharedPreferenceManager.getInstance(requireContext())
                            .removeValue(Constants.KYC_MIDDLE_NAME)
                        SharedPreferenceManager.getInstance(requireContext())
                            .removeValue(Constants.KYC_LAST_NAME)
                        SharedPreferenceManager.getInstance(requireContext())
                            .removeValue(Constants.KYC_FIRST_NAME)
                    }
                }
            }
            R.id.tvEditCardName -> {
               navigate(R.id.action_confirmCardNameFragment_to_editCardNameFragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.clickEvent.removeObserver(onClickObserver)
    }
}