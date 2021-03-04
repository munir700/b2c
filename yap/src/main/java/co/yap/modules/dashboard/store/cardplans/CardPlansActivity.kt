package co.yap.modules.dashboard.store.cardplans

import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.store.cardplans.interfaces.IMainCardPlans
import co.yap.modules.dashboard.store.cardplans.viewmodels.CardPlansMainViewModel
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.IFragmentHolder
import co.yap.yapcore.defaults.DefaultNavigator
import co.yap.yapcore.defaults.INavigator
import co.yap.yapcore.interfaces.IBaseNavigator

class CardPlansActivity : BaseBindingActivity<IMainCardPlans.ViewModel>(), INavigator,
    IFragmentHolder, IMainCardPlans.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_card_plans

    override fun setObservers() {
    }

    override fun removeObservers() {
    }

    override val viewModel: IMainCardPlans.ViewModel
        get() = ViewModelProviders.of(this).get(CardPlansMainViewModel::class.java)
    override val navigator: IBaseNavigator
        get() = DefaultNavigator(this@CardPlansActivity, R.id.card_plans_navigation)
}