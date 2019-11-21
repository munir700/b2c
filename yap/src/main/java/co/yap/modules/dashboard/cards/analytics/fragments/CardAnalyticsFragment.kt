package co.yap.modules.dashboard.cards.analytics.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentCardAnalyticsBinding
import co.yap.modules.dashboard.cards.analytics.adaptors.CATEGORY_ANALYTICS
import co.yap.modules.dashboard.cards.analytics.adaptors.CardAnalyticsLandingAdaptor
import co.yap.modules.dashboard.cards.analytics.adaptors.MERCHANT_ANALYTICS
import co.yap.modules.dashboard.cards.analytics.interfaces.ICardAnalytics
import co.yap.modules.dashboard.cards.analytics.main.fragments.CardAnalyticsBaseFragment
import co.yap.modules.dashboard.cards.analytics.viewmodels.CardAnalyticsViewModel
import co.yap.translation.Strings
import co.yap.translation.Translator
import com.google.android.material.tabs.TabLayoutMediator

class CardAnalyticsFragment : CardAnalyticsBaseFragment<ICardAnalytics.ViewModel>(),
    ICardAnalytics.View {

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_card_analytics

    override val viewModel: ICardAnalytics.ViewModel
        get() = ViewModelProviders.of(this).get(CardAnalyticsViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
        setupAdaptor()
        setupTabs()
        viewModel.fetchCardAnalytics()
    }


    override fun setObservers() {
        viewModel.parentViewModel.selectedItemPosition.observe(this, Observer {
            when (getBindingView().tabLayout.selectedTabPosition) {
                CATEGORY_ANALYTICS -> {
                    showToast(
                        "Position $it and data is ${viewModel.parentViewModel.categoryAnalyticsItemLiveData.value?.get(
                            it
                        )?.transactionType}"
                    )
                }
                MERCHANT_ANALYTICS -> {
                    showToast(
                        "Position $it and data is ${viewModel.parentViewModel.merchantAnalyticsItemLiveData.value?.get(
                            it
                        )?.transactionType}"
                    )
                }
            }
        })
    }

    private fun setupAdaptor() {
        val adaptor = CardAnalyticsLandingAdaptor(this)
        getBindingView().viewPager.adapter = adaptor
    }

    private fun setupTabs() {
        TabLayoutMediator(getBindingView().tabLayout, getBindingView().viewPager,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                tab.text = getTabTitle(position)
            }).attach()

        getBindingView().viewPager.isUserInputEnabled = false
        getBindingView().viewPager.offscreenPageLimit = 1
    }

    private fun getTabTitle(position: Int): String? {
        return when (position) {
            CATEGORY_ANALYTICS -> Translator.getString(
                requireContext(),
                Strings.screen_card_analytics_display_tab_title_category
            )
            MERCHANT_ANALYTICS -> Translator.getString(
                requireContext(),
                Strings.screen_card_analytics_display_tab_title_merchant
            )
            else -> null
        }
    }

    private fun getBindingView(): FragmentCardAnalyticsBinding {
        return (viewDataBinding as FragmentCardAnalyticsBinding)
    }
}