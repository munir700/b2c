package co.yap.modules.others.generic_otp

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import co.yap.modules.dashboard.more.main.activities.MoreActivity
import co.yap.modules.forgotpasscode.fragments.ForgotPasscodeOtpFragment
import co.yap.modules.forgotpasscode.interfaces.IForgotPasscodeOtp
import co.yap.yapcore.helpers.Utils

class GenericOtpFragment : ForgotPasscodeOtpFragment() {
    val args: GenericOtpFragmentArgs? by navArgs()

    override val viewModel: IForgotPasscodeOtp.ViewModel
        get() = ViewModelProviders.of(this).get(GenericOtpViewModel::class.java)

    override fun loadData() {
        if (args?.mobileNumber!!.startsWith("00")) {
            viewModel.state.mobileNumber[0] =
                args!!.mobileNumber.replaceRange(
                    0,
                    2,
                    "+"
                )
        } else {
            viewModel.state.mobileNumber[0] = Utils.formatePhoneWithPlus(args?.mobileNumber.toString())
        }

        viewModel.destination = args!!.username
        viewModel.emailOtp = args!!.emailOtp
        viewModel.action = args!!.otpType
    }

    override fun setObservers() {
        viewModel.nextButtonPressEvent.observe(this, Observer {
            MoreActivity.navigationVariable = true
            findNavController().navigateUp()
        })
    }

    override fun onDestroy() {
        viewModel.nextButtonPressEvent.removeObservers(this)
        super.onDestroy()
    }
}