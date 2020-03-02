package co.yap.modules.others.otp

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import co.yap.modules.dashboard.cards.paymentcarddetail.forgotcardpin.activities.ForgotCardPinActivity
import co.yap.modules.dashboard.more.main.activities.MoreActivity
import co.yap.modules.dashboard.yapit.sendmoney.activities.BeneficiaryCashTransferActivity
import co.yap.modules.dashboard.yapit.sendmoney.activities.SendMoneyHomeActivity
import co.yap.modules.forgotpasscode.fragments.ForgotPasscodeOtpFragment
import co.yap.modules.forgotpasscode.interfaces.IForgotPasscodeOtp
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.helpers.Utils

open class GenericOtpFragment : ForgotPasscodeOtpFragment() {
    val args: GenericOtpFragmentArgs? by navArgs()

    override val viewModel: IForgotPasscodeOtp.ViewModel
        get() = ViewModelProviders.of(this).get(GenericOtpViewModel::class.java)

    override fun loadData() {
        viewModel.action = args?.otpType
        args?.mobileNumber?.let {
            if (it.startsWith("00")) {
                viewModel.state.mobileNumber[0] =
                    args?.mobileNumber?.replaceRange(
                        0,
                        2,
                        "+"
                    )
            } else if (args?.mobileNumber?.startsWith("+") == true) {
                viewModel.state.mobileNumber[0] =
                    Utils.getFormattedPhone(args?.mobileNumber.toString())
            } else {
                viewModel.state.mobileNumber[0] =
                    Utils.formatePhoneWithPlus(args?.mobileNumber.toString())
            }
        }
        viewModel.destination = args?.username
        viewModel.emailOtp = args?.emailOtp
        if (activity is BeneficiaryCashTransferActivity) {
            (activity as BeneficiaryCashTransferActivity).viewModel.state.toolBarTitle =
                "Confirm transfer"
        }
    }

    override fun setObservers() {
        viewModel.nextButtonPressEvent.observe(this, Observer {
            when (activity) {
                is ForgotCardPinActivity -> {
                    (activity as ForgotCardPinActivity).viewModel.state.currentScreen =
                        Constants.FORGOT_CARD_PIN_ACTION
                }
                is MoreActivity -> {
                    MoreActivity.navigationVariable = true
                }
                is BeneficiaryCashTransferActivity -> {
                    (activity as BeneficiaryCashTransferActivity).viewModel.state.otpSuccess = true
                }
                is SendMoneyHomeActivity -> {
                    when (viewModel.action) {
                        Constants.DOMESTIC_BENEFICIARY -> (activity as SendMoneyHomeActivity).viewModel.otpSuccess.value =
                            true
                        Constants.CASHPAYOUT_BENEFICIARY -> (activity as SendMoneyHomeActivity).viewModel.otpSuccess.value =
                            true
                        Constants.RMT_BENEFICIARY -> (activity as SendMoneyHomeActivity).viewModel.otpSuccess.value =
                            true
                        Constants.SWIFT_BENEFICIARY -> (activity as SendMoneyHomeActivity).viewModel.otpSuccess.value =
                            true
                    }
                }
            }
            findNavController().navigateUp()
        })
    }

    override fun onDestroy() {
        viewModel.nextButtonPressEvent.removeObservers(this)
        super.onDestroy()
    }
}