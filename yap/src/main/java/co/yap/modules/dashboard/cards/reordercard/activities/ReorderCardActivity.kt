package co.yap.modules.dashboard.cards.reordercard.activities

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.cards.addpaymentcard.interfaces.IAddPaymentCard
import co.yap.modules.dashboard.cards.addpaymentcard.viewmodels.AddPaymentCardViewModel
import co.yap.modules.dashboard.cards.reportcard.activities.ReportLostOrStolenCardActivity
import co.yap.networking.cards.responsedtos.Card
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.IFragmentHolder
import co.yap.yapcore.defaults.DefaultNavigator
import co.yap.yapcore.defaults.INavigator
import co.yap.yapcore.interfaces.IBaseNavigator

class ReorderCardActivity : BaseBindingActivity<IAddPaymentCard.ViewModel>(),
    INavigator,
    IFragmentHolder {
    companion object {
        private const val CARD = "card"
        fun newIntent(context: Context, card: Card): Intent {
            val intent = Intent(context, ReportLostOrStolenCardActivity::class.java)
            intent.putExtra(CARD, card)
            return intent
        }
    }

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_reorder_card

    override val viewModel: IAddPaymentCard.ViewModel
        get() = ViewModelProviders.of(this).get(AddPaymentCardViewModel::class.java)

    override val navigator: IBaseNavigator
        get() = DefaultNavigator(
            this@ReorderCardActivity,
            R.id.main_report_stolen_cards_nav_host_fragment
        )
}