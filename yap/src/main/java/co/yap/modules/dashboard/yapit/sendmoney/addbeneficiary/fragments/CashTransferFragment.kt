package co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.fragments

import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.R
import co.yap.databinding.FragmentCashTransferBinding
import co.yap.modules.dashboard.yapit.sendmoney.activities.BeneficiaryCashTransferActivity
import co.yap.modules.dashboard.yapit.sendmoney.adapters.ReasonListAdapter
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.interfaces.ICashTransfer
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.viewmodels.CashTransferViewModel
import co.yap.modules.dashboard.yapit.sendmoney.fragments.SendMoneyBaseFragment
import co.yap.networking.transactions.responsedtos.InternationalFundsTransferReasonList
import co.yap.translation.Strings
import co.yap.yapcore.BR
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.enums.SendMoneyBeneficiaryProductCode
import co.yap.yapcore.enums.SendMoneyBeneficiaryType
import co.yap.yapcore.helpers.CustomSnackbar
import co.yap.yapcore.helpers.DecimalDigitsInputFilter
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.interfaces.OnItemClickListener
import co.yap.yapcore.managers.MyUserManager
import co.yap.yapcore.toast
import kotlinx.android.synthetic.main.fragment_cash_transfer.*
import kotlinx.android.synthetic.main.fragment_y2y_funds_transfer.clFTSnackbar
import kotlinx.android.synthetic.main.fragment_y2y_funds_transfer.etAmount

class CashTransferFragment : SendMoneyBaseFragment<ICashTransfer.ViewModel>(), ICashTransfer.View {

//    val args: Y2YTransferFragmentArgs by navArgs()

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_cash_transfer

    override val viewModel: CashTransferViewModel
        get() = ViewModelProviders.of(this).get(CashTransferViewModel::class.java)

    private var mReasonListAdapter: ReasonListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.state.availableBalance = MyUserManager.cardBalance.value?.availableBalance
        viewModel.getTransactionFeeForCashPayout(getProductCode())
        setObservers()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpData()
    }

    override fun setObservers() {
        viewModel.clickEvent.observe(this, clickEvent)
        viewModel.errorEvent.observe(this, Observer {
            showErrorSnackBar()
        })

        viewModel.populateSpinnerData.observe(this, Observer {
            if (it == null) return@Observer
            reasonsSpinnerCashTransfer.adapter = getReasonListAdapter(it)
            mReasonListAdapter?.setItemListener(listener)
        })
    }

    val listener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            reasonsSpinnerCashTransfer.setSelection(pos)
            toast(data.toString())
        }
    }
    val clickEvent = Observer<Int> {
        when (it) {
            R.id.btnConfirm -> {
                val action =
                    CashTransferFragmentDirections.actionCashTransferFragmentToGenericOtpLogoFragment(
                        false,
                        Constants.BENEFICIARY_CASH_TRANSFER,
                        viewModel.state.amount

                    )
                findNavController().navigate(action)
            }
            Constants.ADD_CASH_PICK_UP_SUCCESS -> {
                val action =
                    CashTransferFragmentDirections.actionCashTransferFragmentToTransferSuccessFragment2(
                        "",
                        viewModel.state.currencyType,
                        Utils.getFormattedCurrency(viewModel.state.amount)
                    )
                findNavController().navigate(action)
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
                        //RMT is for international( RMT(linked with Rak))
                        SendMoneyBeneficiaryType.RMT -> {
                            //Call service for RMT
                            toast("Flow to be implemented for RMT")
                        }
                        //Swift is for international(non RMT(Not linked with Rak))
                        SendMoneyBeneficiaryType.SWIFT -> {
                            //call service for SWIFT
                            toast("Flow to be implemented for swift")
                        }
                        SendMoneyBeneficiaryType.CASHPAYOUT -> {
                            //call service for CASHPAYOUT
                            beneficiary.id?.let { beneficiaryId ->
                                viewModel.cashPayoutTransferRequest(beneficiaryId.toString())
                            }

                        }
                        //Rak to Rak(yap to rak(Internal transfer))
                        SendMoneyBeneficiaryType.DOMESTIC -> {
                            //call service for DOMESTIC
                            beneficiary.id?.let { beneficiaryId ->
                                viewModel.domesticTransferRequest(beneficiaryId.toString())
                            }
                        }
                        SendMoneyBeneficiaryType.INTERNAL_TRANSFER -> {
                            //call service for INTERNAL_TRANSFER

                        }
                        SendMoneyBeneficiaryType.UAEFTS -> {
                            //call service for INTERNAL_TRANSFER

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

    fun getProductCode(): String {

        if (context is BeneficiaryCashTransferActivity) {
            (context as BeneficiaryCashTransferActivity).viewModel.state.beneficiary?.let { beneficiary ->
                beneficiary.beneficiaryType?.let { beneficiaryType ->
                    if (beneficiaryType.isNotEmpty())
                        when (SendMoneyBeneficiaryType.valueOf(beneficiaryType)) {
                            SendMoneyBeneficiaryType.RMT -> {
                                return SendMoneyBeneficiaryProductCode.P012.name
                            }
                            SendMoneyBeneficiaryType.SWIFT -> {
                                return SendMoneyBeneficiaryProductCode.P011.name
                            }
                            SendMoneyBeneficiaryType.CASHPAYOUT -> {
                                return SendMoneyBeneficiaryProductCode.P013.name
                            }
                            SendMoneyBeneficiaryType.DOMESTIC -> {
                                return SendMoneyBeneficiaryProductCode.P023.name
                            }
                            /*SendMoneyBeneficiaryType.INTERNAL_TRANSFER -> {
                                //call service for INTERNAL_TRANSFER

                            }*/
                            SendMoneyBeneficiaryType.UAEFTS -> {
                                return SendMoneyBeneficiaryProductCode.P010.name


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

    fun getReasonListAdapter(it: List<InternationalFundsTransferReasonList.ReasonList>): ReasonListAdapter {
        if (mReasonListAdapter == null)
            mReasonListAdapter = ReasonListAdapter(
                requireContext(), R.layout.item_reason_list, it
            )
        return this.mReasonListAdapter!!
    }

}