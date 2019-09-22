package co.yap.modules.dashboard.cards.paymentcarddetail.removefunds.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.R
import co.yap.modules.dashboard.cards.paymentcarddetail.addfunds.activities.AddFundsActivity
import co.yap.modules.dashboard.cards.paymentcarddetail.addfunds.interfaces.IFundActions
import co.yap.modules.dashboard.cards.paymentcarddetail.removefunds.viewmodels.RemoveFundsViewModel
import co.yap.networking.cards.responsedtos.Card
import co.yap.translation.Strings
import co.yap.yapcore.helpers.Utils
import kotlinx.android.synthetic.main.activity_fund_actions.*

class RemoveFundsActivity : AddFundsActivity() {

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
    }
    override fun setObservers() {
        viewModel.clickEvent.observe(this, Observer {
            when (it) {
                R.id.btnAction -> (if (viewModel.state.buttonTitle != getString(Strings.screen_success_funds_transaction_display_text_button)) {
                    viewModel.removeFunds()
                } else {
                    this.finish()
                })
                R.id.ivCross -> this.finish()

                viewModel.EVENT_REMOVE_FUNDS_SUCCESS ->{
                    viewModel.state.topUpSuccess =
                        getString(Strings.screen_success_remove_funds_transaction_display_text_moved_success).format(
                            viewModel.state.currencyType,
                            Utils.getFormattedCurrency(viewModel.state.amount)
                        )
                    performSuccessOperations()
                    etAmount.visibility = View.GONE
                    viewModel.state.buttonTitle =
                        getString(Strings.screen_success_funds_transaction_display_text_button)
                }
            }


        })

    }

    private fun setupData() {
        val card: Card = intent.getParcelableExtra(CARD)
        viewModel.state.cardNumber = card.maskedCardNo
        viewModel.cardSerialNumber = card.cardSerialNumber
        viewModel.state.cardName = card.cardName
        viewModel.state.availableBalanceText =  " " + getString(Strings.common_text_currency_type) + " "+ card.availableBalance
    }

    override fun onDestroy() {
        viewModel.clickEvent.removeObservers(this)
        super.onDestroy()

    }

}