package co.yap.modules.dashboard.more.profile.fragments

import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.more.profile.intefaces.IUnverifiedChangeEmail
import co.yap.modules.dashboard.more.profile.viewmodels.UnverifiedChangeEmailViewModel
import co.yap.yapcore.BaseBindingFragment

class UnverifiedChangeEmailFragment : BaseBindingFragment<IUnverifiedChangeEmail.ViewModel>(),IUnverifiedChangeEmail.View{
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_unverified_change_email

    override val viewModel: IUnverifiedChangeEmail.ViewModel
        get() = ViewModelProviders.of(this).get(UnverifiedChangeEmailViewModel::class.java)

}