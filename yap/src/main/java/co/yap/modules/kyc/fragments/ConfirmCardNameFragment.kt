package co.yap.modules.kyc.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import co.yap.BR
import co.yap.R
import co.yap.modules.kyc.activities.DocumentsResponse
import co.yap.modules.kyc.interfaces.IConfirmCardName
import co.yap.modules.kyc.viewmodels.ConfirmCardNameViewModel

class ConfirmCardNameFragment : KYCChildFragment<IConfirmCardName.ViewModel>(),
    IConfirmCardName.View {
    override val viewModel: ConfirmCardNameViewModel by viewModels()

    override fun getBindingVariable() = BR.viewModel
    override fun getLayoutId() = R.layout.fragment_onboarding_confirm_card_name

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
    }

    private fun setObservers() {
        viewModel.clickEvent.observe(this, onClickObserver)
    }

    val onClickObserver = Observer<Int> {
        when (it) {
            R.id.btnNameFine -> {
                viewModel.parentViewModel?.finishKyc?.value =
                    DocumentsResponse(true)
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