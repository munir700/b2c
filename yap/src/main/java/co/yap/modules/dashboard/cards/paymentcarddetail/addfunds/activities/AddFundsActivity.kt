package co.yap.modules.dashboard.cards.paymentcarddetail.addfunds.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.text.InputFilter
import androidx.core.os.bundleOf
import androidx.core.view.children
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.databinding.ActivityAddFundsBinding
import co.yap.modules.dashboard.cards.paymentcarddetail.addfunds.interfaces.IAddFunds
import co.yap.modules.dashboard.cards.paymentcarddetail.addfunds.viewmodels.AddFundsViewModel
import co.yap.modules.otp.GenericOtpFragment
import co.yap.modules.otp.OtpDataModel
import co.yap.networking.cards.responsedtos.Card
import co.yap.translation.Strings
import co.yap.translation.Translator
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.enums.OTPActions
import co.yap.yapcore.helpers.*
import co.yap.yapcore.helpers.extentions.afterTextChanged
import co.yap.yapcore.helpers.extentions.parseToDouble
import co.yap.yapcore.helpers.extentions.startFragmentForResult
import co.yap.yapcore.helpers.extentions.toFormattedAmountWithCurrency
import co.yap.yapcore.helpers.spannables.color
import co.yap.yapcore.helpers.spannables.getText
import co.yap.yapcore.managers.MyUserManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_fund_actions.*

class AddFundsActivity : BaseBindingActivity<IAddFunds.ViewModel>(), IAddFunds.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_add_funds
    override val viewModel: AddFundsViewModel
        get() = ViewModelProviders.of(this).get(AddFundsViewModel::class.java)

    companion object {
        private const val CARD = "card"
        fun newIntent(context: Context, card: Card): Intent {
            val intent = Intent(context, AddFundsActivity::class.java)
            intent.putExtra(CARD, card)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.state.card.set(intent.getParcelableExtra(CARD))
        addObservers()
        val display = this.windowManager.defaultDisplay
        display.getRectSize(Rect())
        clBottomNew.children.forEach { it.alpha = 0f }
    }

    override fun addObservers() {
        setEditTextWatcher()
        viewModel.clickEvent.observe(this, clickObserver)
        viewModel.isFeeReceived.observe(this, Observer {
            if (it) viewModel.updateFees(viewModel.state.amount)
        })
        viewModel.updatedFee.observe(this, Observer {
            if (it.isNotBlank()) setSpannableFee(it)
        })
    }

    private fun setSpannableFee(feeAmount: String?) {
        viewModel.state.transferFee.set(
            resources.getText(
                getString(Strings.common_text_fee), this.color(
                    R.color.colorPrimaryDark,
                    "${feeAmount?.toFormattedAmountWithCurrency()}"
                )
            )
        )
    }


    private fun setDenominationValue(denominationAmount: String) {
        hideKeyboard()
        getBinding().etAmount.setText("")
        getBinding().etAmount.setText(denominationAmount)
        val position = etAmount.length()
        getBinding().etAmount.setSelection(position)
        getBinding().etAmount.clearFocus()
    }

    private fun setEditTextWatcher() {
        etAmount.filters =
            arrayOf(InputFilter.LengthFilter(7), DecimalDigitsInputFilter(2))

        etAmount.afterTextChanged {
            if (!viewModel.state.amount.isBlank()) {
                checkOnTextChangeValidation()
            } else {
                setAmountBg()
                viewModel.state.valid = false
                cancelAllSnackBar()
            }
            viewModel.updateFees(viewModel.state.amount)
        }
    }

    private val clickObserver = Observer<Int> {
        when (it) {
            R.id.tvDominationFirstAmount -> setDenominationValue(
                viewModel.state.firstDenomination.get() ?: ""
            )
            R.id.tvDominationSecondAmount -> setDenominationValue(
                viewModel.state.secondDenomination.get() ?: ""
            )
            R.id.tvDominationThirdAmount -> setDenominationValue(
                viewModel.state.thirdDenomination.get() ?: ""
            )
            R.id.tbIvClose -> finish()
        }
    }

    private fun checkOnTextChangeValidation() {
        when {
            !isBalanceAvailable() -> {
                setAmountBg(true)
                showBalanceNotAvailableError()
            }
            isDailyLimitReached() -> {
                setAmountBg(true)
                showErrorSnackBar(viewModel.errorDescription)
            }
            viewModel.state.amount.parseToDouble() < viewModel.state.minLimit -> {
                viewModel.state.valid = true
            }
            viewModel.state.amount.parseToDouble() > viewModel.state.maxLimit -> {
                setAmountBg(true)
                showUpperLowerLimitError()
                showErrorSnackBar(viewModel.errorDescription)
            }
            isTopUpLimitReached() -> {
                setAmountBg(true)
                showErrorSnackBar(viewModel.errorDescription)
            }
            else -> {
                setAmountBg()
            }
        }
    }

    private fun showUpperLowerLimitError() {
        viewModel.errorDescription = Translator.getString(
            this,
            Strings.common_display_text_min_max_limit_error_transaction,
            viewModel.state.minLimit.toString().toFormattedAmountWithCurrency(),
            viewModel.state.maxLimit.toString().toFormattedAmountWithCurrency()
        )
    }

    private fun showBalanceNotAvailableError() {
        viewModel.errorDescription = Translator.getString(
            this,
            Strings.common_display_text_available_balance_error
        ).format(viewModel.state.amount.toFormattedAmountWithCurrency())
        showErrorSnackBar(viewModel.errorDescription)
    }

    private fun isBalanceAvailable(): Boolean {
        val availableBalance =
            MyUserManager.cardBalance.value?.availableBalance?.toDoubleOrNull()
        return if (availableBalance != null) {
            (availableBalance > viewModel.getAmountWithFee())
        } else
            false
    }


    private fun isTopUpLimitReached(): Boolean {
        return viewModel.transactionThreshold?.let { threshold ->
            return when {
                viewModel.state.card.get()?.availableBalance.parseToDouble() == threshold.virtualCardBalanceLimit ?: 0.0 -> {
                    viewModel.errorDescription = Translator.getString(
                        this,
                        Strings.screen_add_funds_display_text_error_card_balance_limit_reached,
                        threshold.virtualCardBalanceLimit.toString().toFormattedAmountWithCurrency()
                    )
                    return true
                }
                viewModel.state.amount.parseToDouble()
                    .plus(viewModel.state.card.get()?.availableBalance.parseToDouble()) > threshold.virtualCardBalanceLimit ?: 0.0 -> {
                    viewModel.errorDescription = Translator.getString(
                        this,
                        Strings.screen_add_funds_display_text_error_card_balance_limit,
                        threshold.virtualCardBalanceLimit.toString()
                            .toFormattedAmountWithCurrency(),
                        (threshold.virtualCardBalanceLimit?.minus(viewModel.state.card.get()?.availableBalance.parseToDouble())).toString()
                            .toFormattedAmountWithCurrency()
                    )
                    return true
                }
                else -> false
            }
        } ?: false
    }

    private fun isDailyLimitReached(): Boolean {
        viewModel.transactionThreshold?.let {
            it.dailyLimit?.let { dailyLimit ->
                it.totalDebitAmount?.let { totalConsumedAmount ->
                    viewModel.state.amount.parseToDouble().let { enteredAmount ->
                        val remainingDailyLimit =
                            if ((dailyLimit - totalConsumedAmount) < 0.0) 0.0 else (dailyLimit - totalConsumedAmount)
                        viewModel.errorDescription =
                            when (dailyLimit) {
                                totalConsumedAmount -> getString(Strings.common_display_text_daily_limit_error)
                                else -> Translator.getString(
                                    this,
                                    Strings.common_display_text_daily_limit_remaining_error,
                                    remainingDailyLimit.toString().toFormattedAmountWithCurrency()
                                )
                            }
                        return enteredAmount > remainingDailyLimit
                    }
                } ?: return false
            } ?: return false
        } ?: return false
    }

    private fun setAmountBg(isError: Boolean = false) {
        getBinding().etAmountLayout.background =
            this.resources.getDrawable(
                if (isError) co.yap.yapcore.R.drawable.bg_funds_error else co.yap.yapcore.R.drawable.bg_funds,
                null
            )
        if (!isError) cancelAllSnackBar()
        viewModel.state.valid = !isError
    }

    private fun showErrorSnackBar(errorMessage: String) {
        getSnackBarFromQueue(0)?.let {
            if (it.isShown) {
                it.updateSnackBarText(errorMessage)
            }
        } ?: getBinding().clSnackBar.showSnackBar(
            msg = errorMessage,
            viewBgColor = R.color.errorLightBackground,
            colorOfMessage = R.color.error, duration = Snackbar.LENGTH_INDEFINITE, marginTop = 0
        )
    }


    private fun startOtpFragment() {
        startFragmentForResult<GenericOtpFragment>(
            GenericOtpFragment::class.java.name,
            bundleOf(
                OtpDataModel::class.java.name to OtpDataModel(
                    OTPActions.TOP_UP_SUPPLEMENTARY.name,
                    MyUserManager.user?.currentCustomer?.getFormattedPhoneNumber(this)
                        ?: "",
                    amount = viewModel.state.amount
                )
            )
        ) { resultCode, _ ->
            if (resultCode == Activity.RESULT_OK) {
//                viewModel.addFunds()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        removeObservers()
    }

    override fun removeObservers() {
        viewModel.clickEvent.removeObserver(clickObserver)
    }

    private fun getBinding(): ActivityAddFundsBinding {
        return (viewDataBinding as ActivityAddFundsBinding)
    }

}