package co.yap.modules.dashboard.cards.analytics.fragments

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentMerchantAnalyticsBinding
import co.yap.modules.dashboard.cards.analytics.adaptors.MerchantAnalyticsAdaptor
import co.yap.modules.dashboard.cards.analytics.interfaces.IMerchantAnalytics
import co.yap.modules.dashboard.cards.analytics.main.fragments.CardAnalyticsBaseFragment
import co.yap.modules.dashboard.cards.analytics.viewmodels.MerchantAnalyticsViewModel
import co.yap.networking.transactions.responsedtos.TxnAnalytic
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.interfaces.OnItemClickListener
import kotlinx.android.synthetic.main.item_analytics.view.*

class MerchantAnalyticsFragment : CardAnalyticsBaseFragment<IMerchantAnalytics.ViewModel>(),
    IMerchantAnalytics.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_merchant_analytics

    override val viewModel: IMerchantAnalytics.ViewModel
        get() = ViewModelProviders.of(this).get(MerchantAnalyticsViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdaptor()
        setObservers()
    }

    override fun setObservers() {
        viewModel.pViewModel.merchantAnalyticsItemLiveData?.observe(this, Observer {
            if (it == null) {
                return@Observer
            }
            getAdaptor().setList(it)
        })

        viewModel.pViewModel.selectedItemPositionParent.observe(this, Observer {
            val view = getBinding().recycler.layoutManager?.findViewByPosition(it)
            if (null != view) {
                highlightSelectedItem(view, it)
            } else {
                getBinding().recycler.removeOnScrollListener(onScrollListener)
                getBinding().recycler.addOnScrollListener(onScrollListener)
                getBinding().recycler.smoothScrollToPosition(it)
            }
        })
    }

    private fun initAdaptor() {
        Constants.MERCHANT_TYPE = "merchant-name"
        getBinding().recycler.adapter = MerchantAnalyticsAdaptor(mutableListOf())
        getAdaptor().setItemListener(listener)
    }

    val listener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            //    highlightSelectedItem(view, pos)
            viewModel.pViewModel.selectedItemPosition.value = pos
            navigateDetails(pos)
        }
    }

    private fun navigateDetails(pos: Int) {
        val selectedItem = getAdaptor().getDataForPosition(pos)
        navigate(
            R.id.cardAnalyticsDetailsFragment,
            bundleOf(
                Constants.TRANSACTION_DETAIL to TxnAnalytic(
                    title = selectedItem.title,
                    txnCount = selectedItem.txnCount,
                    totalSpending = selectedItem.totalSpending,
                    logoUrl = selectedItem.logoUrl,
                    totalSpendingInPercentage = selectedItem.totalSpendingInPercentage
                )
            )
        )
    }

    private fun highlightSelectedItem(view: View?, pos: Int) {
        val colors = resources.getIntArray(co.yap.yapcore.R.array.analyticsColors)
        if (getAdaptor().checkedPosition != pos) {
            view?.let {
                it.isSelected = true
                it.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.itemBackground
                    )
                )
                it.tvName.setTextColor(colors[pos % colors.size])
                getAdaptor().notifyItemChanged(getAdaptor().checkedPosition)
                getAdaptor().checkedPosition = pos
            }
        }
    }

    private val onScrollListener: RecyclerView.OnScrollListener =
        object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(
                recyclerView: RecyclerView,
                newState: Int
            ) {
                when (newState) {
                    RecyclerView.SCROLL_STATE_IDLE -> {
                        val pos = viewModel.pViewModel.selectedItemPositionParent.value
                        pos?.let {
                            val view = getBinding().recycler.layoutManager?.findViewByPosition(it)
                            highlightSelectedItem(view, it)
                        }
                    }
                }
            }
        }

    private fun getAdaptor(): MerchantAnalyticsAdaptor {
        return getBinding().recycler.adapter as MerchantAnalyticsAdaptor
    }

    private fun getBinding(): FragmentMerchantAnalyticsBinding {
        return (viewDataBinding as FragmentMerchantAnalyticsBinding)
    }

}