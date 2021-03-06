package co.yap.modules.kyc.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentEditCardNameBinding
import co.yap.modules.kyc.activities.DocumentsResponse
import co.yap.modules.kyc.interfaces.IEditCardName
import co.yap.modules.kyc.viewmodels.EditCardNameViewModel
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.helpers.SharedPreferenceManager

class EditCardNameFragment : KYCChildFragment<FragmentEditCardNameBinding,IEditCardName.ViewModel>(), IEditCardName.View {
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_edit_card_name
    override val viewModel: EditCardNameViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        viewModel.parentViewModel?.showProgressBar?.value = false
        viewModel.clickEvent.observe(this, Observer {
            when (it) {
                R.id.btnNext -> {
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
            }

        })
    }
}