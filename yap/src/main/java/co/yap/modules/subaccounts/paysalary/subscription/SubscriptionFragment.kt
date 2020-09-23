package co.yap.modules.subaccounts.paysalary.subscription

import android.os.Bundle
import androidx.lifecycle.Observer
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentSubscriptionBinding
import co.yap.translation.Strings
import co.yap.widgets.MultiStateView
import co.yap.widgets.State
import co.yap.widgets.Status
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment
import kotlinx.android.synthetic.main.fragment_subscription.*


class SubscriptionFragment :
    BaseNavViewModelFragment<FragmentSubscriptionBinding, ISubscription.State, SubscriptionVM>() {

    override fun getBindingVariable() = BR.viewModel

    override fun getLayoutId() = R.layout.fragment_subscription
    override fun getToolBarTitle() = getString(Strings.screen_household_subscription_title)
    override fun postExecutePendingBindings(savedInstanceState: Bundle?) {
        super.postExecutePendingBindings(savedInstanceState)
        ivUserImage?.setOnClickListener {
            navigateForwardWithAnimation(
                SubscriptionFragmentDirections.actionSubscriptionFragmentToHHProfileFragment(),
                arguments
            )
        }
        viewModel.stateLiveData.observe(
            this,
            Observer { if (it.status != Status.IDEAL) handleState(it) })
    }

    private fun handleState(state: State?) {
        when (state?.status) {
            Status.LOADING -> multiStateView?.viewState = MultiStateView.ViewState.LOADING
            Status.ERROR -> multiStateView?.viewState = MultiStateView.ViewState.ERROR
            Status.SUCCESS -> multiStateView?.viewState =
                MultiStateView.ViewState.CONTENT
            else -> multiStateView?.viewState = MultiStateView.ViewState.EMPTY

        }

    }

    override fun onClick(id: Int) {

    }
}