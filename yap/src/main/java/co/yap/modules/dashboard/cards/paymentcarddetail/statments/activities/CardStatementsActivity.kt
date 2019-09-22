package co.yap.modules.dashboard.cards.paymentcarddetail.statments.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.cards.paymentcarddetail.activities.PaymentCardDetailActivity
import co.yap.modules.dashboard.cards.paymentcarddetail.fragments.CardClickListener
import co.yap.modules.dashboard.cards.paymentcarddetail.statments.interfaces.ICardStatments
import co.yap.modules.dashboard.cards.paymentcarddetail.statments.viewmodels.CardStatementsViewModel
import co.yap.networking.cards.responsedtos.Card
import co.yap.yapcore.BaseBindingActivity

class CardStatementsActivity : BaseBindingActivity<ICardStatments.ViewModel>(),
    ICardStatments.View, CardClickListener {

    companion object {
        private const val CARD = "card"
        fun newIntent(context: Context, card: Card): Intent {
            val intent = Intent(context, CardStatementsActivity::class.java)
            intent.putExtra(CARD, card)
            return intent
        }
    }

    override val viewModel: ICardStatments.ViewModel
        get() = ViewModelProviders.of(this).get(CardStatementsViewModel::class.java)

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_card_statements

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onClick(eventType: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
