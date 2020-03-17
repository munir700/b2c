package co.yap.sendmoney.addbeneficiary.fragments

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.Gravity
import android.view.Gravity.CENTER_VERTICAL
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import co.yap.sendmoney.R
import co.yap.databinding.FragmentCashTransferBinding
import co.yap.sendmoney.activities.BeneficiaryCashTransferActivity
import co.yap.sendmoney.addbeneficiary.interfaces.ICashTransfer
import co.yap.sendmoney.addbeneficiary.viewmodels.CashTransferViewModel
import co.yap.sendmoney.fragments.SendMoneyBaseFragment
import co.yap.networking.transactions.responsedtos.InternationalFundsTransferReasonList
import co.yap.translation.Strings
import co.yap.translation.Translator
import co.yap.widgets.spinneradapter.ViewHolderArrayAdapter
import co.yap.yapcore.BR
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.enums.SendMoneyBeneficiaryProductCode
import co.yap.yapcore.enums.SendMoneyBeneficiaryType
import co.yap.yapcore.helpers.DecimalDigitsInputFilter
import co.yap.yapcore.helpers.cancelAllSnackBar
import co.yap.yapcore.helpers.extentions.toFormattedCurrency
import co.yap.yapcore.helpers.spannables.color
import co.yap.yapcore.helpers.spannables.getText
import co.yap.yapcore.managers.MyUserManager
import kotlinx.android.synthetic.main.fragment_cash_transfer.*
import kotlinx.android.synthetic.main.fragment_y2y_funds_transfer.etAmount

class CashTransferFragment : SendMoneyBaseFragment<ICashTransfer.ViewModel>(), ICashTransfer.View {

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_cash_transfer

    override val viewModel: CashTransferViewModel
        get() = ViewModelProviders.of(this).get(CashTransferViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (context is BeneficiaryCashTransferActivity) {
            viewModel.state.beneficiary =
                (context as BeneficiaryCashTransferActivity).viewModel.state.beneficiary
        }
        viewModel.state.produceCode = getProductCode()
        startFlows()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpData()
        if (viewModel.transactionData.size > 0)
            setSpinnerAdapter(viewModel.transactionData)

    }

    override fun setObservers() {
        viewModel.clickEvent.observe(this, clickEvent)
        viewModel.errorEvent.observe(this, Observer {
            showErrorSnackBar()
        })

        viewModel.populateSpinnerData.observe(this, Observer {
            if (it == null) return@Observer
            setSpinnerAdapter(it)
        })
    }

    private fun setSpinnerAdapter(list: ArrayList<InternationalFundsTransferReasonList.ReasonList>) {
        val data = ArrayList<InternationalFundsTransferReasonList.ReasonList>()
        data.addAll(list)
        data.add(
            0,
            InternationalFundsTransferReasonList.ReasonList("Select a Reason", "0")
        )
        reasonsSpinnerCashTransfer.adapter =
            ViewHolderArrayAdapter(requireContext(), data, { parent ->
                ReasonDropDownViewHolder.inflateSelectedView(parent)
            }, { parent ->
                ReasonDropDownViewHolder.inflate(parent)
            }, { viewHolder, position, item ->
                viewHolder.bind(item)
            }, { viewHolder, position, item ->
                viewHolder.bind(item)
            })
        reasonsSpinnerCashTransfer.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    viewModel.reasonPosition = position
                    viewModel.state.reasonTransferValue = data[position].reason
                    viewModel.state.reasonTransferCode = data[position].code
                }
            }
        reasonsSpinnerCashTransfer.setSelection(viewModel.reasonPosition)
    }

    val clickEvent = Observer<Int> {
        when (it) {
            R.id.btnConfirm -> {
                if (isUaeftsBeneficiary())
                    moveToConfirmationScreen()
                else
                    moveToOtp()

            }
            R.id.viewTriggerSpinnerClickReasonCash -> {
                reasonsSpinnerCashTransfer.performClick()
            }

            Constants.ADD_CASH_PICK_UP_SUCCESS -> {
                // Send Broadcast for updating transactions list in `Home Fragment`
                val intent = Intent(Constants.BROADCAST_UPDATE_TRANSACTION)
                LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
                viewModel.state.referenceNumber?.let { referenceNumber ->
                    val action =
                        CashTransferFragmentDirections.actionCashTransferFragmentToTransferSuccessFragment2(
                            "",
                            viewModel.state.currencyType,
                            viewModel.state.amount.toFormattedCurrency()?:"",
                            referenceNumber,
                            viewModel.state.position
                        )
                    findNavController().navigate(action)
                }
            }
        }
    }

    private fun isUaeftsBeneficiary(): Boolean {
        viewModel.state.beneficiary?.beneficiaryType?.let {
            return (it == SendMoneyBeneficiaryType.UAEFTS.type||it == SendMoneyBeneficiaryType.DOMESTIC.type)
        } ?: return false
    }

    private fun moveToOtp() {
        val action =
            CashTransferFragmentDirections.actionCashTransferFragmentToGenericOtpLogoFragment(
                false,
                viewModel.state.otpAction ?: "",
                viewModel.state.amount
                , viewModel.state.position

            )
        findNavController().navigate(action)
    }


    private fun moveToConfirmationScreen() {
        val action =
            CashTransferFragmentDirections.actionCashTransferFragmentToCashTransferConfirmationFragment(
                viewModel.state.amount,
                viewModel.state.reasonTransferCode.toString(),
                viewModel.state.reasonTransferValue.toString(),
                viewModel.state.noteValue?:"",
                viewModel.state.originalTransferFeeAmount.get().toString(),
                viewModel.state.position
            )
        findNavController().navigate(action)

    }

    private fun setUpData() {
        if (context is BeneficiaryCashTransferActivity) {
            (context as BeneficiaryCashTransferActivity).viewModel.state.otpSuccess?.let {
                if (it) {
                    viewModel.proceedToTransferAmount()
                }
                (context as BeneficiaryCashTransferActivity).viewModel.state.otpSuccess = false
            }
        }

        if (activity is BeneficiaryCashTransferActivity) {
            (activity as BeneficiaryCashTransferActivity).let { it ->
                it.viewModel.state.leftButtonVisibility = false
                it.viewModel.state.rightButtonVisibility = true
                if (it.viewModel.state.beneficiary?.beneficiaryType.equals(SendMoneyBeneficiaryType.CASHPAYOUT.type)) {
                    it.viewModel.state.toolBarTitle =
                        getString(Strings.screen_cash_pickup_funds_display_text_header)
                } else {
                    it.viewModel.state.toolBarTitle =
                        getString(Strings.screen_funds_local_toolbar_header)
                }

                viewModel.state.position = it.viewModel.state.position
                it.viewModel.state.beneficiary?.let {
                    viewModel.state.fullName = "${it.firstName} ${it.lastName}"
                }
            }
        }

        viewModel.state.availableBalanceText =
            " " + getString(Strings.common_text_currency_type) + " " +
                viewModel.state.availableBalance?.toFormattedCurrency()


        etAmount.filters =
            arrayOf(InputFilter.LengthFilter(7), DecimalDigitsInputFilter(2))
        etAmount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                viewModel.state.clearError()
                if (viewModel.state.feeType == Constants.FEE_TYPE_TIER) {
                    if (viewModel.state.amount.isNotEmpty() && viewModel.state.amount != ".") {
                        viewModel.state.setSpannableFee(viewModel.state.findFee(viewModel.state.amount.toDouble()).toString())
                    } else {
                        viewModel.state.setSpannableFee("0.0")
                    }
                }
                if (viewModel.state.amount.isNotEmpty()) {
                    val totalAmount = viewModel.state.amount.toDoubleOrNull() ?: 0.0.plus(
                        viewModel.state.transferFeeAmount
                    )
                    viewModel.state.totalTransferAmount.set(totalAmount)
                    if (isBalanceAvailable()) {
                        if (isDailyLimitReached()) {
                            showLimitError()
                            viewModel.state.valid = false
                        } else {
                            cancelAllSnackBar()
                            viewModel.state.valid = true
                        }
                    } else {
                        viewModel.state.valid = false
                        showBalanceNotAvailableError()
                    }
                }

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0?.length!! > 0) {
                    etAmount.gravity = Gravity.CENTER_HORIZONTAL or CENTER_VERTICAL
                } else {
                    etAmount.gravity = Gravity.CENTER_HORIZONTAL or CENTER_VERTICAL
                }


            }
        })
    }

    private fun showBalanceNotAvailableError() {
        val des = Translator.getString(
            requireContext(),
            Strings.common_display_text_available_balance_error
        ).format(MyUserManager.cardBalance.value?.availableBalance?.toFormattedCurrency())
        if (activity is BeneficiaryCashTransferActivity) {
            (activity as BeneficiaryCashTransferActivity).viewModel.errorEvent.value =
                des
        }
    }

    private fun showLimitError() {
        if (activity is BeneficiaryCashTransferActivity) {
            (activity as BeneficiaryCashTransferActivity).viewModel.errorEvent.value =
                viewModel.state.errorDescription
        }
    }

    private fun isBalanceAvailable(): Boolean {
        val availableBalance =
            MyUserManager.cardBalance.value?.availableBalance?.toDoubleOrNull()
        if (availableBalance != null) {
            val totalTransferAmount = viewModel.state.amount.toDoubleOrNull() ?: 0.0
            +viewModel.state.transferFeeAmount

            return (availableBalance > totalTransferAmount)
        } else
            return false
    }

    private fun isDailyLimitReached(): Boolean {
        viewModel.transactionThreshold.value?.let {
            it.dailyLimit?.let { dailyLimit ->
                it.totalDebitAmount?.let { totalConsumedAmount ->
                    viewModel.state.totalTransferAmount.get()?.let { enteredAmount ->
                        val remainingDailyLimit =
                            if ((dailyLimit - totalConsumedAmount) < 0.0) 0.0 else (dailyLimit - totalConsumedAmount)

                        viewModel.state.errorDescription =
                            if (enteredAmount > dailyLimit) getString(Strings.common_display_text_daily_limit_error_single_transaction) else getString(
                                Strings.common_display_text_daily_limit_error_single_transaction
                            )

                        return (enteredAmount >= remainingDailyLimit)

                    } ?: return false
                } ?: return false
            } ?: return false
        } ?: return false

    }

    private fun showErrorSnackBar() {
        if (activity is BeneficiaryCashTransferActivity) {
            (activity as BeneficiaryCashTransferActivity).viewModel.errorEvent.value =
                viewModel.state.errorDescription
        }
    }

    private fun startFlows() {
        viewModel.state.beneficiary?.beneficiaryType?.let { beneficiaryType ->
            if (beneficiaryType.isNotEmpty())
                when (SendMoneyBeneficiaryType.valueOf(beneficiaryType)) {
                    //RMT is for international( RMT(linked with Rak))
                    SendMoneyBeneficiaryType.RMT -> {
                        skipCashTransferFragment()
                    }
                    //Swift is for international(non RMT(Not linked with Rak))
                    SendMoneyBeneficiaryType.SWIFT -> {
                        skipCashTransferFragment()
                    }
                    else -> {
                        viewModel.state.availableBalance =
                            MyUserManager.cardBalance.value?.availableBalance

                        viewModel.state.availableBalanceString =
                            resources.getText(
                                getString(Strings.screen_cash_transfer_display_text_available_balance), requireContext().color(
                                    R.color.colorPrimaryDark,
                                    "${"AED"} ${viewModel.state.availableBalance?.toFormattedCurrency()}"
                                )
                            )
                        viewModel.state.setSpannableFee("0.0")
                        viewModel.getMoneyTransferLimits(viewModel.state.produceCode)
                        viewModel.getTransactionFeeForCashPayout(viewModel.state.produceCode)
                        viewModel.getCashTransferReasonList()
                        setObservers()
                    }
                }
        }
    }

    private fun getProductCode(): String {
        if (context is BeneficiaryCashTransferActivity) {
            (context as BeneficiaryCashTransferActivity).viewModel.state.beneficiary?.let { beneficiary ->
                viewModel.state.beneficiaryCountry = beneficiary.country
                beneficiary.beneficiaryType?.let { beneficiaryType ->
                    if (beneficiaryType.isNotEmpty())
                        when (SendMoneyBeneficiaryType.valueOf(beneficiaryType)) {
                            SendMoneyBeneficiaryType.RMT -> {
                                viewModel.state.otpAction = SendMoneyBeneficiaryType.RMT.type
                                viewModel.state.produceCode =
                                    SendMoneyBeneficiaryProductCode.P012.name
                                return viewModel.state.produceCode ?: ""
                            }
                            SendMoneyBeneficiaryType.SWIFT -> {
                                viewModel.state.otpAction = SendMoneyBeneficiaryType.SWIFT.type

                                viewModel.state.produceCode =
                                    SendMoneyBeneficiaryProductCode.P011.name
                                return viewModel.state.produceCode ?: ""
                            }
                            SendMoneyBeneficiaryType.CASHPAYOUT -> {
                                viewModel.state.otpAction = SendMoneyBeneficiaryType.CASHPAYOUT.type

                                viewModel.state.reasonsVisibility = false
                                viewModel.state.produceCode =
                                    SendMoneyBeneficiaryProductCode.P013.name
                                return viewModel.state.produceCode ?: ""
                            }
                            SendMoneyBeneficiaryType.DOMESTIC -> {
                                viewModel.state.otpAction =
                                    SendMoneyBeneficiaryType.DOMESTIC_TRANSFER.type
                                viewModel.state.produceCode =
                                    SendMoneyBeneficiaryProductCode.P023.name
                                viewModel.state.ibanVisibility = true
                                viewModel.state.ibanNumber = beneficiary.accountNo
                                return viewModel.state.produceCode ?: ""
                            }
                            /*SendMoneyBeneficiaryType.INTERNAL_TRANSFER -> {
                                //call service for INTERNAL_TRANSFER

                            }*/
                            SendMoneyBeneficiaryType.UAEFTS -> {
                                viewModel.state.otpAction = SendMoneyBeneficiaryType.UAEFTS.type
                                viewModel.state.produceCode =
                                    SendMoneyBeneficiaryProductCode.P010.name
                                viewModel.state.ibanVisibility = true
                                viewModel.state.ibanNumber = beneficiary.accountNo
                                return viewModel.state.produceCode ?: ""
                            }
                            else -> {
                                return SendMoneyBeneficiaryProductCode.P010.name
                            }
                        }
                }
            }
        }
        return ""
    }

    override fun onDestroy() {
        viewModel.clickEvent.removeObservers(this)
        super.onDestroy()
    }

    private fun skipCashTransferFragment() {
        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.cashTransferFragment, true) // starting destination skiped
            .build()

        findNavController().navigate(
            R.id.action_cashTransferFragment_to_internationalFundsTransferFragment,
            null,
            navOptions
        )

    }

    class ReasonDropDownViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        companion object {
            fun inflate(parent: ViewGroup): ReasonDropDownViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_spinner, parent, false)
                return ReasonDropDownViewHolder(view)
            }

            fun inflateSelectedView(parent: ViewGroup): ReasonDropDownViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_selected_spinner, parent, false)
                return ReasonDropDownViewHolder(view)
            }
        }

        var title: TextView = view.findViewById(R.id.textView)


        fun bind(reason: InternationalFundsTransferReasonList.ReasonList) {
            title.text = reason.reason
        }
    }

    fun getBindings(): FragmentCashTransferBinding {
        return viewDataBinding as FragmentCashTransferBinding
    }
}