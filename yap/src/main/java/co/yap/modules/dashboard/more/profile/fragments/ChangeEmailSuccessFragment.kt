package co.yap.modules.dashboard.more.profile.fragments

import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.more.profile.intefaces.IChangeEmailSuccess
import co.yap.modules.dashboard.more.profile.viewmodels.ChangeEmailSuccessViewModel
import co.yap.yapcore.BaseBindingFragment

class ChangeEmailSuccessFragment : BaseBindingFragment<IChangeEmailSuccess.ViewModel>(),
    IChangeEmailSuccess.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_change_email_success

    override val viewModel: IChangeEmailSuccess.ViewModel
        get() = ViewModelProviders.of(this).get(ChangeEmailSuccessViewModel::class.java)
}