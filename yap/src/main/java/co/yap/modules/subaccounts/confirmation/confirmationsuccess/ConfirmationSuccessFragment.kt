package co.yap.modules.subaccounts.confirmation.confirmationsuccess

import co.yap.R
import co.yap.BR
import co.yap.databinding.FragmentConfirmationSuccessBinding
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment

class ConfirmationSuccessFragment :
    BaseNavViewModelFragment<FragmentConfirmationSuccessBinding, IConfirmationSuccess.State, ConfirmationSuccessVM>() {

    override fun getBindingVariable() = BR.viewModel
    override fun getLayoutId() = R.layout.fragment_confirmation_success
}
