package co.yap.modules.dashboard.store.cardplans.fragments

import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.store.cardplans.interfaces.ICardViewer
import co.yap.modules.dashboard.store.cardplans.viewmodels.CardPlanViewerViewModel

class CardPlanViewerFragment : CardPlansBaseFragment<ICardViewer.ViewModel>(), ICardViewer.View {
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_viewer_card_plans

    override val viewModel: ICardViewer.ViewModel
        get() = ViewModelProviders.of(this).get(CardPlanViewerViewModel::class.java)
}