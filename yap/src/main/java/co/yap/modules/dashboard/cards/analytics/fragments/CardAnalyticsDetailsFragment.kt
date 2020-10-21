package co.yap.modules.dashboard.cards.analytics.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.cards.analytics.adaptors.CardAnalyticsDetailsAdapter
import co.yap.modules.dashboard.cards.analytics.interfaces.ICardAnalyticsDetails
import co.yap.modules.dashboard.cards.analytics.main.fragments.CardAnalyticsBaseFragment
import co.yap.modules.dashboard.cards.analytics.viewmodels.CardAnalyticsDetailsViewModel
import co.yap.modules.dashboard.home.adaptor.TransactionsListingAdapter
import co.yap.networking.transactions.responsedtos.TxnAnalytic
import co.yap.widgets.DividerItemDecoration
import co.yap.widgets.MultiStateView
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.helpers.extentions.dimen
import kotlinx.android.synthetic.main.fragment_card_analytics_details.*

class CardAnalyticsDetailsFragment : CardAnalyticsBaseFragment<ICardAnalyticsDetails.ViewModel>() {
    override fun getBindingVariable() = BR.cardAnalyticsDetailsViewModel

    override fun getLayoutId() = R.layout.fragment_card_analytics_details


    override val viewModel: CardAnalyticsDetailsViewModel
        get() = ViewModelProviders.of(this).get(CardAnalyticsDetailsViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.adapter.set(TransactionsListingAdapter(mutableListOf()))
        arguments?.let { bundle ->
            bundle.getParcelable<TxnAnalytic>(Constants.TRANSACTION_TITLE)?.let {
                viewModel.parentViewModel?.state?.toolbarTitle = it.title ?: ""
//                viewModel.state.title.set(it.title.toString())
                viewModel.state.totalSpendings.set(it.totalSpending)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.clickEvent?.observe(this, Observer {
            activity?.onBackPressed()
        })
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                R.drawable.line_divider,
                false,
                false, dimen(R.dimen._52sdp)!!
            )
        )
        multiStateView.viewState = MultiStateView.ViewState.CONTENT
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.clickEvent?.removeObservers(this)
    }
}