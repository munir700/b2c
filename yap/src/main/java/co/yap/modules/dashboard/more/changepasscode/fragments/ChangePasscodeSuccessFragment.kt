package co.yap.modules.dashboard.more.changepasscode.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.more.changepasscode.interfaces.IChangePassCodeSuccess
import co.yap.modules.dashboard.more.changepasscode.viewmodels.ChangePasscodeSuccessViewModel
import co.yap.translation.Strings

class ChangePasscodeSuccessFragment :
    ChangePasscodeBaseFragment<IChangePassCodeSuccess.ViewModel>(),
    IChangePassCodeSuccess.View {

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_change_passcodee_success
    override val viewModel: ChangePasscodeSuccessViewModel
        get() = ViewModelProviders.of(this).get(ChangePasscodeSuccessViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.buttonClickEvent.observe(this, Observer {
            activity?.finish()
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.state.topSubHeading = "Your passcode has been updated \n successfully"
        viewModel.state.topMainHeading = resources.getString(R.string.screen_email_address_success_display_text_heading)
    }

    override fun onDestroy() {
        viewModel.buttonClickEvent.removeObservers(this)
        super.onDestroy()
    }

    override fun onBackPressed(): Boolean {
        return true
    }
}