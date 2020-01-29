package co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentInternationalTransactionConfirmationBinding
import co.yap.modules.dashboard.yapit.sendmoney.activities.BeneficiaryCashTransferActivity
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.interfaces.IInternationalTransactionConfirmation
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.viewmodels.InternationalTransactionConfirmationViewModel
import co.yap.translation.Strings
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.enums.SendMoneyBeneficiaryType
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.spannables.color
import co.yap.yapcore.helpers.spannables.getText

class InternationalTransactionConfirmationFragment :
    BaseBindingFragment<IInternationalTransactionConfirmation.ViewModel>(),
    IInternationalTransactionConfirmation.View {
    val args: InternationalTransactionConfirmationFragmentArgs by navArgs()
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_international_transaction_confirmation

    override val viewModel: IInternationalTransactionConfirmation.ViewModel
        get() = ViewModelProviders.of(this).get(InternationalTransactionConfirmationViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
        successOtpFlow()
    }

    private fun setUpViews() {
        if (activity is BeneficiaryCashTransferActivity) {
            setData()
            (activity as BeneficiaryCashTransferActivity).viewModel.state.toolBarVisibility = true
            (activity as BeneficiaryCashTransferActivity).viewModel.state.rightButtonVisibility =
                false
            (activity as BeneficiaryCashTransferActivity).viewModel.state.leftButtonVisibility =
                true
            (activity as BeneficiaryCashTransferActivity).viewModel.state.toolBarTitle =
                viewModel.state.confirmHeading

        }
    }

    override fun setData() {
        viewModel.state.args = args
        viewModel.state.name = args.beneficiaryName
        viewModel.state.confirmHeading =
            getString(Strings.screen_cash_pickup_funds_display_otp_header)
        viewModel.state.referenceNumber = args.referenceNumber
        viewModel.state.beneficiaryCountry = args.country


        viewModel.state.transferDescription = resources.getText(
            getString(Strings.screen_funds_confirmation_success_description)
            ,
            requireContext().color(R.color.colorPrimaryDark, args.senderCurrency)
            , requireContext().color(
                R.color.colorPrimaryDark,
                Utils.getFormattedCurrency(args.fxRateAmount)
            ),
            // viewModel.state.name
            args.firstName
            ,
            requireContext().color(
                R.color.colorPrimaryDark,
                "${args.fromFxRate} to ${args.toFxRate}"
            )
        )

        viewModel.state.position = args.position

        viewModel.state.receivingAmountDescription =
            resources.getText(
                getString(Strings.screen_funds_receive_description)
                ,
                //viewModel.state.name
                args.firstName,
                requireContext().color(
                    R.color.colorPrimaryDark,
                    "${Utils.getFormattedCurrency(args.receiverCurrencyAmount)} ${args.toFxRateCurrency}"
                )
            )

        val tst = args.totalAmount
        println(tst)
        viewModel.state.transferFeeDescription =
            resources.getText(
                getString(Strings.screen_funds_transfer_fee_description), requireContext().color(
                    R.color.colorPrimaryDark,
                    "${"AED"} ${Utils.getFormattedCurrency(args.totalAmount)}"
                )
            )
    }

    override fun setObservers() {
        viewModel.clickEvent.observe(this, clickEvent)
    }

    val clickEvent = Observer<Int> {
        when (it) {
            R.id.confirmButton -> {
                viewModel.createOtp()
//                do this after otp success
                /*  viewModel.state.referenceNumber?.let { referenceNumber ->
                      viewModel.state.position?.let { position ->
                          viewModel.state.beneficiaryCountry?.let { beneficiaryCountry ->
                              val action =
                                  InternationalTransactionConfirmationFragmentDirections.actionInternationalTransactionConfirmationFragmentToTransferSuccessFragment2(
                                      "",
                                      args.senderCurrency,
                                      Utils.getFormattedCurrency(args.fxRateAmount),
                                      referenceNumber, position, beneficiaryCountry
                                  )
                              findNavController().navigate(action)
                          }
                      }
                  }*/

            }
            viewModel.CREATE_OTP_SUCCESS_EVENT -> {
                viewModel.state.position?.let { position ->
                    viewModel.state.beneficiaryCountry?.let { beneficiaryCountry ->
                        val action =
                            InternationalTransactionConfirmationFragmentDirections.actionInternationalTransactionConfirmationFragmentToGenericOtpLogoFragment(
                                false,
                                viewModel.state.args?.otpAction ?: "",
                                args.fxRateAmount,
                                position,
                                beneficiaryCountry
                            )
                        findNavController().navigate(action)
                    }
                }
            }

            Constants.ADD_SUCCESS -> {
                // Send Broadcast for updating transactions list in `Home Fragment`
                val intent = Intent(Constants.BROADCAST_UPDATE_TRANSACTION)
                LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
                viewModel.state.referenceNumber?.let { referenceNumber ->
                    viewModel.state.position?.let { position ->
                        viewModel.state.beneficiaryCountry?.let { beneficiaryCountry ->
                            val action =
                                InternationalTransactionConfirmationFragmentDirections.actionInternationalTransactionConfirmationFragmentToTransferSuccessFragment2(
                                    "",
                                    args.senderCurrency,
                                    Utils.getFormattedCurrency(args.fxRateAmount),
                                    referenceNumber, position, beneficiaryCountry
                                )
                            findNavController().navigate(action)
                        }
                    }
                }
                /*   viewModel.state.position?.let { position ->
                       viewModel.state.beneficiaryCountry?.let { beneficiaryCountry ->
                           val action=
                           findNavController().navigate(action)
                       }

                   }*/

            }
        }
    }

    override fun onResume() {
        setObservers()
        super.onResume()
    }

    override fun onPause() {
        viewModel.clickEvent.removeObservers(this)
        super.onPause()
    }

    fun getBinding(): FragmentInternationalTransactionConfirmationBinding {
        return viewDataBinding as FragmentInternationalTransactionConfirmationBinding
    }

    private fun successOtpFlow() {
        if (context is BeneficiaryCashTransferActivity) {
            (context as BeneficiaryCashTransferActivity).viewModel.state.otpSuccess?.let { success ->
                if (success) {
                    callTransactionApi()
                }
                (context as BeneficiaryCashTransferActivity).viewModel.state.otpSuccess = false
            }
        }
    }

    private fun callTransactionApi() {
        (context as BeneficiaryCashTransferActivity).viewModel.state.beneficiary?.let { beneficiary ->
            beneficiary.beneficiaryType?.let { beneficiaryType ->
                if (beneficiaryType.isNotEmpty())
                    when (SendMoneyBeneficiaryType.valueOf(beneficiaryType)) {
                        SendMoneyBeneficiaryType.RMT -> {
                            beneficiary.id?.let { beneficiaryId ->
                                viewModel.rmtTransferRequest(beneficiaryId.toString())
                            }
                        }
                        SendMoneyBeneficiaryType.SWIFT -> {
                            beneficiary.id?.let { beneficiaryId ->
                                viewModel.swiftTransferRequest(beneficiaryId.toString())
                            }
                        }
                        else -> {

                        }
                    }
            }
        }

    }
}
