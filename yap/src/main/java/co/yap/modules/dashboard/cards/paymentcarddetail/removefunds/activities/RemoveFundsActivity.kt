package co.yap.modules.dashboard.cards.paymentcarddetail.removefunds.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputFilter
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.R
import co.yap.modules.dashboard.cards.paymentcarddetail.addfunds.activities.AddFundsActivity
import co.yap.modules.dashboard.cards.paymentcarddetail.addfunds.interfaces.IFundActions
import co.yap.modules.dashboard.cards.paymentcarddetail.addfunds.viewmodels.FundActionsViewModel
import co.yap.modules.dashboard.cards.paymentcarddetail.removefunds.viewmodels.RemoveFundsViewModel
import co.yap.modules.others.helper.Constants
import co.yap.networking.cards.responsedtos.Card
import co.yap.networking.cards.responsedtos.CardBalance
import co.yap.translation.Strings
import co.yap.yapcore.enums.TransactionProductCode
import co.yap.yapcore.helpers.DecimalDigitsInputFilter
import co.yap.yapcore.helpers.extentions.afterTextChanged
import co.yap.yapcore.helpers.extentions.toFormattedCurrency
import co.yap.yapcore.helpers.spannables.color
import co.yap.yapcore.helpers.spannables.getText
import co.yap.yapcore.managers.MyUserManager
import kotlinx.android.synthetic.main.activity_fund_actions.*

class RemoveFundsActivity : AddFundsActivity() {

    private var fundsRemoved: Boolean = false
    private lateinit var updatedSpareCardBalance: String
    private var parentViewModel: FundActionsViewModel? = null

    companion object {
        private const val CARD = "card"
        fun newIntent(context: Context, card: Card): Intent {
            val intent = Intent(context, RemoveFundsActivity::class.java)
            intent.putExtra(CARD, card)
            return intent
        }
    }

    override val viewModel: IFundActions.ViewModel
        get() = ViewModelProviders.of(this).get(RemoveFundsViewModel::class.java)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parentViewModel =
            this.let { ViewModelProviders.of(it).get(FundActionsViewModel::class.java) }
        parentViewModel?.getTransferFees(TransactionProductCode.WITHDRAW_SUPPLEMENTARY_CARD.pCode)
        setupData()
        viewModel.firstDenominationClickEvent.observe(this, Observer {
            hideKeyboard()
            etAmount.setText("")
            etAmount.append(viewModel.state.denominationAmount)
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
        parentViewModel?.isFeeReceived?.observe(this, Observer {
            if (it) parentViewModel?.updateFees(viewModel.state.amount ?: "")
        })
        parentViewModel?.updatedFee?.observe(this, Observer {
            if (it.isNotBlank()) setSpannableFee(it)
        })
        viewModel.clickEvent.observe(this, clickEvent)
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
                viewModel.removeFunds()
            } else {
                if (fundsRemoved) {
                    setupActionsIntent()
                }
                this.finish()
            })
            R.id.ivCross -> this.finish()
            R.id.tbIvClose -> this.finish()

            viewModel.EVENT_REMOVE_FUNDS_SUCCESS -> {
                fundsRemoved = true
                setUpSuccessData()
                performSuccessOperations()
                etAmount.visibility = View.GONE
                viewModel.state.buttonTitle =
                    getString(Strings.screen_success_funds_transaction_display_text_button)
            }
        }
    }

    private fun setupData() {
        val card: Card = intent.getParcelableExtra(CARD)
        viewModel.state.cardNumber = card.maskedCardNo
        viewModel.cardSerialNumber = card.cardSerialNumber
        if (Constants.CARD_TYPE_PREPAID == card.cardType) {
            if (card.physical) {
                viewModel.state.cardName = Constants.TEXT_SPARE_CARD_PHYSICAL
            } else {
                viewModel.state.cardName = Constants.TEXT_SPARE_CARD_VIRTUAL
            }
        }
        viewModel.state.availableBalance = card.availableBalance
//        viewModel.state.availableBalance =  MyUserManager.cardBalance.value?.availableBalance.toString()
        viewModel.state.availableBalanceText =
            " " + getString(Strings.common_text_currency_type) + " " +
                    card.availableBalance.toFormattedCurrency()

        setEditTextWatcher()
    }

    override fun onDestroy() {
        viewModel.clickEvent.removeObservers(this)
        super.onDestroy()
    }

    private fun setUpSuccessData() {
        viewModel.state.topUpSuccess =
            getString(Strings.screen_success_remove_funds_transaction_display_text_moved_success).format(
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

        val updatedCardBalance: String =
            (MyUserManager.cardBalance.value?.availableBalance.toString()
                .toDouble() + viewModel.state.amount!!.toDouble()).toString()
        MyUserManager.cardBalance.value =
            CardBalance(availableBalance = updatedCardBalance)

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
            (viewModel.state.availableBalance.toDouble() - viewModel.state.amount!!.toDouble()).toString()

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

    private fun setEditTextWatcher() {
        etAmount.filters =
            arrayOf(InputFilter.LengthFilter(7), DecimalDigitsInputFilter(2))

        etAmount.afterTextChanged {
            parentViewModel?.updateFees(viewModel.state.amount ?: "")
        }
    }
    private fun setupActionsIntent() {
        val returnIntent = Intent()
        returnIntent.putExtra("newBalance", updatedSpareCardBalance)
        setResult(Activity.RESULT_OK, returnIntent)
    }
}