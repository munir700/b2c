package co.yap.modules.dashboard.cards.analytics.fragments

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentCategoryAnalyticsBinding
import co.yap.modules.dashboard.cards.analytics.adaptors.CategoryAnalyticsAdaptor
import co.yap.modules.dashboard.cards.analytics.interfaces.ICategoryAnalytics
import co.yap.modules.dashboard.cards.analytics.main.fragments.CardAnalyticsBaseFragment
import co.yap.modules.dashboard.cards.analytics.viewmodels.CategoryAnalyticsViewModel
import co.yap.yapcore.interfaces.OnItemClickListener


class CategoryAnalyticsFragment : CardAnalyticsBaseFragment<ICategoryAnalytics.ViewModel>(),
    ICategoryAnalytics.View {

    var checkedPosition = 0
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
            (getBinding().recycler.adapter as CategoryAnalyticsAdaptor).setList(it)
        })
    }


    private fun initAdaptor() {
        getBinding().recycler.adapter = CategoryAnalyticsAdaptor(mutableListOf())
        (getBinding().recycler.adapter as CategoryAnalyticsAdaptor).setItemListener(listener)
    }

    val listener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            val adapter = getBinding().recycler.adapter as CategoryAnalyticsAdaptor
            if (adapter.checkedPosition != pos) {
                view.isSelected = true
                view.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.greyLight))
                adapter.notifyItemChanged(adapter.checkedPosition)
                adapter.checkedPosition = pos
//                (view as ConstraintLayout).setBackgroundColor(
//                    ContextCompat.getColor(
//                        requireContext(),
//                        R.color.greyLight
//                    )
//                )
            } else {
                (view as ConstraintLayout).setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.white
                    )
                )
            }


            /*if (((view as ConstraintLayout).background as ColorDrawable).color == R.color.white) {
                (view as ConstraintLayout).setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.greyLight
                    )
                )
            } else {
                (view as ConstraintLayout).setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.white
                    )
                )
            }*/
            viewModel.parentViewModel.selectedItemPosition.value = pos

            //showToast("clicked item ${(data as AnalyticsItem).transactionType}")
        }
    }

    private fun getBinding(): FragmentCategoryAnalyticsBinding {
        return (viewDataBinding as FragmentCategoryAnalyticsBinding)
    }

}