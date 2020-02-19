package co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.fragment.findNavController
import co.yap.R
import co.yap.databinding.FragmentCashTransferConfirmationBinding
import co.yap.modules.dashboard.yapit.sendmoney.activities.BeneficiaryCashTransferActivity
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.interfaces.ICashTransferConfirmation
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.viewmodels.CashTransferConfirmationViewModel
import co.yap.modules.otp.GenericOtpFragment
import co.yap.modules.otp.OtpDataModel
import co.yap.translation.Strings
import co.yap.yapcore.BR
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.enums.OTPActions
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.extentions.startFragmentForResult
import co.yap.yapcore.helpers.spannables.color
import co.yap.yapcore.helpers.spannables.getText
import co.yap.yapcore.managers.MyUserManager

class CashTransferConfirmationFragment :
    BaseBindingFragment<ICashTransferConfirmation.ViewModel>(), ICashTransferConfirmation.View {
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_cash_transfer_confirmation

    override val viewModel: CashTransferConfirmationViewModel
        get() = ViewModelProviders.of(this).get(CashTransferConfirmationViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addObservers()
        if (context is BeneficiaryCashTransferActivity) {
            (context as BeneficiaryCashTransferActivity).let { activity ->
                viewModel.beneficiary =
                    activity.viewModel.state.beneficiary
                activity.viewModel.state.leftButtonVisibility = true
                activity.viewModel.state.rightButtonVisibility = false
                activity.viewModel.state.toolBarTitle = "Confirm transfer"
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        receiveDataFromArgs()
        setTransferAmountString()
        setTransferFeeAmountString()
    }

    private fun receiveDataFromArgs() {
        viewModel.state.enteredAmount.set(arguments?.let {
            CashTransferConfirmationFragmentArgs.fromBundle(
                it
            ).amount
        })
        viewModel.reasonCode =
            arguments?.let { CashTransferConfirmationFragmentArgs.fromBundle(it).reasonCode }
                .toString()
        viewModel.reason =
            arguments?.let { CashTransferConfirmationFragmentArgs.fromBundle(it).reason }.toString()
        viewModel.transferNote =
            arguments?.let { CashTransferConfirmationFragmentArgs.fromBundle(it).transferNote }
                .toString()

        viewModel.state.transferFee.set(
            arguments?.let { CashTransferConfirmationFragmentArgs.fromBundle(it).transferFee }
                .toString())

        viewModel.state.position.set(
            arguments?.let { CashTransferConfirmationFragmentArgs.fromBundle(it).position })


    }

    private fun setTransferAmountString() {
        viewModel.state.description.set(
            resources.getText(
                getString(Strings.scren_send_money_funds_transfer_confirmation_display_text_amount_uaefts)
                ,
                //viewModel.state.name
                viewModel.beneficiary?.firstName,
                requireContext().color(
                    R.color.colorPrimaryDark,
                    "${"AED"} ${Utils.getFormattedCurrency(viewModel.state.enteredAmount.get())}"
                )
            )
        )
    }

    private fun setTransferFeeAmountString() {
        viewModel.state.transferFeeDescription.set(
            resources.getText(
                getString(Strings.scren_send_money_funds_transfer_confirmation_display_text_fee),
                requireContext().color(
                    R.color.colorPrimaryDark,
                    "${"AED"} ${Utils.getFormattedCurrency(viewModel.state.transferFee.get())}"
                )
            )
        )
    }

    override fun addObservers() {
        viewModel.clickEvent.observe(this, clickObserver)
    }

    override fun removeObservers() {
        viewModel.clickEvent.removeObserver(clickObserver)
    }

    private val clickObserver = Observer<Int> {
        when (it) {
            R.id.confirmButton -> {
                if (isOtpRequired()) {
                    startOtpFragment()
                } else {
                    viewModel.proceedToTransferAmount()
                }
            }
            Constants.ADD_CASH_PICK_UP_SUCCESS -> {
                cashTransferSuccess()
            }
        }
    }

    private fun cashTransferSuccess() {
        // Send Broadcast for updating transactions list in `Home Fragment`
        val intent = Intent(Constants.BROADCAST_UPDATE_TRANSACTION)
        LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
        viewModel.state.referenceNumber?.let { referenceNumber ->
            val action =
                CashTransferConfirmationFragmentDirections.actionCashTransferConfirmationFragmentToTransferSuccessFragment2(
                    "",
                    "AED",
                    Utils.getFormattedCurrency(viewModel.state.enteredAmount.get()),
                    viewModel.state.referenceNumber.get().toString(),
                    viewModel.state.position.get()?:0
                )
            findNavController().navigate(action)
        }
    }

    private fun isOtpRequired(): Boolean {
        viewModel.transactionThreshold.value?.let {
            it.totalDebitAmountRemittance?.let { totalSMConsumedAmount ->
                viewModel.state.enteredAmount.get()?.toDoubleOrNull()?.let { enteredAmount ->
                    val remainingOtpLimit = it.otpLimit?.minus(totalSMConsumedAmount)
                    return enteredAmount > (remainingOtpLimit ?: 0.0)
                } ?: return false
            } ?: return false
        } ?: return false
    }

    private fun startOtpFragment() {
        startFragmentForResult<GenericOtpFragment>(
            GenericOtpFragment::class.java.name,
            bundleOf(
                OtpDataModel::class.java.name to OtpDataModel(
                    OTPActions.UAEFTS.name,
                    MyUserManager.user?.currentCustomer?.getFormattedPhoneNumber(requireContext()),
                    MyUserManager.user?.currentCustomer?.getFullName(),
                    false
                )
            )
        ) { resultCode, _ ->
            if (resultCode == Activity.RESULT_OK) {
                viewModel.proceedToTransferAmount()
            }
        }
    }


    override fun getViewBinding(): FragmentCashTransferConfirmationBinding {
        return (viewDataBinding as FragmentCashTransferConfirmationBinding)
    }

    override fun onDestroy() {
        super.onDestroy()
        removeObservers()
    }
}