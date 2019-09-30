package co.yap.modules.dashboard.more.profile.fragments

import androidx.lifecycle.ViewModelProviders
import co.yap.R
import co.yap.modules.dashboard.more.profile.intefaces.IChangeEmail
import co.yap.modules.dashboard.more.profile.viewmodels.ChangeEmailViewModel
import co.yap.yapcore.BaseBindingFragment

class ChangeEmailFragment : BaseBindingFragment<IChangeEmail.ViewModel>(), IChangeEmail.View {
    override fun getBindingVariable(): Int = 0

    override fun getLayoutId(): Int = R.layout.fragment_change_email
    override val viewModel: IChangeEmail.ViewModel
        get() = ViewModelProviders.of(this).get(ChangeEmailViewModel::class.java)
}