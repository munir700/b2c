package co.yap.modules.dashboard.cards.analytics.fragments

import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.cards.analytics.viewmodels.CardAnalyticsDetailsViewModel
import co.yap.yapcore.BaseBindingFragment

class CardAnalyticsDetailsFragment : BaseBindingFragment<CardAnalyticsDetailsViewModel>() {
    override fun getBindingVariable() = BR.cardAnalyticsDetailsViewModel

    override fun getLayoutId() = R.layout.fragment_card_analytics_details

    override val viewModel: CardAnalyticsDetailsViewModel
        get() = ViewModelProviders.of(this).get(CardAnalyticsDetailsViewModel::class.java)
}