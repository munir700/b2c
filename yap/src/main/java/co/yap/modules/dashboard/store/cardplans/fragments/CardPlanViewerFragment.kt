package co.yap.modules.dashboard.store.cardplans.fragments

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.store.cardplans.CardsFragmentAdapter
import co.yap.modules.dashboard.store.cardplans.interfaces.ICardViewer
import co.yap.modules.dashboard.store.cardplans.viewmodels.CardPlanViewerViewModel
import co.yap.sendmoney.y2y.home.adaptors.TransferLandingAdaptor
import kotlinx.android.synthetic.main.fragment_viewer_card_plans.*

class CardPlanViewerFragment : CardPlansBaseFragment<ICardViewer.ViewModel>(), ICardViewer.View {
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_viewer_card_plans

    override val viewModel: ICardViewer.ViewModel
        get() = ViewModelProviders.of(this).get(CardPlanViewerViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    private fun setupAdaptor() {
        val adaptor = CardsFragmentAdapter(this)
        cardViewPager.adapter = adaptor
    }
}