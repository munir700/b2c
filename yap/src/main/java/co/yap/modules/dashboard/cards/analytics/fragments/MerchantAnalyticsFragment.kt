package co.yap.modules.dashboard.cards.analytics.fragments

import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentMerchantAnalyticsBinding
import co.yap.modules.dashboard.cards.analytics.interfaces.IMerchantAnalytics
import co.yap.modules.dashboard.cards.analytics.main.fragments.CardAnalyticsBaseFragment
import co.yap.modules.dashboard.cards.analytics.viewmodels.MerchantAnalyticsViewModel

class MerchantAnalyticsFragment : CardAnalyticsBaseFragment<IMerchantAnalytics.ViewModel>(),
    IMerchantAnalytics.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_merchant_analytics

    override val viewModel: IMerchantAnalytics.ViewModel
        get() = ViewModelProviders.of(this).get(MerchantAnalyticsViewModel::class.java)


    private fun getBinding(): FragmentMerchantAnalyticsBinding {
        return (viewDataBinding as FragmentMerchantAnalyticsBinding)
    }
}