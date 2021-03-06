package co.yap.modules.dashboard.yapit.topup.topupamount.fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentTopUpCardFundsBinding
import co.yap.modules.dashboard.cards.paymentcarddetail.addfunds.interfaces.IFundActions
import co.yap.modules.dashboard.cards.paymentcarddetail.addfunds.viewmodels.FundActionsViewModel
import co.yap.modules.dashboard.yapit.topup.addtopupcard.activities.AddTopUpCardActivityV2
import co.yap.modules.dashboard.yapit.topup.topupamount.activities.TopUpCardActivity
import co.yap.modules.dashboard.yapit.topup.topupamount.viewModels.TopUpCardFundsViewModel
import co.yap.translation.Strings
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.enums.TransactionProductCode
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.cancelAllSnackBar
import co.yap.yapcore.helpers.extentions.*
import co.yap.yapcore.helpers.showTextUpdatedAbleSnackBar
import co.yap.yapcore.helpers.spannables.color
import co.yap.yapcore.helpers.spannables.getText
import co.yap.yapcore.managers.SessionManager
import com.google.android.material.snackbar.Snackbar

class TopUpCardFundsFragment : BaseBindingFragment<FragmentTopUpCardFundsBinding,IFundActions.ViewModel>(),
    IFundActions.View {
    private var parentViewModel: FundActionsViewModel? = null
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_top_up_card_funds

    override val viewModel: IFundActions.ViewModel
        get() = ViewModelProvider(this).get(TopUpCardFundsViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parentViewModel =
            this.let { ViewModelProvider(it).get(FundActionsViewModel::class.java) }
        parentViewModel?.getTransferFees(TransactionProductCode.TOP_UP_VIA_CARD.pCode)
        setObservers()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity is TopUpCardActivity)
            (activity as TopUpCardActivity).cardInfo?.let {
                viewModel.initateVM(it)
            }
        setupData()
    }

    override fun setObservers() {
        viewModel.clickEvent.observe(this, clickEvent)
        viewModel.errorEvent.observe(this, Observer {
            showTextUpdatedAbleSnackBar(
                viewModel.state.errorDescription,
                Snackbar.LENGTH_INDEFINITE
            )
        })
        viewModel.enteredAmount.observe(this, enterAmountObserver)
        viewModel.htmlLiveData.observe(this, Observer {
            if (!it.isNullOrEmpty()) {
                launchActivity<AddTopUpCardActivityV2>(requestCode = Constants.EVENT_TOP_UP_CARD_TRANSACTION) {
                    putExtra(Constants.KEY, it)
                    putExtra(Constants.TYPE, Constants.TYPE_TOP_UP_TRANSACTION)
                }
            } else {
                return@Observer
            }
        })

        viewModel.topUpTransactionModelLiveData?.observe(this, Observer {
            if (context is TopUpCardActivity) {
                (context as TopUpCardActivity).topUpTransactionModel =
                    viewModel.topUpTransactionModelLiveData
                val action =
                    TopUpCardFundsFragmentDirections.actionTopUpCardFundsFragmentToVerifyCardCvvFragment(
                        viewModel.enteredAmount.value.toString(),
                        viewModel.state.currencyType
                    )
                findNavController().navigate(
                    action
                )
            }
        })
        parentViewModel?.isFeeReceived?.observe(this, Observer {
            if (it) parentViewModel?.updateFees(viewModel.state.amount ?: "")
        })
        parentViewModel?.updatedFee?.observe(this, Observer {
            if (it.isNotBlank()) setUpFeeData(it)
        })
    }

    var clickEvent = Observer<Int> {
        when (it) {
            R.id.btnAction -> {
                if (viewModel.enteredAmount.value?.getValueWithoutComa()
                        .parseToDouble() ?: 0.0 < viewModel.state.minLimit
                ) {
                    viewModel.state.amountBackground =
                        resources.getDrawable(co.yap.yapcore.R.drawable.bg_funds_error, null)
                    showUpperLowerLimitError()
                } else
                    viewModel.createTransactionSession()
            }
            R.id.tvDominationFirstAmount -> viewModel.denominationAmountValidator(
                viewModel.state.denominationFirstAmount.get() ?: ""
            ) { enabled ->
                viewModel.state.valid = enabled
                getDataBindingView<FragmentTopUpCardFundsBinding>().etAmount.setText(viewModel.enteredAmount.value)
            }
            R.id.tvDominationSecondAmount -> viewModel.denominationAmountValidator(
                viewModel.state.denominationSecondAmount.get() ?: ""
            ) { enabled ->
                getDataBindingView<FragmentTopUpCardFundsBinding>().etAmount.setText(viewModel.enteredAmount.value)
                viewModel.state.valid = enabled
            }
            R.id.tvDominationThirdAmount -> viewModel.denominationAmountValidator(
                viewModel.state.denominationThirdAmount.get() ?: ""
            ) { enabled ->
                getDataBindingView<FragmentTopUpCardFundsBinding>().etAmount.setText(viewModel.enteredAmount.value)
                viewModel.state.valid = enabled
            }
        }
    }

    override fun onToolBarClick(id: Int) {
        when (id) {
            R.id.ivLeftIcon -> activity?.finish()
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private val enterAmountObserver = Observer<String> {
        parentViewModel?.updateFees(it.getValueWithoutComa(), isTopUpFee = true)
        val enteredAmount = it.getValueWithoutComa().toDoubleOrNull() ?: 0.0
        if (it.isNotBlank()) {
            when {
                isMaxMinLimitReached(it.getValueWithoutComa()) || enteredAmount < viewModel.state.minLimit -> {
                    viewModel.state.valid = false
                    viewModel.state.amountBackground =
                        resources.getDrawable(co.yap.yapcore.R.drawable.bg_funds_error, null)
                    showUpperLowerLimitError()
                }
                enteredAmount > viewModel.state.minLimit && enteredAmount <= viewModel.state.remainingAccumulative.get() ?: 0.0 -> {
                    viewModel.state.valid = true
                    viewModel.state.amountBackground =
                        resources.getDrawable(co.yap.yapcore.R.drawable.bg_funds, null)
                    cancelAllSnackBar()
                }
                enteredAmount > viewModel.state.remainingAccumulative.get() ?: 0.0 ||
                        enteredAmount > viewModel.state.maximumAccumulative.get() ?: 0.0 -> {
                    viewModel.state.valid = false
                    viewModel.state.amountBackground =
                        resources.getDrawable(co.yap.yapcore.R.drawable.bg_funds_error, null)
                    showAccumalatveLimitError(getString(if (viewModel.state.remainingAccumulative.get() == 0.0) Strings.screen_topup_bank_error_display_no_accumulative_balance else Strings.screen_topup_bank_error_display_less_accumulative_balance))
                }
                else -> {
                    viewModel.state.valid = true
                    viewModel.state.amountBackground =
                        resources.getDrawable(co.yap.yapcore.R.drawable.bg_funds, null)
                    cancelAllSnackBar()
                }
            }
        } else {
            viewModel.state.valid = false
            viewModel.state.amountBackground =
                resources.getDrawable(co.yap.yapcore.R.drawable.bg_funds, null)
            cancelAllSnackBar()
        }
    }

    private fun isMaxMinLimitReached(amount: String): Boolean {
        return if (amount.isNotBlank() || amount.toDoubleOrNull() ?: 0.0 > 0.0)
            amount.toDoubleOrNull() ?: 0.0 > viewModel.state.maxLimit
        else
            false
    }

    fun showUpperLowerLimitError() {
        viewModel.state.errorDescription = getString(
            Strings.common_display_text_min_max_limit_error_transaction
        ).format(
            viewModel.state.minLimit.toString().toFormattedCurrency(),
            viewModel.state.maxLimit.toString().toFormattedCurrency()
        )
        showTextUpdatedAbleSnackBar(
            viewModel.state.errorDescription,
            Snackbar.LENGTH_INDEFINITE
        )
    }

    fun showAccumalatveLimitError(message: String) {
        showTextUpdatedAbleSnackBar(
            message,
            Snackbar.LENGTH_INDEFINITE
        )
    }

    private fun setupData() {
        if (context is TopUpCardActivity) {
            viewModel.state.cardNumber =
                (context as TopUpCardActivity).cardInfo?.number.toString()
            viewModel.state.cardName = (context as TopUpCardActivity).cardInfo?.alias.toString()
        }

        getDataBindingView<FragmentTopUpCardFundsBinding>().tvAvailableBalanceGuide.text =
            requireContext().resources.getText(
                getString(Strings.common_display_text_available_balance),
                requireContext().color(
                    R.color.colorPrimaryDark,
                    SessionManager.cardBalance.value?.availableBalance.toString()
                        .toFormattedCurrency(showCurrency = true)
                )
            )
    }

    private fun setUpFeeData(transactionFee: String) {
        if (transactionFee == getString(Strings.screen_topup_transfer_display_text_transaction_no_fee)) {
            viewModel.state.transactionFeeSpannableString =
                getString(Strings.screen_topup_transfer_display_text_transaction_fee)
                    .format(transactionFee)
            getDataBindingView<FragmentTopUpCardFundsBinding>().tvFeeDescription.text =
                Utils.getSpannableString(
                    requireContext(),
                    viewModel.state.transactionFeeSpannableString, transactionFee
                )

        } else if (transactionFee.toDouble() >= 0) {
            getDataBindingView<FragmentTopUpCardFundsBinding>().tvFeeDescription.text =
                requireContext().resources.getText(
                    getString(Strings.screen_topup_transfer_display_text_transaction_fee),
                    requireContext().color(
                        R.color.colorPrimaryDark,
                        transactionFee.parseToDouble().roundValHalfEven().toString()
                            .toFormattedCurrency(showCurrency = true)
                    )
                )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constants.EVENT_TOP_UP_CARD_TRANSACTION && resultCode == Activity.RESULT_OK) {
            if (true == data?.let {
                    it.getBooleanExtra(Constants.START_POOLING, false)
                }) {
                viewModel.startPooling(true)
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.enteredAmount.removeObservers(this)
    }
}
