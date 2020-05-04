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
import co.yap.modules.dashboard.cards.paymentcarddetail.addfunds.viewmodels.AddFundsViewModel
import co.yap.modules.dashboard.cards.paymentcarddetail.addfunds.viewmodels.FundActionsViewModel
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
import co.yap.yapcore.helpers.AnimationUtils
import co.yap.yapcore.helpers.DecimalDigitsInputFilter
import co.yap.yapcore.helpers.extentions.afterTextChanged
import co.yap.yapcore.helpers.extentions.startFragmentForResult
import co.yap.yapcore.helpers.extentions.toFormattedAmountWithCurrency
import co.yap.yapcore.helpers.extentions.toFormattedCurrency
import co.yap.yapcore.helpers.showTextUpdatedAbleSnackBar
import co.yap.yapcore.helpers.spannables.color
import co.yap.yapcore.helpers.spannables.getText
import co.yap.yapcore.managers.MyUserManager
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_fund_actions.*
import kotlinx.android.synthetic.main.layout_card_info.*
import kotlinx.android.synthetic.main.layout_fund_actions_tool_bar.*


open class AddFundsActivity : BaseBindingActivity<IFundActions.ViewModel>(),
    IFundActions.View {

    var amount: String? = null
    private val windowSize: Rect = Rect()
    var card: Card? = null
    private lateinit var updatedSpareCardBalance: String
    private var fundsAdded: Boolean = false
    private var parentViewModel: FundActionsViewModel? = null

    companion object {
        private const val CARD = "card"
        fun newIntent(context: Context, card: Card): Intent {
            val intent = Intent(context, AddFundsActivity::class.java)
            intent.putExtra(CARD, card)
            return intent
        }
    }

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_fund_actions

    override val viewModel: IFundActions.ViewModel
        get() = ViewModelProviders.of(this).get(AddFundsViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parentViewModel =
            this.let { ViewModelProviders.of(it).get(FundActionsViewModel::class.java) }
        parentViewModel?.getTransferFees(TransactionProductCode.TOP_UP_SUPPLEMENTARY_CARD.pCode)
        val display = this.windowManager.defaultDisplay
        display.getRectSize(windowSize)
        clBottomNew.children.forEach { it.alpha = 0f }
        setObservers()
        setupData()
        viewModel.errorEvent.observe(this, Observer {
            showErrorSnackBar()
        })

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
            R.id.btnAction -> (if (viewModel.state.buttonTitle != getString(Strings.screen_success_funds_transaction_display_text_button)) {
                if (!isDailyLimitReached())
                    if (isOtpRequired()) startOtpFragment() else viewModel.addFunds()
                else
                    viewModel.errorEvent.call()

            } else {
                co.yap.yapcore.AdjustEvents.trackAdjustPlatformEvent(AdjustEvents.TOP_UP_END.type)
                if (fundsAdded) {
                    setupActionsIntent()
                }

                this.finish()
            })

            R.id.ivCross -> this.finish()
            R.id.tbIvClose -> this.finish()

            viewModel.EVENT_ADD_FUNDS_SUCCESS -> {
                fundsAdded = true
                setUpSuccessData()
                performSuccessOperations()
                etAmount.visibility = View.GONE
                viewModel.state.buttonTitle =
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
        viewModel.state.availableBalanceText =
            " " + getString(Strings.common_text_currency_type) + " " +
                    viewModel.state.availableBalance.toFormattedCurrency()

        setEditTextWatcher()
    }

    private fun setEditTextWatcher() {
        etAmount.filters =
            arrayOf(InputFilter.LengthFilter(7), DecimalDigitsInputFilter(2))

        etAmount.afterTextChanged {
                parentViewModel?.updateFees(viewModel.state.amount ?: "")
        }
    }

    private fun showErrorSnackBar() {
        showTextUpdatedAbleSnackBar(viewModel.state.errorDescription, Snackbar.LENGTH_INDEFINITE)
    }

    fun performSuccessOperations() {
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

        viewModel.state.topUpSuccess =
            getString(Strings.screen_success_funds_transaction_display_text_top_up).format(
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

        updatedSpareCardBalance =
            (card!!.availableBalance.toDouble() + viewModel.state.amount!!.toDouble()).toString()

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
                        if (enteredAmount > remainingDailyLimit) viewModel.state.errorDescription =
                            Translator.getString(
                                this,
                                Strings.common_display_text_daily_limit_error
                            ).format(remainingDailyLimit.toString().toFormattedAmountWithCurrency())

                        return enteredAmount > remainingDailyLimit

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
                    return enteredAmount >= (remainingOtpLimit ?: 0.0)
                } ?: return false
            } ?: return false
        } ?: return false
    }

}