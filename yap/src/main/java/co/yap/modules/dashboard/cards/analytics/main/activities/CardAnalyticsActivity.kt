package co.yap.modules.dashboard.cards.analytics.main.activities

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.databinding.ActivityCardsAnalyticsBinding
import co.yap.modules.dashboard.cards.analytics.main.interfaces.ICardAnalyticsMain
import co.yap.modules.dashboard.cards.analytics.main.viewmodels.CardAnalyticsMainViewModel
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.IFragmentHolder
import co.yap.yapcore.defaults.DefaultNavigator
import co.yap.yapcore.defaults.INavigator
import co.yap.yapcore.helpers.extentions.ExtraType
import co.yap.yapcore.helpers.extentions.getValue
import co.yap.yapcore.interfaces.IBaseNavigator

class CardAnalyticsActivity : BaseBindingActivity<ActivityCardsAnalyticsBinding,ICardAnalyticsMain.ViewModel>(),
    ICardAnalyticsMain.View, INavigator, IFragmentHolder {

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_cards_analytics

    override val viewModel: ICardAnalyticsMain.ViewModel
        get() = ViewModelProviders.of(this).get(CardAnalyticsMainViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.state.selectedDate =
            intent.getValue("CurrentMonth", ExtraType.STRING.name) as? String
    }

    override val navigator: IBaseNavigator
        get() = DefaultNavigator(
            this@CardAnalyticsActivity,
            R.id.card_analytics_nav_host_fragment
        )

    override fun onToolBarClick(id: Int) {
        super.onToolBarClick(id)
        when (id) {
            R.id.ivLeftIcon -> {
                onBackPressed()
            }
        }
    }
}