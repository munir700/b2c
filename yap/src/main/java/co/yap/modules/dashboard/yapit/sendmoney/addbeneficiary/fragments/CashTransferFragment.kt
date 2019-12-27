package co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.fragments

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.Gravity
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
import co.yap.R
import co.yap.databinding.FragmentCashTransferBinding
import co.yap.modules.dashboard.yapit.sendmoney.activities.BeneficiaryCashTransferActivity
import co.yap.modules.dashboard.yapit.sendmoney.adapters.ReasonListAdapter
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.interfaces.ICashTransfer
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.viewmodels.CashTransferViewModel
import co.yap.modules.dashboard.yapit.sendmoney.fragments.SendMoneyBaseFragment
import co.yap.networking.transactions.responsedtos.InternationalFundsTransferReasonList
import co.yap.translation.Strings
import co.yap.widgets.spinneradapter.ViewHolderArrayAdapter
import co.yap.yapcore.BR
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.enums.SendMoneyBeneficiaryProductCode
import co.yap.yapcore.enums.SendMoneyBeneficiaryType
import co.yap.yapcore.helpers.CustomSnackbar
import co.yap.yapcore.helpers.DecimalDigitsInputFilter
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.managers.MyUserManager
import kotlinx.android.synthetic.main.fragment_cash_transfer.*
import kotlinx.android.synthetic.main.fragment_y2y_funds_transfer.clFTSnackbar
import kotlinx.android.synthetic.main.fragment_y2y_funds_transfer.etAmount

class CashTransferFragment : SendMoneyBaseFragment<ICashTransfer.ViewModel>(), ICashTransfer.View {

//    val args: Y2YTransferFragmentArgs by navArgs()

    private var mReasonListAdapter: ReasonListAdapter? = null
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_cash_transfer

    override val viewModel: CashTransferViewModel
        get() = ViewModelProviders.of(this).get(CashTransferViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startFlows()
        viewModel.getTransactionFeeInternational()
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
//            it.add(
//                0,
//                InternationalFundsTransferReasonList.ReasonList("Select a Reason", "0")
//            )

//            reasonsSpinnerCashTransfer.adapter = getReasonListAdapter(it)

//            reasonsSpinnerCashTransfer.adapter =
//                ViewHolderArrayAdapter(requireContext(), it, { parent ->
//                    ReasonDropDownViewHolder.inflate(parent)
//                }, { parent ->
//                    ReasonDropDownViewHolder.inflate(parent)
//                }, { viewHolder, position, item ->
//                    viewHolder.bind(item)
//                }, { viewHolder, position, item ->
//                    viewHolder.bind(item)
//                })
//            reasonsSpinnerCashTransfer.onItemSelectedListener =
//                object : AdapterView.OnItemSelectedListener {
//                    override fun onNothingSelected(parent: AdapterView<*>?) {
//                    }
//
//                    override fun onItemSelected(
//                        parent: AdapterView<*>?,
//                        view: View?,
//                        position: Int,
//                        id: Long
//                    ) {
//                        viewModel.reasonPosition = position
//                        viewModel.state.reasonTransferValue = it[position].reason
//                        viewModel.state.reasonTransferCode = it[position].code
//                    }
//                }
        })
        //reasonsSpinnerCashTransfer.setSelection(viewModel.reasonPosition)
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
                val action =
                    CashTransferFragmentDirections.actionCashTransferFragmentToGenericOtpLogoFragment(
                        false,
                        viewModel.state.otpAction ?: "",
                        viewModel.state.amount
                        , viewModel.state.position

                    )
                findNavController().navigate(action)
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
                            Utils.getFormattedCurrency(viewModel.state.amount),
                            referenceNumber,
                            viewModel.state.position
                        )
                    findNavController().navigate(action)
                }
            }
        }
    }

    private fun setUpData() {
        if (context is BeneficiaryCashTransferActivity) {
            (context as BeneficiaryCashTransferActivity).viewModel.state.otpSuccess?.let {
                if (it) {
                    callTransactionApi()
                }
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

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0?.length!! > 0) {
                    etAmount.gravity = Gravity.CENTER
                } else {
                    etAmount.gravity = Gravity.START or Gravity.CENTER_VERTICAL
                }
            }
        })
    }

    private fun callTransactionApi() {
        (context as BeneficiaryCashTransferActivity).viewModel.state.beneficiary?.let { beneficiary ->
            beneficiary.beneficiaryType?.let { beneficiaryType ->
                if (beneficiaryType.isNotEmpty())
                    when (SendMoneyBeneficiaryType.valueOf(beneficiaryType)) {
                        SendMoneyBeneficiaryType.CASHPAYOUT -> {
                            beneficiary.id?.let { beneficiaryId ->
                                viewModel.cashPayoutTransferRequest(beneficiaryId)
                            }
                        }
                        //Rak to Rak(yap to rak(Internal transfer))
                        SendMoneyBeneficiaryType.DOMESTIC -> {
                            beneficiary.id?.let { beneficiaryId ->
                                viewModel.domesticTransferRequest(beneficiaryId.toString())
                            }
                        }
                        //UAE non RAK(within UAE(External transfer))
                        SendMoneyBeneficiaryType.UAEFTS -> {
                            beneficiary.id?.let { beneficiaryId ->
                                viewModel.uaeftsTransferRequest(beneficiaryId.toString())
                            }

                        }
                        else -> {

                        }
                    }
            }
        }

    }

    private fun showErrorSnackBar() {
        CustomSnackbar.showErrorCustomSnackbar(
            context = requireContext(),
            layout = clFTSnackbar,
            message = viewModel.state.errorDescription
        )
    }

    override fun onDestroy() {
        viewModel.clickEvent.removeObservers(this)
        super.onDestroy()
    }


    override fun onBackPressed(): Boolean {
        return super.onBackPressed()
    }

    private fun getBindings(): FragmentCashTransferBinding {
        return viewDataBinding as FragmentCashTransferBinding
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

    private fun startFlows() {
        (context as BeneficiaryCashTransferActivity).viewModel.state.beneficiary?.let { beneficiary ->
            beneficiary.beneficiaryType?.let { beneficiaryType ->
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
                            viewModel.getTransactionFeeForCashPayout(getProductCode())
                            setObservers()
                        }
                    }
            }
        }
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
}