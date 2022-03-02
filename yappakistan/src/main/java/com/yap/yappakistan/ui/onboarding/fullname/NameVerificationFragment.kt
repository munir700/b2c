package com.yap.yappakistan.ui.onboarding.fullname

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.yap.core.base.BaseNavFragment
import com.yap.yappakistan.BR
import com.yap.yappakistan.R
import com.yap.yappakistan.databinding.FragmentNameVerificationBinding
import com.yap.yappakistan.ui.onboarding.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NameVerificationFragment :
    BaseNavFragment<FragmentNameVerificationBinding, INameVerification.State, INameVerification.ViewModel>(
        R.layout.fragment_name_verification
    ) {
    override fun getBindingVariable(): Int = BR.viewModel
    override val viewModel: NameVerificationVM by viewModels()
    private val parentViewModel: MainViewModel by activityViewModels()

    override fun onClick(id: Int) {
        when (id) {
            R.id.btnNext -> {
                parentViewModel.signupData.firstName = viewModel.viewState.firstName.value
                parentViewModel.signupData.lastName = viewModel.viewState.lastName.value
                navigate(
                    R.id.action_nameVerificationFragment_to_emailVerificationFragment
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        parentViewModel.setProgressVisibility(true)
        parentViewModel.setProgress(60)
    }
}