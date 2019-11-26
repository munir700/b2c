package co.yap.modules.dashboard.cards.analytics.fragments

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentCategoryAnalyticsBinding
import co.yap.modules.dashboard.cards.analytics.adaptors.CategoryAnalyticsAdaptor
import co.yap.modules.dashboard.cards.analytics.interfaces.ICategoryAnalytics
import co.yap.modules.dashboard.cards.analytics.main.fragments.CardAnalyticsBaseFragment
import co.yap.modules.dashboard.cards.analytics.viewmodels.CategoryAnalyticsViewModel
import co.yap.yapcore.interfaces.OnItemClickListener
import kotlinx.android.synthetic.main.item_analytics.view.*


class CategoryAnalyticsFragment : CardAnalyticsBaseFragment<ICategoryAnalytics.ViewModel>(),
    ICategoryAnalytics.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_category_analytics

    override val viewModel: ICategoryAnalytics.ViewModel
        get() = ViewModelProviders.of(this).get(CategoryAnalyticsViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
        initAdaptor()
    }

    private fun setObservers() {
        viewModel.parentViewModel.categoryAnalyticsItemLiveData.observe(this, Observer {
            getAdaptor().setList(it)
        })
        viewModel.parentViewModel.selectedItemPositionParent.observe(this, Observer {
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
        getBinding().recycler.adapter = CategoryAnalyticsAdaptor(mutableListOf())
        getAdaptor().setItemListener(listener)
    }

    val listener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            highlightSelectedItem(view, pos)
            viewModel.parentViewModel.selectedItemPosition.value = pos
        }
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
                    SCROLL_STATE_IDLE -> {
                        val pos = viewModel.parentViewModel.selectedItemPositionParent.value
                        pos?.let {
                            val view = getBinding().recycler.layoutManager?.findViewByPosition(it)
                            highlightSelectedItem(view, it)
                        }
                    }
                }
            }
        }

    private fun getAdaptor(): CategoryAnalyticsAdaptor {
        return getBinding().recycler.adapter as CategoryAnalyticsAdaptor
    }

    private fun getBinding(): FragmentCategoryAnalyticsBinding {
        return (viewDataBinding as FragmentCategoryAnalyticsBinding)
    }

}