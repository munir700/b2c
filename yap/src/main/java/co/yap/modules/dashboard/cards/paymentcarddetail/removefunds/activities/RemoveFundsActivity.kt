package co.yap.modules.dashboard.cards.paymentcarddetail.removefunds.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
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
import co.yap.modules.dashboard.cards.paymentcarddetail.removefunds.viewmodels.RemoveFundsViewModel
import co.yap.modules.others.helper.Constants
import co.yap.networking.cards.responsedtos.Card
import co.yap.networking.cards.responsedtos.CardBalance
import co.yap.translation.Strings
import co.yap.yapcore.enums.TransactionProductCode
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.spannables.color
import co.yap.yapcore.helpers.spannables.getText
import co.yap.yapcore.managers.MyUserManager
import kotlinx.android.synthetic.main.activity_fund_actions.*

class RemoveFundsActivity : AddFundsActivity() {

    private var fundsRemoved: Boolean = false
    private lateinit var updatedSpareCardBalance: String

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
        setupData()
        viewModel.firstDenominationClickEvent.observe(this, Observer {
            hideKeyboard()
            etAmount.setText("")
            etAmount.append(viewModel.state.denominationAmount)
            val position=etAmount.length()
            etAmount.setSelection(position)
            etAmount.clearFocus()
        })
        viewModel.secondDenominationClickEvent.observe(this, Observer {
            hideKeyboard()
            etAmount.setText("")
            etAmount.append(viewModel.state.denominationAmount)
            val position=etAmount.length()
            etAmount.setSelection(position)
            etAmount.clearFocus()
        })
        viewModel.thirdDenominationClickEvent.observe(this, Observer {
            hideKeyboard()
            etAmount.setText("")
            etAmount.append(viewModel.state.denominationAmount)
            val position=etAmount.length()
            etAmount.setSelection(position)
            etAmount.clearFocus()
        })
    }

    override fun setObservers() {
        viewModel.getFee(TransactionProductCode.WITHDRAW_SUPPLEMENTARY_CARD.pCode)
        viewModel.clickEvent.observe(this, Observer {
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

                viewModel.EVENT_REMOVE_FUNDS_SUCCESS -> {
                    fundsRemoved = true
                    setUpSuccessData()
                    performSuccessOperations()
                    etAmount.visibility = View.GONE
                    viewModel.state.buttonTitle =
                        getString(Strings.screen_success_funds_transaction_display_text_button)
                }
                co.yap.yapcore.constants.Constants.CARD_FEE -> {
                    viewModel.state.transferFee =
                        resources.getText(
                            getString(Strings.common_text_fee), this.color(
                                R.color.colorPrimaryDark,
                                "${viewModel.state.currencyType} ${Utils.getFormattedCurrency(
                                    viewModel.state.fee
                                )}"
                            )
                        )
                }
            }


        })

    }

    private fun setupData() {
        val card: Card = intent.getParcelableExtra(CARD)
        viewModel.state.cardNumber = card.maskedCardNo
        viewModel.cardSerialNumber = card.cardSerialNumber
        if (Constants.CARD_TYPE_PREPAID == card?.cardType) {
            if(card?.physical!!){
                viewModel.state.cardName = Constants.TEXT_SPARE_CARD_PHYSICAL
            }else{
                viewModel.state.cardName = Constants.TEXT_SPARE_CARD_VIRTUAL
            }
        }
        viewModel.state.availableBalance = card.availableBalance
//        viewModel.state.availableBalance =  MyUserManager.cardBalance.value?.availableBalance.toString()
        viewModel.state.availableBalanceText =
            " " + getString(Strings.common_text_currency_type) + " " + Utils.getFormattedCurrency(
                card.availableBalance
            )
    }

    override fun onDestroy() {
        viewModel.clickEvent.removeObservers(this)
        super.onDestroy()
    }

    private fun setUpSuccessData() {
        viewModel.state.topUpSuccess =
            getString(Strings.screen_success_remove_funds_transaction_display_text_moved_success).format(
                viewModel.state.currencyType,
                Utils.getFormattedCurrency(viewModel.state.amount)
            )

        val fcs = ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorPrimaryDark))

        val separated = viewModel.state.topUpSuccess.split(viewModel.state.currencyType)
        val str = SpannableStringBuilder(viewModel.state.topUpSuccess)

        str.setSpan(
            fcs,
            separated[0].length,
            separated[0].length + viewModel.state.currencyType.length + Utils.getFormattedCurrency(
                viewModel.state.amount
            ).length + 1,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        tvTopUp.text = str

        val updatedCardBalance: String =
            (MyUserManager.cardBalance.value?.availableBalance.toString().toDouble() + viewModel.state.amount!!.toDouble()).toString()
        MyUserManager.cardBalance.value =
            CardBalance(availableBalance = updatedCardBalance)

        viewModel.state.primaryCardUpdatedBalance =
            getString(Strings.screen_success_funds_transaction_display_text_primary_balance).format(
                viewModel.state.currencyType,
                Utils.getFormattedCurrency(MyUserManager.cardBalance.value?.availableBalance.toString())
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
                Utils.getFormattedCurrency(updatedSpareCardBalance)
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
}