package co.yap.modules.dashboard.yapit.y2y.transfer.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.Gravity
import android.view.Gravity.CENTER_VERTICAL
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentY2yFundsTransferBinding
import co.yap.modules.dashboard.yapit.y2y.main.fragments.Y2YBaseFragment
import co.yap.modules.dashboard.yapit.y2y.transfer.interfaces.IY2YFundsTransfer
import co.yap.modules.dashboard.yapit.y2y.transfer.viewmodels.Y2YFundsTransferViewModel
import co.yap.modules.otp.GenericOtpFragment
import co.yap.modules.otp.OtpDataModel
import co.yap.modules.otp.OtpToolBarData
import co.yap.translation.Strings
import co.yap.translation.Translator
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.enums.OTPActions
import co.yap.yapcore.helpers.DecimalDigitsInputFilter
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.cancelAllSnackBar
import co.yap.yapcore.helpers.extentions.startFragmentForResult
import co.yap.yapcore.helpers.showSnackBar
import co.yap.yapcore.helpers.spannables.color
import co.yap.yapcore.helpers.spannables.getText
import co.yap.yapcore.managers.MyUserManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_y2y_funds_transfer.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class Y2YTransferFragment : Y2YBaseFragment<IY2YFundsTransfer.ViewModel>(), IY2YFundsTransfer.View {
    val args: Y2YTransferFragmentArgs by navArgs()

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_y2y_funds_transfer

    override val viewModel: Y2YFundsTransferViewModel
        get() = ViewModelProviders.of(this).get(Y2YFundsTransferViewModel::class.java)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.state.availableBalance = MyUserManager.cardBalance.value?.availableBalance
        viewModel.getTransactionFee()
        setObservers()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpData()
    }

    override fun setObservers() {
        viewModel.clickEvent.observe(this, clickEvent)
        viewModel.enteredAmount.observe(this, enterAmountObserver)
        viewModel.errorEvent.observe(this, Observer {
            showSnackBar(
                msg = viewModel.state.errorDescription,
                viewBgColor = R.color.errorLightBackground,gravity = Gravity.TOP,
                colorOfMessage = R.color.error, duration = Snackbar.LENGTH_LONG
            )
        })
        viewModel.transferFundSuccess.observe(this, transferFundSuccessObserver)


    }

    private val transferFundSuccessObserver = Observer<Boolean> {
        if (it) {
            moveToFundTransferSuccess()
        }
    }
    private val enterAmountObserver = Observer<String> {
        when {
            isBalanceAvailable(it) -> showErrorSnackBar(true)
            //isDailyLimitReached() -> showErrorSnackBar(true)
            else -> showErrorSnackBar(false)
        }
    }

    private fun isBalanceAvailable(enteredAmount: String?): Boolean {
        return viewModel.state.checkValidity(enteredAmount ?: "").isNotBlank()
    }

    val clickEvent = Observer<Int> {
        when (it) {
            R.id.btnConfirm -> {
                when {
                    viewModel.enteredAmount.value?.toDoubleOrNull() ?: 0.0 < viewModel.state.minLimit || viewModel.enteredAmount.value?.toDoubleOrNull() ?: 0.0 > viewModel.state.maxLimit -> {
                        setUpperLowerLimitError()
                        viewModel.errorEvent.call()
                    }
//                    isDailyLimitReached() -> {
//                        viewModel.errorEvent.call()
//                    }
                    isOtpRequired() -> {
                        createOtp()
                    }
                    else -> {
                        viewModel.proceedToTransferAmount()
                    }
                }
            }
            Constants.CARD_FEE -> {
                viewModel.state.transferFee =
                    resources.getText(
                        getString(Strings.common_text_fee), requireContext().color(
                            R.color.colorPrimaryDark,
                            "${viewModel.state.currencyType} ${Utils.getFormattedCurrency(viewModel.state.fee)}"
                        )
                    )
            }

        }
    }

    private fun setUpperLowerLimitError() {
        viewModel.state.errorDescription = Translator.getString(
            requireContext(),
            Strings.common_display_text_min_max_limit_error_transaction,
            Utils.getFormattedCurrency(viewModel.state.minLimit.toString()),
            Utils.getFormattedCurrency(viewModel.state.maxLimit.toString())

        )
    }

    private fun createOtp() {
        startFragmentForResult<GenericOtpFragment>(
            GenericOtpFragment::class.java.name,
            bundleOf(
                OtpDataModel::class.java.name to OtpDataModel(
                    OTPActions.Y2Y.name,
                    MyUserManager.user?.currentCustomer?.getCompletePhone(),
                    MyUserManager.user?.currentCustomer?.getFullName(),
                    false,
                    OtpToolBarData()
                )
            )
        ) { resultCode, data ->
            if (resultCode == Activity.RESULT_OK) {
                viewModel.proceedToTransferAmount()
            }
        }
    }

    private fun setUpData() {

        viewModel.state.fullName = args.beneficiaryName
        viewModel.receiverUUID = args.receiverUUID
        viewModel.state.imageUrl = args.imagePath


        getBinding().lyUserImage.tvNameInitials.background = Utils.getContactBackground(
            getBinding().lyUserImage.tvNameInitials.context,
            args.position
        )


        getBinding().lyUserImage.tvNameInitials.setTextColor(
            Utils.getContactColors(
                getBinding().lyUserImage.tvNameInitials.context, args.position
            )
        )

        viewModel.state.availableBalanceText =
            " " + getString(Strings.common_text_currency_type) + " " + Utils.getFormattedCurrency(
                viewModel.state.availableBalance
            )
        etAmount.filters =
            arrayOf(InputFilter.LengthFilter(7), DecimalDigitsInputFilter(2))
        etAmount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) =
                if (p0?.length!! > 0) {
                    etAmount.gravity = Gravity.CENTER_HORIZONTAL or CENTER_VERTICAL
                } else {
                    etAmount.gravity = Gravity.CENTER_HORIZONTAL or CENTER_VERTICAL
                }
        })
    }

    private fun isDailyLimitReached(): Boolean {
        viewModel.transactionThreshold.value?.let {
            it.dailyLimit?.let { dailyLimit ->
                it.totalDebitAmount?.let { totalConsumedAmount ->
                    viewModel.enteredAmount.value?.toDoubleOrNull()?.let { enteredAmount ->
                        val remainingDailyLimit =
                            if ((dailyLimit - totalConsumedAmount) < 0.0) 0.0 else (dailyLimit - totalConsumedAmount)
                        viewModel.state.errorDescription =
                            if (enteredAmount > dailyLimit) getString(Strings.common_display_text_daily_limit_error_single_transaction) else getString(
                                Strings.common_display_text_daily_limit_error_single_transaction
                            )
                        return enteredAmount > remainingDailyLimit

                    } ?: return false
                } ?: return false
            } ?: return false
        } ?: return false
    }

    private fun isOtpRequired(): Boolean {
        viewModel.transactionThreshold.value?.let {
            it.totalDebitAmountY2Y?.let { totalY2YConsumedAmount ->
                viewModel.enteredAmount.value?.toDoubleOrNull()?.let { enteredAmount ->
                    val remainingOtpLimit = it.otpLimitY2Y?.minus(totalY2YConsumedAmount)
                    return enteredAmount > (remainingOtpLimit ?: 0.0)
                } ?: return false
            } ?: return false
        } ?: return false
    }

    private fun moveToFundTransferSuccess() {
        // Send Broadcast for updating transactions list in `Home Fragment`
        val intent = Intent(Constants.BROADCAST_UPDATE_TRANSACTION)
        LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)

        val action =
            Y2YTransferFragmentDirections.actionY2YTransferFragmentToY2YFundsTransferSuccessFragment(
                viewModel.state.fullName,
                "AED",
                viewModel.enteredAmount.value ?: "", args.position
            )
        findNavController().navigate(action)
    }

    //    private fun showErrorSnackBar(isShowBar: Boolean) {
//        if (isShowBar)
//        CustomSnackbar.showErrorCustomSnackbar(
//            context = requireContext(),
//            layout = getBinding().clFTSnackbar,
//            message = viewModel.state.errorDescription,
//            duration = Snackbar.LENGTH_INDEFINITE
//        )
//        else
//            CustomSnackbar.cancelAllSnackBar()
//    }
    private fun showErrorSnackBar(isShowBar: Boolean) {
        if (isShowBar)
            viewModel.parentViewModel?.errorEvent?.value = viewModel.state.errorDescription
        else
            viewModel.parentViewModel?.errorEvent?.value = null

    }

    override fun onDestroy() {
        viewModel.clickEvent.removeObservers(this)
        cancelAllSnackBar()
        viewModel.enteredAmount.removeObservers(this)
        super.onDestroy()

    }

    override fun getBinding(): FragmentY2yFundsTransferBinding {
        return viewDataBinding as FragmentY2yFundsTransferBinding
    }

    override fun onBackPressed(): Boolean {
        viewModel.parentViewModel?.state?.rightButtonVisibility = View.VISIBLE
        return super.onBackPressed()
    }

}