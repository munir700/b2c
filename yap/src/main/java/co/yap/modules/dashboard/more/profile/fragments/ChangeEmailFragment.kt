package co.yap.modules.dashboard.more.profile.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.more.main.activities.MoreActivity
import co.yap.modules.dashboard.more.main.fragments.MoreBaseFragment
import co.yap.modules.dashboard.more.profile.intefaces.IChangeEmail
import co.yap.modules.dashboard.more.profile.viewmodels.ChangeEmailViewModel
import co.yap.translation.Strings
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.managers.MyUserManager


open class ChangeEmailFragment : MoreBaseFragment<IChangeEmail.ViewModel>(), IChangeEmail.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_change_email
    override val viewModel: IChangeEmail.ViewModel
        get() = ViewModelProviders.of(this).get(ChangeEmailViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setObservers()
    }

    override fun setObservers() {
        viewModel.success.observe(this, Observer {
            if (it) {
                val action =
                    ChangeEmailFragmentDirections.actionChangeEmailFragmentToGenericOtpFragment(
                        otpType = Constants.CHANGE_EMAIL,
                        mobileNumber = Utils.getFormattedMobileNumber(
                            MyUserManager.user!!.currentCustomer.countryCode,
                            MyUserManager.user!!.currentCustomer.mobileNo
                        )
                    )
                findNavController().navigate(action)
            }
        })

        viewModel.clickEvent.observe(this, Observer {
            when (it) {
                R.id.tbBtnBack -> {

                }
            }
        })


        viewModel.changeEmailSuccessEvent.observe(this, Observer {
            MyUserManager.user?.currentCustomer?.email = viewModel.state.newEmail
            viewModel.sharedPreferenceManager.saveUserNameWithEncryption(
                viewModel.state.newEmail
            )
            val action =
                ChangeEmailFragmentDirections.actionChangeEmailFragmentToChangeEmailSuccessFragment(
                    getString(Strings.screen_email_address_success_display_text_sub_heading),
                    viewModel.state.newEmail
                )
            findNavController().navigate(action)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (MoreActivity.navigationVariable) {
            MoreActivity.navigationVariable = false
            viewModel.changeEmail()
        }
    }

    override fun onDestroy() {
        viewModel.clickEvent.removeObservers(this)
        viewModel.success.removeObservers(this)
        viewModel.changeEmailSuccessEvent.removeObservers(this)
        super.onDestroy()
    }
}