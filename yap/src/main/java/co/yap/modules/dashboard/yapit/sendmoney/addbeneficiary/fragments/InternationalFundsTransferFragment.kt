package co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.fragments

import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentInternationalFundsTransferBinding
import co.yap.modules.dashboard.yapit.sendmoney.activities.BeneficiaryCashTransferActivity
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.interfaces.IInternationalFundsTransfer
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.viewmodels.InternationalFundsTransferViewModel
import co.yap.modules.dashboard.yapit.sendmoney.fragments.SendMoneyBaseFragment
import co.yap.networking.transactions.responsedtos.InternationalFundsTransferReasonList
import co.yap.translation.Strings
import co.yap.translation.Translator
import co.yap.widgets.spinneradapter.ViewHolderArrayAdapter
import co.yap.yapcore.enums.SendMoneyBeneficiaryProductCode
import co.yap.yapcore.enums.SendMoneyBeneficiaryType
import co.yap.yapcore.helpers.CustomSnackbar
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.extentions.toast
import co.yap.yapcore.managers.MyUserManager
import kotlinx.android.synthetic.main.fragment_beneficiary_overview.*
import kotlinx.android.synthetic.main.fragment_international_funds_transfer.*


class InternationalFundsTransferFragment :
    SendMoneyBaseFragment<IInternationalFundsTransfer.ViewModel>(),
    IInternationalFundsTransfer.View {

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_international_funds_transfer
    override val viewModel: IInternationalFundsTransfer.ViewModel
        get() = ViewModelProviders.of(this).get(InternationalFundsTransferViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getBeneficiaryId()
        val productCode = getProductCode()
        viewModel.getMoneyTransferLimits(productCode)
        viewModel.getTransactionFeeInternational(productCode)
        viewModel.getReasonList(productCode)
        viewModel.getTransactionInternationalfxList(productCode)


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //successOtpFlow()
        getBindings().etSenderAmount.filters =
            arrayOf(InputFilter.LengthFilter(7), co.yap.widgets.DecimalDigitsInputFilter(2))
        getBindings().etSenderAmount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                if (p0?.length!! > 0) {
//                    getBindings().etSenderAmount.gravity = Gravity.CENTER
//                } else {
//                    getBindings().etSenderAmount.gravity = Gravity.START or Gravity.CENTER_VERTICAL
//                }
                if (p0?.length!! > 0) {
                    getBindings().etSenderAmount.gravity =
                        Gravity.CENTER_HORIZONTAL or Gravity.CENTER_VERTICAL
                } else {
                    getBindings().etSenderAmount.gravity =
                        Gravity.CENTER_HORIZONTAL or Gravity.CENTER_VERTICAL
                }
            }
        })
    }
//
//    private fun successOtpFlow() {
//        if (context is BeneficiaryCashTransferActivity) {
//            (context as BeneficiaryCashTransferActivity).viewModel.state.otpSuccess?.let { success ->
//                if (success) {
//                    callTransactionApi()
//                }
//                (context as BeneficiaryCashTransferActivity).viewModel.state.otpSuccess = false
//            }
//        }
//    }
//
//    private fun callTransactionApi() {
//        (context as BeneficiaryCashTransferActivity).viewModel.state.beneficiary?.let { beneficiary ->
//            beneficiary.beneficiaryType?.let { beneficiaryType ->
//                if (beneficiaryType.isNotEmpty())
//                    when (SendMoneyBeneficiaryType.valueOf(beneficiaryType)) {
//                        SendMoneyBeneficiaryType.RMT -> {
//                            beneficiary.id?.let { beneficiaryId ->
//                                viewModel.rmtTransferRequest(beneficiaryId.toString())
//                            }
//                        }
//                        SendMoneyBeneficiaryType.SWIFT -> {
//                            beneficiary.id?.let { beneficiaryId ->
//                                viewModel.swiftTransferRequest(beneficiaryId.toString())
//                            }
//                        }
//                        else -> {
//
//                        }
//                    }
//            }
//        }
//
//    }

    private fun setObservers() {
        viewModel.clickEvent.observe(this, clickEvent)
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
        reasonsSpinner.adapter = ViewHolderArrayAdapter(requireContext(), data, { parent ->
            CashTransferFragment.ReasonDropDownViewHolder.inflateSelectedView(parent)
        }, { parent ->
            CashTransferFragment.ReasonDropDownViewHolder.inflate(parent)
        }, { viewHolder, position, item ->
            viewHolder.bind(item)
        }, { viewHolder, position, item ->
            viewHolder.bind(item)
        })
        reasonsSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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
        reasonsSpinner.setSelection(viewModel.reasonPosition)
    }

    val clickEvent = Observer<Int> {
        when (it) {
            R.id.btnNext -> {
                if (viewModel.state.reasonTransferValue.equals("Select a Reason")) {
                    toast("Select a Reason")
                } else {

                    val availableBalance =
                        MyUserManager.cardBalance.value?.availableBalance?.toDouble()
                    if (availableBalance != null) {
                        val inputAmount = viewModel.state.fxRateAmount?.toDouble() ?: 0.0
                        +viewModel.state.transferFeeAmount

                        if (availableBalance > inputAmount) {
                            if (viewModel.state.fxRateAmount?.toDouble() ?: 0.0 < viewModel.state.minLimit ?: 0.0 || viewModel.state.fxRateAmount?.toDouble() ?: 0.0 > viewModel.state.maxLimit ?: 0.0) {

                                val errorDescription = getString(
                                    Strings.scren_send_money_funds_transfer_display_text_amount_error
                                ).format(
                                    Utils.getFormattedCurrency(viewModel.state.minLimit.toString()),
                                    Utils.getFormattedCurrency(viewModel.state.maxLimit.toString()),
                                    availableBalance.toString()
                                )
                                if (activity is BeneficiaryCashTransferActivity) {
                                    (activity as BeneficiaryCashTransferActivity).viewModel.errorEvent.value =
                                        errorDescription
                                }
                                //showSnackBarForLimits(errorDescription)
                            } else {
                                viewModel.state.position?.let { position ->
                                    viewModel.state.beneficiaryCountry?.let { beneficiaryCountry ->
                                        val action =
                                            InternationalFundsTransferFragmentDirections.actionInternationalFundsTransferFragmentToInternationalTransactionConfirmationFragment(
                                                viewModel.state.beneficiaryName,
                                                viewModel.state.senderCurrency.toString(),
                                                viewModel.state.fxRateAmount.toString(),
                                                viewModel.state.receiverCurrencyAmount.toString(),
                                                viewModel.state.toFxRateCurrency ?: "",
                                                viewModel.state.totalAmount.toString(),
                                                viewModel.state.fromFxRate.toString(),
                                                viewModel.state.toFxRate.toString(),
                                                viewModel.state.referenceNumber.toString(),
                                                position,
                                                beneficiaryCountry,
                                                viewModel.state.firstName
                                                    ?: viewModel.state.beneficiaryName,
                                                viewModel.state.fromFxRateCurrency?: "",
                                                viewModel.state.reasonTransferCode?: "",
                                                viewModel.state.transactionNote ?: "",
                                                viewModel.state.reasonTransferValue?: "",
                                                viewModel.state.rate?: "",
                                                viewModel.otpAction ?: ""
                                            )
                                        findNavController().navigate(action)
                                    }

                                }

                                // viewModel.createOtp(R.id.btnNext)
                            }

                            /*if (viewModel.state.minLimit != null && viewModel.state.maxLimit != null) {
                                if (inputAmount < viewModel.state.minLimit!!.toDouble() && inputAmount > viewModel.state.maxLimit!!.toDouble()) {
                                    showErrorSnackBar()
                                } else {
                                    viewModel.createOtp(R.id.btnNext)
                                }
                            }*/
                        } else {
                            val des = Translator.getString(
                                requireContext(),
                                Strings.screen_y2y_funds_transfer_display_text_error_exceeding_amount
                            )
                            if (activity is BeneficiaryCashTransferActivity) {
                                (activity as BeneficiaryCashTransferActivity).viewModel.errorEvent.value =
                                    des
                            }
                        }
                    }

//                val action =
//                    InternationalFundsTransferFragmentDirections.actionInternationalFundsTransferFragmentToGenericOtpLogoFragment(
//                        false,
//                        viewModel.otpAction.toString(),
//                        viewModel.state.fxRateAmount.toString()
//                    )
//                findNavController().navigate(action)
                }

            }
            200 -> {
                viewModel.state.position?.let { position ->
                    viewModel.state.beneficiaryCountry?.let { beneficiaryCountry ->
                        val action =
                            InternationalFundsTransferFragmentDirections.actionInternationalFundsTransferFragmentToGenericOtpLogoFragment(
                                false,
                                viewModel.otpAction.toString(),
                                viewModel.state.fxRateAmount.toString(),
                                position,
                                beneficiaryCountry
                            )
                        findNavController().navigate(action)
                    }
                }
            }
//            Constants.ADD_SUCCESS -> {
//                // Send Broadcast for updating transactions list in `Home Fragment`
//                val intent = Intent(Constants.BROADCAST_UPDATE_TRANSACTION)
//                LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
//                viewModel.state.position?.let { position ->
//                    viewModel.state.beneficiaryCountry?.let { beneficiaryCountry ->
//                        val action =
//                            InternationalFundsTransferFragmentDirections.actionInternationalFundsTransferFragmentToInternationalTransactionConfirmationFragment(
//                                viewModel.state.beneficiaryName,
//                                viewModel.state.senderCurrency.toString(),
//                                viewModel.state.fxRateAmount.toString(),
//                                viewModel.state.receiverCurrencyAmount.toString(),
//                                viewModel.state.toFxRateCurrency ?: "",
//                                viewModel.state.totalAmount.toString(),
//                                viewModel.state.fromFxRate.toString(),
//                                viewModel.state.toFxRate.toString(),
//                                viewModel.state.referenceNumber.toString(),
//                                position,
//                                beneficiaryCountry,
//                                viewModel.state.firstName ?: viewModel.state.beneficiaryName
//                            )
//                        findNavController().navigate(action)
//                    }
//
//                }
//
//            }
            R.id.viewSpinnerClickReason -> {
                reasonsSpinner.performClick()
            }
        }
    }

    private fun editBeneficiaryScreen() {
        etnickName.isEnabled = true
        etFirstName.isEnabled = true
        etLastName.isEnabled = true
        etAccountIbanNumber.isEnabled = true
        etnickName.isEnabled = true
        etSwiftCode.isEnabled = true
        etBankREquiredFieldCode.isEnabled = true
    }


    override fun onBackPressed(): Boolean {
        return super.onBackPressed()
    }

    private fun getProductCode(): String {

        if (context is BeneficiaryCashTransferActivity) {
            (context as BeneficiaryCashTransferActivity).let { beneficiaryCashTransaferActivity ->
                beneficiaryCashTransaferActivity.viewModel.state.toolBarTitle = getString(
                    Strings.screen_funds_toolbar_header
                )
                viewModel.state.position = beneficiaryCashTransaferActivity.viewModel.state.position
            }
            (context as BeneficiaryCashTransferActivity).viewModel.state.beneficiary?.let { beneficiary ->
                viewModel.state.beneficiaryCountry = beneficiary.country
                viewModel.state.beneficiaryName = beneficiary.fullName()
                viewModel.state.firstName = beneficiary.firstName
                beneficiary.beneficiaryType?.let { beneficiaryType ->
                    if (beneficiaryType.isNotEmpty())
                        return when (SendMoneyBeneficiaryType.valueOf(beneficiaryType)) {
                            SendMoneyBeneficiaryType.RMT -> {
                                viewModel.otpAction = SendMoneyBeneficiaryType.RMT.name
                                SendMoneyBeneficiaryProductCode.P012.name
                            }
                            SendMoneyBeneficiaryType.SWIFT -> {
                                viewModel.otpAction = SendMoneyBeneficiaryType.SWIFT.name
                                SendMoneyBeneficiaryProductCode.P011.name
                            }
                            else -> {
                                ""
                            }
                        }
                }
            }
        }
        return ""

    }

    private fun getBeneficiaryId() {
        if (context is BeneficiaryCashTransferActivity) {
            (context as BeneficiaryCashTransferActivity).viewModel.state.beneficiary?.let { beneficiary ->
                beneficiary.id?.let { beneficiaryId ->
                    viewModel.state.beneficiaryId = beneficiaryId.toString()
                }
            }
        }
    }


//    private fun callTransactionApi() {
//        (context as BeneficiaryCashTransferActivity).viewModel.state.beneficiary?.let { beneficiary ->
//            beneficiary.beneficiaryType?.let { beneficiaryType ->
//                if (beneficiaryType.isNotEmpty())
//                    when (SendMoneyBeneficiaryType.valueOf(beneficiaryType)) {
//                        SendMoneyBeneficiaryType.RMT -> {
//                            beneficiary.id?.let { beneficiaryId ->
//                                viewModel.rmtTransferRequest(beneficiaryId.toString())
//                            }
//                        }
//                        SendMoneyBeneficiaryType.SWIFT -> {
//                            beneficiary.id?.let { beneficiaryId ->
//                                viewModel.swiftTransferRequest(beneficiaryId.toString())
//                            }
//                        }
//                        else -> {
//
//                        }
//                    }
//            }
//        }
//    }

//    private fun showErrorSnackBar() {
//        val des = Translator.getString(
//            requireContext(),
//            Strings.screen_y2y_funds_transfer_display_text_error_exceeding_amount
//        )
//        CustomSnackbar.showErrorCustomSnackbar(
//            context = requireContext(),
//            layout = clFTSnackbar,
//            message = des
//        )
//    }

    override fun onResume() {
        super.onResume()
        setObservers()
    }


    override fun onPause() {
        super.onPause()
        viewModel.clickEvent.removeObservers(this)
        viewModel.populateSpinnerData.removeObservers(this)
    }

    fun getBindings(): FragmentInternationalFundsTransferBinding {
        return viewDataBinding as FragmentInternationalFundsTransferBinding
    }
}