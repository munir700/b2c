package co.yap.modules.dashboard.cards.analytics.main.fragments

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders
import co.yap.modules.dashboard.cards.analytics.main.viewmodels.CardAnalyticsBaseViewModel
import co.yap.modules.dashboard.cards.analytics.main.viewmodels.CardAnalyticsMainViewModel
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.IBase

abstract class CardAnalyticsBaseFragment<VB : ViewDataBinding, V : IBase.ViewModel<*>> :
    BaseBindingFragment<VB,V>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (viewModel is CardAnalyticsBaseViewModel<*>) {
            (viewModel as CardAnalyticsBaseViewModel<*>).parentViewModel =
                ViewModelProviders.of(activity!!).get(CardAnalyticsMainViewModel::class.java)
        }
    }

    override fun onBackPressed(): Boolean {
        return super.onBackPressed()
    }
}