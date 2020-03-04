package co.yap.modules.dashboard.cards.paymentcarddetail.statments.activities

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.cards.paymentcarddetail.statments.adaptor.CardStatementsAdaptor
import co.yap.modules.dashboard.cards.paymentcarddetail.statments.interfaces.ICardStatments
import co.yap.modules.dashboard.cards.paymentcarddetail.statments.viewmodels.CardStatementsViewModel
import co.yap.networking.cards.responsedtos.Card
import co.yap.networking.transactions.responsedtos.CardStatement
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.interfaces.OnItemClickListener

class CardStatementsActivity : BaseBindingActivity<ICardStatments.ViewModel>(),
    ICardStatments.View {

    companion object {
        const val CARD = "card"
        var isFromDrawer: Boolean = false
        fun newIntent(context: Context, card: Card, IsFromDrawer: Boolean = false): Intent {
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
        viewModel.card = intent.getParcelableExtra(CARD)
        isFromDrawer = intent.getBooleanExtra("isFromDrawer", false)
        if (isFromDrawer) {
            viewModel.loadStatementsFromDashBoard()
        } else {
            viewModel.loadStatements(viewModel.card.cardSerialNumber)
        }
        viewModel.clickEvent.observe(this, Observer {
            if (it == R.id.tbBtnBack) {
                onBackPressed()
            }
        })
        viewModel.adapter.set(CardStatementsAdaptor(mutableListOf()))
        viewModel.adapter.get()?.allowFullItemClickListener = true
        viewModel.adapter.get()?.setItemListener(listener)
    }

    val listener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            val browserIntent =
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("http://docs.google.com/gview?embedded=true&url=" + (data as CardStatement).statementURL)
                )
            startActivity(browserIntent)
        }
    }
}
