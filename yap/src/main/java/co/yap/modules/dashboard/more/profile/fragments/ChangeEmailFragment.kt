package co.yap.modules.dashboard.more.profile.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.more.fragments.MoreBaseFragment
import co.yap.modules.dashboard.more.profile.intefaces.IChangeEmail
import co.yap.modules.dashboard.more.profile.viewmodels.ChangeEmailViewModel
import kotlinx.android.synthetic.main.fragment_change_email.*


class ChangeEmailFragment : MoreBaseFragment<IChangeEmail.ViewModel>(), IChangeEmail.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_change_email
    override val viewModel: IChangeEmail.ViewModel
        get() = ViewModelProviders.of(this).get(ChangeEmailViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
      //  etNewEmail.setError("dsa")
    }
}