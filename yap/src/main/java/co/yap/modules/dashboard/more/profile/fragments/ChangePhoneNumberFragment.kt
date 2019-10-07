package co.yap.modules.dashboard.more.profile.fragments

import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.more.profile.intefaces.IChangePhoneNumber
import co.yap.modules.dashboard.more.profile.viewmodels.ChangePhoneNumberViewModel
import co.yap.yapcore.BaseBindingFragment

class ChangePhoneNumberFragment : BaseBindingFragment<IChangePhoneNumber.ViewModel>(),
    IChangePhoneNumber.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_change_phone_number
    override val viewModel: IChangePhoneNumber.ViewModel
        get() = ViewModelProviders.of(this).get(ChangePhoneNumberViewModel::class.java)
}