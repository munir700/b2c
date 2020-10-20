package co.yap.modules.dashboard.cards.analytics.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.cards.analytics.adaptors.CardAnalyticsDetailsAdapter
import co.yap.modules.dashboard.cards.analytics.interfaces.ICardAnalyticsDetails
import co.yap.modules.dashboard.cards.analytics.viewmodels.CardAnalyticsDetailsViewModel
import co.yap.networking.transactions.responsedtos.TxnAnalytic
import co.yap.widgets.DividerItemDecoration
import co.yap.widgets.MultiStateView
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.helpers.extentions.dimen
import kotlinx.android.synthetic.main.fragment_card_analytics_details.*

class CardAnalyticsDetailsFragment : BaseBindingFragment<ICardAnalyticsDetails.ViewModel>() {
    override fun getBindingVariable() = BR.cardAnalyticsDetailsViewModel

    override fun getLayoutId() = R.layout.fragment_card_analytics_details


    override val viewModel: ICardAnalyticsDetails.ViewModel
        get() = ViewModelProviders.of(this).get(CardAnalyticsDetailsViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.adapter.set(CardAnalyticsDetailsAdapter(mutableListOf(), null))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            var Model = it.get("DATA") as TxnAnalytic
            viewModel.state.toolbarTitle = Model.title?:"Some Title"
        }
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