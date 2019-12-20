package co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.yapit.sendmoney.activities.BeneficiaryCashTransferActivity
import co.yap.modules.dashboard.yapit.sendmoney.adapters.ReasonListAdapter
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.interfaces.IInternationalFundsTransfer
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.viewmodels.InternationalFundsTransferViewModel
import co.yap.modules.dashboard.yapit.sendmoney.fragments.SendMoneyBaseFragment
import co.yap.networking.transactions.responsedtos.InternationalFundsTransferReasonList
import co.yap.translation.Strings
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.enums.SendMoneyBeneficiaryProductCode
import co.yap.yapcore.enums.SendMoneyBeneficiaryType
import co.yap.yapcore.interfaces.OnItemClickListener
import co.yap.yapcore.toast
import kotlinx.android.synthetic.main.fragment_beneficiary_overview.*
import kotlinx.android.synthetic.main.fragment_international_funds_transfer.*
import kotlinx.android.synthetic.main.item_transaction_list.*


class InternationalFundsTransferFragment :
    SendMoneyBaseFragment<IInternationalFundsTransfer.ViewModel>(),
    IInternationalFundsTransfer.View {


    private var mReasonListAdapter: ReasonListAdapter? = null
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_international_funds_transfer
    override val viewModel: IInternationalFundsTransfer.ViewModel
        get() = ViewModelProviders.of(this).get(InternationalFundsTransferViewModel::class.java)

    val reasonList = charArrayOf()
    var bankReasonList: MutableList<InternationalFundsTransferReasonList.ReasonList> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getBeneficiaryId()
        viewModel.getTransactionFeeInternational(getProductCode())
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        successOtpFlow()
    }

    fun successOtpFlow() {
        if (context is BeneficiaryCashTransferActivity) {
            (context as BeneficiaryCashTransferActivity).viewModel.state.otpSuccess?.let {
                if (it) {
                    callTransactionApi()
                }
            }
        }
    }

    private fun setObservers() {
        viewModel.clickEvent.observe(this, clickEvent)
        viewModel.populateSpinnerData.observe(this, Observer {
            if (it == null) return@Observer
            bankReasonList = it as MutableList<InternationalFundsTransferReasonList.ReasonList>
            reasonsSpinner.adapter = getReasonListAdapter(it)
            mReasonListAdapter?.setItemListener(listener)
        })
    }

    val clickEvent = Observer<Int> {
        when (it) {
            R.id.btnNext -> {
                viewModel.state.position?.let { position ->
                    val action =
                        InternationalFundsTransferFragmentDirections.actionInternationalFundsTransferFragmentToGenericOtpLogoFragment(
                            false,
                            viewModel.otpAction.toString(),
                            viewModel.state.fxRateAmount.toString(), position
                        )
                    findNavController().navigate(action)
                }


            }
            Constants.ADD_SUCCESS -> {
                viewModel.state.position?.let { position ->
                    val action =
                        InternationalFundsTransferFragmentDirections.actionInternationalFundsTransferFragmentToInternationalTransactionConfirmationFragment(
                            viewModel.state.beneficiaryName,
                            viewModel.state.senderCurrency.toString(),
                            viewModel.state.fxRateAmount.toString(),
                            viewModel.state.receiverCurrencyAmount.toString(),
                            viewModel.state.internationalFee.toString(),
                            viewModel.state.fromFxRate.toString(),
                            viewModel.state.toFxRate.toString(),
                            viewModel.state.referenceNumber.toString(), position

                        )
                    findNavController().navigate(action)
                }

            }

        }
    }

    val listener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            reasonsSpinner.setSelection(pos)
            if (bankReasonList.isNotEmpty()) {
                println(data.toString())
                bankReasonList[pos].code
                bankReasonList[pos].reason


                println(bankReasonList[pos].code)
                println(bankReasonList[pos].reason)
                toast("")
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


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onPause() {
        viewModel.clickEvent.removeObservers(this)
        super.onPause()

    }

    override fun onResume() {
        setObservers()
        super.onResume()
    }


    fun getReasonListAdapter(it: List<InternationalFundsTransferReasonList.ReasonList>): ReasonListAdapter {
        if (mReasonListAdapter == null)
            mReasonListAdapter = ReasonListAdapter(
                requireContext(), R.layout.item_reason_list, it
            )
        return this.mReasonListAdapter!!
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