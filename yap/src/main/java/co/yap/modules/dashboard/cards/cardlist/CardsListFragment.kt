package co.yap.modules.dashboard.cards.cardlist

import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.main.fragments.YapDashboardChildFragment

class CardsListFragment : YapDashboardChildFragment<ICardsList.ViewModel>(), ICardsList.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_cards_list

    override val viewModel: CardsListViewModel
        get() = ViewModelProviders.of(this).get(CardsListViewModel::class.java)
}