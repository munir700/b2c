package co.yap.modules.dashboard.cards.paymentcarddetail.addfunds.activities

import android.animation.AnimatorSet
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.InputFilter
import android.view.View
import androidx.core.animation.addListener
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
import co.yap.yapcore.helpers.extentions.*
import co.yap.yapcore.helpers.spannables.color
import co.yap.yapcore.helpers.spannables.getText
import co.yap.yapcore.managers.MyUserManager
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_add_funds.*

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
        getBinding().clBottomNew.children.forEach { it.alpha = 0f }
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
        val position = getBinding().etAmount.length()
        getBinding().etAmount.setSelection(position)
        getBinding().etAmount.clearFocus()
    }

    private fun setEditTextWatcher() {
        getBinding().etAmount.filters =
            arrayOf(InputFilter.LengthFilter(7), DecimalDigitsInputFilter(2))

        etAmount.afterTextChanged {
            if (!viewModel.state.amount.isBlank()) {
                checkOnTextChangeValidation()
            } else {
                setAmountBg(true)
            }
            viewModel.updateFees(viewModel.state.amount)
        }
    }

    private val clickObserver = Observer<Int> {
        when (it) {
            R.id.btnAction -> {
                when {
                    getBinding().btnAction.text == getString(Strings.screen_success_funds_transaction_display_text_button) -> {
                        setupActionsIntent()
                    }
                    isOtpRequired() -> startOtpFragment()

                    else -> {
                        viewModel.addFunds {
                            setUpSuccessData()
                            performSuccessOperations()
                        }
                    }
                }
            }
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
                if (isTopUpLimitReached()) {
                    setAmountBg(true)
                    showErrorSnackBar(viewModel.errorDescription)
                } else
                    viewModel.state.valid.set(false)

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
            (availableBalance >= viewModel.getAmountWithFee())
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
            it.dailyLimitTopUpSupplementary?.let { dailyLimit ->
                it.totalDebitAmountTopUpSupplementary?.let { totalConsumedAmount ->
                    viewModel.state.amount.toDoubleOrNull()?.let { enteredAmount ->
                        val remainingDailyLimit =
                            if ((dailyLimit - totalConsumedAmount) < 0.0) 0.0 else (dailyLimit - totalConsumedAmount)
                        if (enteredAmount > remainingDailyLimit.roundVal()) viewModel.errorDescription =
                            when (dailyLimit) {
                                totalConsumedAmount -> getString(Strings.common_display_text_daily_limit_error)
                                else -> Translator.getString(
                                    this,
                                    Strings.common_display_text_daily_limit_remaining_error,
                                    remainingDailyLimit.roundVal().toString().toFormattedAmountWithCurrency()
                                )
                            }
                        return enteredAmount > remainingDailyLimit.roundVal()

                    } ?: return false
                } ?: return false
            } ?: return false
        } ?: return false
    }

    private fun isOtpRequired(): Boolean {
        viewModel.transactionThreshold?.let {
            it.totalDebitAmountTopUpSupplementary?.let { totalTopupConsumedAmount ->
                viewModel.state.amount.toDoubleOrNull()?.let { enteredAmount ->
                    val remainingOtpLimit =
                        it.otpLimitTopUpSupplementary?.minus(totalTopupConsumedAmount)
                    return enteredAmount > (remainingOtpLimit ?: 0.0)
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
        viewModel.state.valid.set(!isError)
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
                viewModel.addFunds {
                    setUpSuccessData()
                    performSuccessOperations()
                }
            }
        }
    }

    private fun setUpSuccessData() {
        viewModel.state.topUpSuccessMsg.set(
            resources.getText(
                getString(Strings.screen_success_funds_transaction_display_text_top_up), this.color(
                    R.color.colorPrimaryDark,
                    viewModel.state.amount.toFormattedAmountWithCurrency()
                )
            )
        )

        viewModel.state.debitCardUpdatedBalance.set(
            resources.getText(
                getString(Strings.screen_success_funds_transaction_display_text_primary_balance),
                this.color(
                    R.color.colorPrimaryDark,
                    MyUserManager.cardBalance.value?.availableBalance.toString()
                        .toFormattedAmountWithCurrency()
                )
            )
        )

        viewModel.state.spareCardUpdatedBalance.set(
            resources.getText(
                getString(Strings.screen_success_funds_transaction_display_text_success_updated_prepaid_card_balance),
                this.color(
                    R.color.colorPrimaryDark,
                    (viewModel.state.card.get()?.availableBalance.parseToDouble() + viewModel.state.amount.parseToDouble()).toString()
                        .toFormattedAmountWithCurrency()
                )
            )
        )
    }

    private fun performSuccessOperations() {
        runOnUiThread {
            getBinding().etAmount.visibility = View.GONE
            getBinding().btnAction.text =
                getString(Strings.screen_success_funds_transaction_display_text_button)
            YoYo.with(Techniques.FadeOut)
                .duration(300)
                .repeat(0)
                .playOn(getBinding().tbIvClose)
            getBinding().clBottom.children.forEach { it.alpha = 0f }
            getBinding().btnAction.alpha = 0f
            getBinding().cardInfoLayout.clRightData.children.forEach { it.alpha = 0f }
            Handler(Looper.getMainLooper()).postDelayed({ runAnimations() }, 1500)
            runCardAnimation()
        }
    }


    private fun runAnimations() {
        AnimationUtils.runSequentially(
            AnimationUtils.runTogether(
                AnimationUtils.jumpInAnimation(tvCardNameSuccess),
                AnimationUtils.jumpInAnimation(tvCardNumberSuccess).apply { startDelay = 100 },
                AnimationUtils.jumpInAnimation(tvTopUp).apply { startDelay = 200 },
                AnimationUtils.jumpInAnimation(tvPrimaryCardBalance).apply { startDelay = 300 },
                AnimationUtils.jumpInAnimation(tvNewSpareCardBalance).apply { startDelay = 400 },
                AnimationUtils.jumpInAnimation(btnAction).apply { startDelay = 600 }

            )
        ).apply {
            addListener(onEnd = {
                startCheckMarkAnimation()
            })
        }.start()
    }

    private fun startCheckMarkAnimation() {
        YoYo.with(Techniques.BounceIn)
            .duration(1000)
            .repeat(0)
            .playOn(ivSuccessCheckMark)
    }


    private fun runCardAnimation() {
        Handler(Looper.getMainLooper()).postDelayed({
            cardAnimation().apply {
                addListener(onEnd = {
                })
            }.start()
        }, 500)
    }


    private fun cardAnimation(): AnimatorSet {
        val checkBtnEndPosition =
            (cardInfoLayout.measuredWidth / 2) - (getBinding().cardInfoLayout.ivCustomCard.width / 2)
        return AnimationUtils.runSequentially(
            AnimationUtils.slideHorizontal(
                view = getBinding().cardInfoLayout.ivCustomCard,
                from = getBinding().cardInfoLayout.ivCustomCard.x,
                to = checkBtnEndPosition.toFloat(),
                duration = 500
            ),
            AnimationUtils.pulse(getBinding().cardInfoLayout.ivCustomCard)
        )
    }

    private fun setupActionsIntent() {
        val returnIntent = Intent()
        returnIntent.putExtra(
            "newBalance",
            (viewModel.state.card.get()?.availableBalance.parseToDouble() + viewModel.state.amount.parseToDouble()).toString()
        )
        setResult(Activity.RESULT_OK, returnIntent)
        finish()
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