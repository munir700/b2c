package co.yap.modules.dashboard.more.profile.fragments

import android.app.Activity
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentChangeEmailBinding
import co.yap.modules.dashboard.more.main.fragments.MoreBaseFragment
import co.yap.modules.dashboard.more.profile.intefaces.IChangeEmail
import co.yap.modules.dashboard.more.profile.viewmodels.ChangeEmailViewModel
import co.yap.modules.otp.GenericOtpFragment
import co.yap.modules.otp.OtpDataModel
import co.yap.modules.otp.getOtpMessageFromComposer
import co.yap.yapcore.enums.OTPActions
import co.yap.yapcore.helpers.SharedPreferenceManager
import co.yap.yapcore.helpers.extentions.startFragmentForResult
import co.yap.yapcore.managers.SessionManager


open class ChangeEmailFragment : MoreBaseFragment<FragmentChangeEmailBinding , IChangeEmail.ViewModel>(), IChangeEmail.View {
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
                startOtpFragment()
            }
        })

        viewModel.changeEmailSuccessEvent.observe(this, Observer {
            SessionManager.user?.currentCustomer?.email = viewModel.state.newEmail
//            SharedPreferenceManager.getInstance(requireContext()).saveUserNameWithEncryption(
//                viewModel.state.newEmail
//            )
            val action =
                ChangeEmailFragmentDirections.actionChangeEmailFragmentToChangeEmailSuccessFragment()
            findNavController().navigate(action)
        })

    }

    private fun startOtpFragment() {
        startFragmentForResult<GenericOtpFragment>(
            fragmentName = GenericOtpFragment::class.java.name,
            bundle = bundleOf(
                OtpDataModel::class.java.name to OtpDataModel(
                    OTPActions.CHANGE_EMAIL.name,
                    SessionManager.user?.currentCustomer?.getFormattedPhoneNumber(requireContext())
                        ?: "",
                    otpMessage = requireContext().getOtpMessageFromComposer(
                        OTPActions.CHANGE_EMAIL.name,
                        SessionManager.user?.currentCustomer?.firstName,
                        "%s1",
                        "%s2",
                        SessionManager.helpPhoneNumber
                    )
                )
            ),
            showToolBar = true
        ) { resultCode, _ ->
            if (resultCode == Activity.RESULT_OK) {
                viewModel.changeEmail()
            }
        }
    }

    override fun onDestroy() {
        viewModel.clickEvent.removeObservers(this)
        viewModel.success.removeObservers(this)
        viewModel.changeEmailSuccessEvent.removeObservers(this)
        super.onDestroy()
    }
}