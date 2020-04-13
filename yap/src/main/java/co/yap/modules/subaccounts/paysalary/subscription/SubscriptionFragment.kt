package co.yap.modules.subaccounts.paysalary.subscription

import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentSubscriptionBinding
import co.yap.yapcore.dagger.base.BaseViewModelFragment
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment


class SubscriptionFragment :
    BaseNavViewModelFragment<FragmentSubscriptionBinding, ISubscription.State, SubscriptionVM>() {

    override fun getBindingVariable() = BR.viewModel

    override fun getLayoutId() = R.layout.fragment_subscription
    override fun getToolBarTitle() = "Subscription"
}