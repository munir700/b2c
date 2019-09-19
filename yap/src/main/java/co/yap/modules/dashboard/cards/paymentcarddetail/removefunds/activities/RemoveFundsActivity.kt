package co.yap.modules.dashboard.cards.paymentcarddetail.removefunds.activities

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.R
import co.yap.modules.dashboard.cards.paymentcarddetail.addfunds.activities.AddFundsActivity
import co.yap.modules.dashboard.cards.paymentcarddetail.addfunds.interfaces.IFundActions
import co.yap.modules.dashboard.cards.paymentcarddetail.removefunds.viewmodels.RemoveFundsViewModel
import co.yap.translation.Strings
import co.yap.yapcore.helpers.Utils
import kotlinx.android.synthetic.main.activity_fund_actions.*
import okhttp3.internal.Util

class RemoveFundsActivity : AddFundsActivity() {
    override val viewModel: IFundActions.ViewModel
        get() = ViewModelProviders.of(this).get(RemoveFundsViewModel::class.java)

    override fun setObservers() {
        viewModel.clickEvent.observe(this, Observer {
            when (it) {
                R.id.btnAction -> (if (viewModel.state.buttonTitle != getString(Strings.screen_success_funds_transaction_display_text_button)) {
                    viewModel.state.topUpSuccess =
                        getString(Strings.screen_success_remove_funds_transaction_display_text_moved_success).format(
                            viewModel.state.currencyType,
                            Utils.getFormattedCurrency(viewModel.state.amount)
                        )
                    performSuccessOperations()
                    etAmount.visibility = View.GONE
                    viewModel.state.buttonTitle =
                        getString(Strings.screen_success_funds_transaction_display_text_button)
                } else {
                    this.finish()
                })
                R.id.ivCross -> this.finish()
            }


        })

    }

    override fun onDestroy() {
        viewModel.clickEvent.removeObservers(this)
        super.onDestroy()

    }

}