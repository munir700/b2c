package co.yap.modules.dashboard.cards.analytics.fragments

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentMerchantAnalyticsBinding
import co.yap.modules.dashboard.cards.analytics.adaptors.MerchantAnalyticsAdaptor
import co.yap.modules.dashboard.cards.analytics.interfaces.IMerchantAnalytics
import co.yap.modules.dashboard.cards.analytics.main.fragments.CardAnalyticsBaseFragment
import co.yap.modules.dashboard.cards.analytics.viewmodels.MerchantAnalyticsViewModel
import co.yap.yapcore.interfaces.OnItemClickListener

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
        viewModel.parentViewModel.merchantAnalyticsItemLiveData.observe(this, Observer {
            (getBinding().recycler.adapter as MerchantAnalyticsAdaptor).setList(it)
        })
    }

    private fun initAdaptor() {
        getBinding().recycler.adapter = MerchantAnalyticsAdaptor(mutableListOf())
        (getBinding().recycler.adapter as MerchantAnalyticsAdaptor).setItemListener(listener)
    }

    val listener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            val adapter=getBinding().recycler.adapter as MerchantAnalyticsAdaptor

            if (adapter.checkedPosition != pos) {
                view.isSelected = true
                view.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.itemBackground
                    )
                )
                adapter.notifyItemChanged(adapter.checkedPosition)
                adapter.checkedPosition = pos
            }
            viewModel.parentViewModel.selectedItemPosition.value = pos
        }
    }

    private fun getBinding(): FragmentMerchantAnalyticsBinding {
        return (viewDataBinding as FragmentMerchantAnalyticsBinding)
    }

}