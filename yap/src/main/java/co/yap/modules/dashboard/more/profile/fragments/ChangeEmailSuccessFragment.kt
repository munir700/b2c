package co.yap.modules.dashboard.more.profile.fragments

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.buttonClickEvent.observe(this, Observer {
            findNavController().navigate(R.id.action_changeEmailSuccessFragment_to_personalDetailsFragment)
        })
    }

    override fun onDestroy() {
        viewModel.buttonClickEvent.removeObservers(this)
        super.onDestroy()
    }
}