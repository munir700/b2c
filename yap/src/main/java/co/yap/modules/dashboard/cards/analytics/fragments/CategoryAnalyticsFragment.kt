package co.yap.modules.dashboard.cards.analytics.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentCategoryAnalyticsBinding
import co.yap.modules.dashboard.cards.analytics.adaptors.CategoryAnalyticsAdaptor
import co.yap.modules.dashboard.cards.analytics.interfaces.ICategoryAnalytics
import co.yap.modules.dashboard.cards.analytics.main.fragments.CardAnalyticsBaseFragment
import co.yap.modules.dashboard.cards.analytics.models.AnalyticsItem
import co.yap.modules.dashboard.cards.analytics.viewmodels.CategoryAnalyticsViewModel
import co.yap.yapcore.interfaces.OnItemClickListener

class CategoryAnalyticsFragment : CardAnalyticsBaseFragment<ICategoryAnalytics.ViewModel>(),
    ICategoryAnalytics.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_category_analytics

    override val viewModel: ICategoryAnalytics.ViewModel
        get() = ViewModelProviders.of(this).get(CategoryAnalyticsViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdaptor()
        setData()
    }

    private fun setData() {
        val list = ArrayList<AnalyticsItem>()
        for (i in 0..4)
            list.add(AnalyticsItem("Shopping", "4 transactions", "AED 600", "42%"))

        (getBinding().recycler.adapter as CategoryAnalyticsAdaptor).setList(list)
    }

    private fun initAdaptor() {
        getBinding().recycler.adapter = CategoryAnalyticsAdaptor(mutableListOf())
        (getBinding().recycler.adapter as CategoryAnalyticsAdaptor).setItemListener(listener)
    }

    val listener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {

        }
    }

    private fun getBinding(): FragmentCategoryAnalyticsBinding {
        return (viewDataBinding as FragmentCategoryAnalyticsBinding)
    }

}