package co.yap.modules.dashboard.more.profile.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
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
        viewModel.clickEvent.observe(this, Observer {
            val action=ChangeEmailFragmentDirections.actionChangeEmailFragmentToGenericOtpFragment("03025101902",false,"03025101902","CHANGE_EMAIL")
            findNavController().navigate(action)
        })
    }
    override fun onDestroy() {
        viewModel.clickEvent.removeObservers(this)
        super.onDestroy()
    }
}