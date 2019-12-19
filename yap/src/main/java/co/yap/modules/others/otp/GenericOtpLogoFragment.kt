package co.yap.modules.others.otp

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentGenericOtpLogoBinding
import co.yap.modules.dashboard.yapit.sendmoney.activities.BeneficiaryCashTransferActivity
import co.yap.modules.forgotpasscode.interfaces.IForgotPasscodeOtp
import co.yap.translation.Strings
import co.yap.yapcore.helpers.Utils

class GenericOtpLogoFragment : GenericOtpFragment() {
    private val genericOtpLogoFragmentArgs: GenericOtpLogoFragmentArgs by navArgs()

    override val viewModel: IForgotPasscodeOtp.ViewModel
        get() = ViewModelProviders.of(this).get(GenericOtpLogoViewModel::class.java)

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_generic_otp_logo
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun loadData() {
        viewModel.action = args?.otpType
        args?.mobileNumber?.let {
            when {
                it.startsWith("00") -> {
                    viewModel.state.mobileNumber[0] =
                        args!!.mobileNumber.replaceRange(
                            0,
                            2,
                            "+"
                        )
                }
                args?.mobileNumber!!.startsWith("+") -> {
                    viewModel.state.mobileNumber[0] =
                        Utils.getFormattedPhone(args?.mobileNumber.toString())
                }
                else -> {
                    viewModel.state.mobileNumber[0] =
                        Utils.formatePhoneWithPlus(args?.mobileNumber.toString())
                }
            }
        }
        viewModel.destination = args?.username
        viewModel.emailOtp = args?.emailOtp
        if (activity is BeneficiaryCashTransferActivity) {
            (activity as BeneficiaryCashTransferActivity).let {
                it.viewModel.state.toolBarTitle =
                    getString(Strings.screen_cash_pickup_funds_display_otp_header)
                it.viewModel.state.leftButtonVisibility = true
                viewModel.state.fullName = it.viewModel.state.beneficiary?.fullName()
                viewModel.state.imageUrl = it.viewModel.state.beneficiary?.beneficiaryPictureUrl
                viewModel.state.amount = genericOtpLogoFragmentArgs.amount
            }

        }
    }

    override fun setObservers() {
        viewModel.nextButtonPressEvent.observe(this, Observer {
            if (activity is BeneficiaryCashTransferActivity) {
                (activity as BeneficiaryCashTransferActivity).viewModel.state.otpSuccess = true
            }
            findNavController().navigateUp()
        })
    }

    override fun onDestroy() {
        viewModel.nextButtonPressEvent.removeObservers(this)
        super.onDestroy()
    }

    private fun getBindings(): FragmentGenericOtpLogoBinding {
        return viewDataBinding as FragmentGenericOtpLogoBinding
    }

}