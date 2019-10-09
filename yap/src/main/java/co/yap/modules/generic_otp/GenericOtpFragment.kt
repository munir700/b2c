package co.yap.modules.generic_otp

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import co.yap.modules.dashboard.more.activities.MoreActivity
import co.yap.modules.forgotpasscode.fragments.ForgotPasscodeOtpFragment
import co.yap.modules.forgotpasscode.interfaces.IForgotPasscodeOtp

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
            viewModel.state.mobileNumber[0] = args?.mobileNumber
        }

        viewModel.destination = args!!.username
        // viewModel.destination=args.mobileNumber
        viewModel.emailOtp = args!!.emailOtp
        viewModel.action = args!!.otpType
       // viewModel.phone = args!!.mobileNumber

    }

    override fun setObservers() {
        viewModel.nextButtonPressEvent.observe(this, Observer {
            MoreActivity.navigationVariable = true
            findNavController().navigateUp()
//            when (viewModel.action) {
//                Constants.CHANGE_EMAIL -> {
//                    MoreActivity.navigationVariable = true
//                    findNavController().navigateUp()
////                    val action=GenericOtpFragmentDirections.actionGenericOtpFragmentToChangeEmailSuccessFragment("",viewModel.destination)
////                    findNavController().navigate(R.id.action_genericOtpFragment_to_changeEmailSuccessFragment)
//                }
//                //"CHANGE_PHONE_NUMBER" ->
//            }
        })
    }

    override fun onDestroy() {
        viewModel.nextButtonPressEvent.removeObservers(this)
        super.onDestroy()
    }
}