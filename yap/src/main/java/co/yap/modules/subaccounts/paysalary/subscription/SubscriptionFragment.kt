package co.yap.modules.subaccounts.paysalary.subscription

import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentSubscriptionBinding
import co.yap.yapcore.dagger.base.BaseViewModelFragment


class SubscriptionFragment : BaseViewModelFragment<FragmentSubscriptionBinding , ISubscription.State , SubscriptionVM>() {

    override fun getBindingVariable() = BR.viewModel

    override fun getLayoutId() = R.layout.fragment_subscription
}