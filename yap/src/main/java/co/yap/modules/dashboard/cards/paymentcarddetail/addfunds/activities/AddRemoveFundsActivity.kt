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
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.Gravity
import android.view.View
import androidx.core.animation.addListener
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.children
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.cards.paymentcarddetail.addfunds.interfaces.IFundActions
import co.yap.modules.dashboard.cards.paymentcarddetail.addfunds.viewmodels.FundActionsViewModel
import co.yap.modules.dashboard.cards.paymentcarddetail.addfunds.viewmodels.fundvm
import co.yap.modules.others.helper.Constants
import co.yap.modules.otp.GenericOtpFragment
import co.yap.modules.otp.OtpDataModel
import co.yap.networking.cards.responsedtos.Card
import co.yap.translation.Strings
import co.yap.translation.Translator
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.adjust.AdjustEvents
import co.yap.yapcore.enums.OTPActions
import co.yap.yapcore.enums.TransactionProductCode
import co.yap.yapcore.helpers.*
import co.yap.yapcore.helpers.extentions.*
import co.yap.yapcore.helpers.spannables.color
import co.yap.yapcore.helpers.spannables.getText
import co.yap.yapcore.managers.MyUserManager
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_fund_actions.*
import kotlinx.android.synthetic.main.layout_card_info.*
import kotlinx.android.synthetic.main.layout_fund_actions_tool_bar.*


open class AddRemoveFundsActivity : BaseBindingActivity<IFundActions.ViewModel>(),
    IFundActions.View {
    private var isAddFundScreen: Boolean? = null
    var amount: String? = null
    private val windowSize: Rect = Rect()
    var card: Card? = null
    private lateinit var updatedSpareCardBalance: String
    private var fundsAddedRemoveSuccess: Boolean = false
    private var parentViewModel: FundActionsViewModel? = null

    companion object {
        private const val CARD = "card"
        private const val IS_ADD_FUND = "isAddFund"
        fun newIntent(context: Context, card: Card, isAddFund: Boolean): Intent {
            val intent = Intent(context, AddRemoveFundsActivity::class.java)
            intent.putExtra(CARD, card)
            intent.putExtra(IS_ADD_FUND, isAddFund)
            return intent
        }
    }

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_fund_actions

    override val viewModel: IFundActions.ViewModel
        get() = ViewModelProviders.of(this).get(fundvm::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isAddFundScreen = intent?.getValue(IS_ADD_FUND, ExtraType.BOOLEAN.name) as? Boolean
        viewModel.state.isAddFundScreen.set(isAddFundScreen)
        parentViewModel =
            this.let { ViewModelProviders.of(it).get(FundActionsViewModel::class.java) }
        val pCode =
            if (isAddFundScreen == true) TransactionProductCode.TOP_UP_SUPPLEMENTARY_CARD.pCode else TransactionProductCode.WITHDRAW_SUPPLEMENTARY_CARD.pCode
        parentViewModel?.getTransferFees(pCode)
        viewModel.getFundTransferDenominations(pCode)
        viewModel.getFundTransferLimits(pCode)

        val display = this.windowManager.defaultDisplay
        display.getRectSize(windowSize)
        clBottomNew.children.forEach { it.alpha = 0f }
        setObservers()
        setDenominationClickObservers()
        setupData()
    }

    fun getAmountWithFee(): Double {
        return viewModel.state.amount.parseToDouble()
            .plus(parentViewModel?.updatedFee?.value.parseToDouble())
    }

    private fun setDenominationClickObservers() {
        viewModel.firstDenominationClickEvent.observe(this, Observer {
            hideKeyboard()
            etAmount.setText("")
            etAmount.setText(viewModel.state.denominationAmount)
            val position = etAmount.length()
            etAmount.setSelection(position)
            etAmount.clearFocus()
        })
        viewModel.secondDenominationClickEvent.observe(this, Observer {
            hideKeyboard()
            etAmount.setText("")
            etAmount.append(viewModel.state.denominationAmount)
            val position = etAmount.length()
            etAmount.setSelection(position)
            etAmount.clearFocus()
        })
        viewModel.thirdDenominationClickEvent.observe(this, Observer {
            hideKeyboard()
            etAmount.setText("")
            etAmount.append(viewModel.state.denominationAmount)
            val position = etAmount.length()
            etAmount.setSelection(position)
            etAmount.clearFocus()
        })
    }

    override fun setObservers() {
        MyUserManager.cardBalance.observe(this, Observer {
            if (it.availableBalance != viewModel.state.availableBalance) {
                if (viewModel.state.isAddFundScreen.get() == true)
                    viewModel.clickEvent.setValue(viewModel.EVENT_ADD_FUNDS_SUCCESS)
                else
                    viewModel.clickEvent.setValue(viewModel.EVENT_REMOVE_FUNDS_SUCCESS)

            }
        })
        viewModel.errorEvent.observe(this, Observer {
            showErrorSnackBar(viewModel.state.errorDescription, Snackbar.LENGTH_LONG)
        })
        viewModel.clickEvent.observe(this, clickEvent)
        parentViewModel?.isFeeReceived?.observe(this, Observer {
            if (it) parentViewModel?.updateFees(viewModel.state.amount ?: "")
        })
        parentViewModel?.updatedFee?.observe(this, Observer {
            if (it.isNotBlank()) setSpannableFee(it)
        })
    }

    private fun setSpannableFee(feeAmount: String?) {
        viewModel.state.transferFee =
            resources.getText(
                getString(Strings.common_text_fee), this.color(
                    R.color.colorPrimaryDark,
                    "${viewModel.state.currencyType} ${feeAmount?.toFormattedCurrency()}"
                )
            )
    }

    private val clickEvent = Observer<Int> {
        when (it) {
            R.id.btnAction ->
                if (MyUserManager.user?.otpBlocked == true) {
                    showToast(Utils.getOtpBlockedMessage(context))
                } else {
                    (if (btnAction.text != getString(Strings.screen_success_funds_transaction_display_text_button)) {
                        if (isAddFundScreen == true)
                            when {
                                viewModel.state.amount.parseToDouble() < viewModel.state.minLimit -> showUpperLowerLimitError()
                                isOtpRequired() -> startOtpFragment()
                                else -> viewModel.addFunds()
                            }
                        else {
                            if (viewModel.state.amount.parseToDouble() < viewModel.state.minLimit)
                                showUpperLowerLimitError()
                            else
                                viewModel.removeFunds()
                        }
                    } else {
                        if (isAddFundScreen == true) co.yap.yapcore.AdjustEvents.trackAdjustPlatformEvent(
                            AdjustEvents.TOP_UP_END.type
                        )
                        if (fundsAddedRemoveSuccess) {
                            setupActionsIntent()
                        }
                        this.finish()
                    })
                }
            R.id.ivCross -> this.finish()
            R.id.tbIvClose -> this.finish()

            viewModel.EVENT_ADD_FUNDS_SUCCESS, viewModel.EVENT_REMOVE_FUNDS_SUCCESS -> {
                fundsAddedRemoveSuccess = true
                setUpSuccessData()
                performSuccessOperations()
                etAmount.visibility = View.GONE
                btnAction.text =
                    getString(Strings.screen_success_funds_transaction_display_text_button)
            }
        }
    }

    private fun setupData() {
        card = intent.getParcelableExtra(CARD)
        viewModel.state.cardNumber = card?.maskedCardNo ?: ""
        viewModel.cardSerialNumber = card?.cardSerialNumber ?: ""

        if (Constants.CARD_TYPE_PREPAID == card?.cardType) {
            if (card?.physical!!) {
                viewModel.state.cardName = Constants.TEXT_SPARE_CARD_PHYSICAL
            } else {
                viewModel.state.cardName = Constants.TEXT_SPARE_CARD_VIRTUAL
            }
        }
        viewModel.state.availableBalance =
            MyUserManager.cardBalance.value?.availableBalance.toString()
        viewModel.state.availableBalanceText = if (viewModel.state.isAddFundScreen.get() == true)
            viewModel.state.availableBalance.toFormattedAmountWithCurrency()
        else
            card?.availableBalance?.toFormattedAmountWithCurrency() ?: "0.0"

        setEditTextWatcher()
    }

    private fun setEditTextWatcher() {
        etAmount.filters =
            arrayOf(InputFilter.LengthFilter(7), DecimalDigitsInputFilter(2))

        etAmount.afterTextChanged {
            if (!viewModel.state.amount.isNullOrBlank() && viewModel.state.amount.parseToDouble() > 0.0) {
                checkOnTextChangeValidation()
            } else {
                removeErrorBg()
                viewModel.state.valid = false
                cancelAllSnackBar()
            }
            parentViewModel?.updateFees(viewModel.state.amount ?: "")
        }
    }

    private fun checkOnTextChangeValidation() {
        when {
            isBalanceAvailable() -> {
                setErrorBg()
                showBalanceNotAvailableError()
            }
            isDailyLimitReached() -> {
                setErrorBg()
                showErrorSnackBar(viewModel.state.errorDescription, Snackbar.LENGTH_INDEFINITE)
            }
            viewModel.state.amount.parseToDouble() < viewModel.state.minLimit -> {
                viewModel.state.valid = true
            }
            viewModel.state.amount.parseToDouble() > viewModel.state.maxLimit -> {
                setErrorBg()
                showUpperLowerLimitError()
            }
            isTopUpLimitReached() -> {
                setErrorBg()
                showErrorSnackBar(viewModel.state.errorDescription, Snackbar.LENGTH_INDEFINITE)
            }
            else -> {
                removeErrorBg()
            }
        }
    }

    private fun isTopUpLimitReached(): Boolean {
        return viewModel.transactionThreshold.value?.let { threshold ->
            return when {
                card?.availableBalance.parseToDouble() == threshold.virtualCardBalanceLimit ?: 0.0 -> {
                    viewModel.state.errorDescription = Translator.getString(
                        this,
                        Strings.screen_add_funds_display_text_error_card_balance_limit_reached,
                        threshold.virtualCardBalanceLimit.toString().toFormattedAmountWithCurrency()
                    )
                    return true
                }
                viewModel.state.amount.parseToDouble()
                    .plus(card?.availableBalance.parseToDouble()) > threshold.virtualCardBalanceLimit ?: 0.0 -> {
                    viewModel.state.errorDescription = Translator.getString(
                        this,
                        Strings.screen_add_funds_display_text_error_card_balance_limit,
                        threshold.virtualCardBalanceLimit.toString()
                            .toFormattedAmountWithCurrency(),
                        (threshold.virtualCardBalanceLimit?.minus(card?.availableBalance.parseToDouble())).toString()
                            .toFormattedAmountWithCurrency()
                    )
                    return true
                }
                else -> false
            }
        } ?: false
    }

    private fun showUpperLowerLimitError() {
        viewModel.state.errorDescription = Translator.getString(
            this,
            Strings.common_display_text_min_max_limit_error_transaction,
            viewModel.state.minLimit.toString().toFormattedAmountWithCurrency(),
            viewModel.state.maxLimit.toString().toFormattedAmountWithCurrency()
        )
        showErrorSnackBar(viewModel.state.errorDescription, Snackbar.LENGTH_INDEFINITE)

    }

    private fun showBalanceNotAvailableError() {
        viewModel.state.errorDescription = Translator.getString(
            context,
            Strings.common_display_text_available_balance_error,
            viewModel.state.amount.toFormattedAmountWithCurrency()
        )
        showErrorSnackBar(viewModel.state.errorDescription, Snackbar.LENGTH_INDEFINITE)
    }


    private fun isBalanceAvailable(): Boolean {
        return if (viewModel.state.isAddFundScreen.get() == true) getAmountWithFee() > viewModel.state.availableBalance.parseToDouble()
        else viewModel.state.amount.parseToDouble() > card?.availableBalance.parseToDouble()
    }

    private fun setErrorBg() {
        viewModel.state.amountBackground =
            this.resources.getDrawable(
                co.yap.yapcore.R.drawable.bg_funds_error,
                null
            )
        viewModel.state.valid = false
    }

    private fun removeErrorBg() {
        viewModel.state.amountBackground =
            this.resources.getDrawable(
                co.yap.yapcore.R.drawable.bg_funds,
                null
            )
        cancelAllSnackBar()
        viewModel.state.valid = true
    }

    private fun showErrorSnackBar(errorMessage: String, length: Int) {
        getSnackBarFromQueue(0)?.let {
            if (it.isShown) {
                it.updateSnackBarText(errorMessage)
            }
        } ?: clFTSnackbar.showSnackBar(
            msg = viewModel.state.errorDescription,
            viewBgColor = R.color.errorLightBackground, gravity = Gravity.TOP,
            colorOfMessage = R.color.error, duration = length, marginTop = 0
        )
    }

    private fun performSuccessOperations() {
        YoYo.with(Techniques.FadeOut)
            .duration(300)
            .repeat(0)
            .playOn(tbIvClose)
        clBottom.children.forEach { it.alpha = 0f }
        btnAction.alpha = 0f
        clRightData.children.forEach { it.alpha = 0f }
        Handler(Looper.getMainLooper()).postDelayed({ runAnimations() }, 1500)
        runCardAnimation()
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
        val checkBtnEndPosition = (cardInfoLayout.measuredWidth / 2) - (ivCustomCard.width / 2)
        return AnimationUtils.runSequentially(
            AnimationUtils.slideHorizontal(
                view = ivCustomCard,
                from = ivCustomCard.x,
                to = checkBtnEndPosition.toFloat(),
                duration = 500
            ),
            AnimationUtils.pulse(ivCustomCard)
        )
    }

    override fun onDestroy() {
        viewModel.clickEvent.removeObservers(this)
        super.onDestroy()
    }

    private fun setUpSuccessData() {
        val successString: String = if (viewModel.state.isAddFundScreen.get() == true) {
            getString(Strings.screen_success_funds_transaction_display_text_top_up)
        } else {
            getString(Strings.screen_success_remove_funds_transaction_display_text_moved_success)
        }
        viewModel.state.topUpSuccess =
            successString.format(
                viewModel.state.currencyType,
                viewModel.state.amount?.toFormattedCurrency()
            )

        val fcs = ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorPrimaryDark))

        val separated = viewModel.state.topUpSuccess.split(viewModel.state.currencyType)
        val str = SpannableStringBuilder(viewModel.state.topUpSuccess)

        str.setSpan(
            fcs,
            separated[0].length,
            separated[0].length + viewModel.state.currencyType.length + (viewModel.state.amount?.toFormattedCurrency()?.length
                ?: 0) + 1,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        tvTopUp.text = str

        viewModel.state.primaryCardUpdatedBalance =
            getString(Strings.screen_success_funds_transaction_display_text_primary_balance).format(
                viewModel.state.currencyType,
                MyUserManager.cardBalance.value?.availableBalance.toString().toFormattedCurrency()
            )

        val separatedPrimary =
            viewModel.state.primaryCardUpdatedBalance.split(viewModel.state.currencyType)
        val primaryStr = SpannableStringBuilder(viewModel.state.primaryCardUpdatedBalance)

        primaryStr.setSpan(
            fcs,
            separatedPrimary[0].length,
            primaryStr.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        tvPrimaryCardBalance.text = primaryStr

        updatedSpareCardBalance = if (viewModel.state.isAddFundScreen.get() == true) {
            (card!!.availableBalance.toDouble() + viewModel.state.amount!!.toDouble()).toString()
        } else {
            (card!!.availableBalance.toDouble() - viewModel.state.amount!!.toDouble()).toString()
        }

        viewModel.state.spareCardUpdatedBalance =
            getString(Strings.screen_success_funds_transaction_display_text_success_updated_prepaid_card_balance).format(
                viewModel.state.currencyType,
                updatedSpareCardBalance.toFormattedCurrency()
            )

        val separatedSpare =
            viewModel.state.spareCardUpdatedBalance.split(viewModel.state.currencyType)
        val spareStr = SpannableStringBuilder(viewModel.state.spareCardUpdatedBalance)

        spareStr.setSpan(
            fcs,
            separatedSpare[0].length,
            spareStr.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        tvNewSpareCardBalance.text = spareStr
    }

    override fun onBackPressed() {
    }

    private fun setupActionsIntent() {
        val returnIntent = Intent()
        returnIntent.putExtra("newBalance", updatedSpareCardBalance)
        setResult(Activity.RESULT_OK, returnIntent)
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
                viewModel.addFunds()
            }
        }
    }

    private fun isDailyLimitReached(): Boolean {
        viewModel.transactionThreshold.value?.let {
            it.dailyLimitTopUpSupplementary?.let { dailyLimit ->
                it.totalDebitAmountTopUpSupplementary?.let { totalConsumedAmount ->
                    viewModel.state.amount?.toDoubleOrNull()?.let { enteredAmount ->
                        val remainingDailyLimit =
                            if ((dailyLimit - totalConsumedAmount) < 0.0) 0.0 else (dailyLimit - totalConsumedAmount)
                        if (enteredAmount > remainingDailyLimit.roundVal()) viewModel.state.errorDescription =
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
        viewModel.transactionThreshold.value?.let {
            it.totalDebitAmountTopUpSupplementary?.let { totalTopupConsumedAmount ->
                viewModel.state.amount?.toDoubleOrNull()?.let { enteredAmount ->
                    val remainingOtpLimit =
                        it.otpLimitTopUpSupplementary?.minus(totalTopupConsumedAmount)
                    return enteredAmount > (remainingOtpLimit ?: 0.0)
                } ?: return false
            } ?: return false
        } ?: return false
    }

}