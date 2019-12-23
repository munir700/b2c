package co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
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
import co.yap.yapcore.helpers.Utils

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
    }

    private fun setUpViews() {
        if (activity is BeneficiaryCashTransferActivity) {
            setData()
            (activity as BeneficiaryCashTransferActivity).viewModel.state.toolBarVisibility = false

        }
    }

    override fun setData() {
        viewModel.state.name = args.name
        viewModel.state.confirmHeading =
            getString(Strings.screen_cash_pickup_funds_display_otp_header)

        viewModel.state.referenceNumber = args.referenceNumber
        viewModel.state.beneficiaryCountry = args.country


        viewModel.state.transferDescription =
            getString(Strings.screen_funds_confirmation_success_description).format(
                args.transferAmountCurrency,
                Utils.getFormattedCurrency(args.transferAmount),
                viewModel.state.name,
                args.fromAmount,
                args.toAmount
            )

        viewModel.state.position = args.position

        viewModel.state.receivingAmountDescription =
            getString(Strings.screen_funds_receive_description).format(
                viewModel.state.name,
                args.toAmount
            )

        viewModel.state.transferFeeDescription =
            getString(Strings.screen_funds_transfer_fee_description).format(args.transferFee)

        /* val feeSpannable = SpannableString(viewModel.state.transferFeeDescription)
         Utils.setSpan(
             5,c
             args.transferFee.length,
             feeSpannable,
             ContextCompat.getColor(requireContext(), R.color.colorPrimaryDark)
         )
         getBinding().tvFeeDescription.text = Utils.setSpan(
             20,
             args.transferFee.length,
             feeSpannable,
             ContextCompat.getColor(requireContext(), R.color.colorPrimaryDark)
         )*/
    }

    override fun setObservers() {
        viewModel.clickEvent.observe(this, clickEvent)
    }

    val clickEvent = Observer<Int> {
        when (it) {
            R.id.confirmButton -> {
                viewModel.state.referenceNumber?.let { referenceNumber ->
                    viewModel.state.position?.let { position ->
                        viewModel.state.beneficiaryCountry?.let { beneficiaryCountry ->
                            val action =
                                InternationalTransactionConfirmationFragmentDirections.actionInternationalTransactionConfirmationFragmentToTransferSuccessFragment2(
                                    "",
                                    args.transferAmountCurrency,
                                    Utils.getFormattedCurrency(args.transferAmount),
                                    referenceNumber, position, beneficiaryCountry
                                )
                            findNavController().navigate(action)
                        }
                    }
                }

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

    override fun onBackPressed(): Boolean {
        return true
    }

    fun getBinding(): FragmentInternationalTransactionConfirmationBinding {
        return viewDataBinding as FragmentInternationalTransactionConfirmationBinding
    }
}
