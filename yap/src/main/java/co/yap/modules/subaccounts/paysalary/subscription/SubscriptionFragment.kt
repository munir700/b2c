package co.yap.modules.subaccounts.paysalary.subscription

import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentSubscriptionBinding
import co.yap.translation.Strings
import co.yap.widgets.MultiStateView
import co.yap.widgets.State
import co.yap.widgets.Status
import co.yap.yapcore.helpers.showSnackBar
import co.yap.yapcore.hilt.base.navigation.BaseNavViewModelFragmentV2
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_subscription.*


@AndroidEntryPoint
class SubscriptionFragment :
    BaseNavViewModelFragmentV2<FragmentSubscriptionBinding, ISubscription.State, SubscriptionVM>() {

    override fun getBindingVariable() = BR.viewModel

    override val viewModel: SubscriptionVM by viewModels()

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

        viewModel.subscriptionCancelled.observe(
            this,
            Observer { if (it) showSubscriptionCancelMessage() })
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

    private fun showSubscriptionCancelMessage() {
        showSnackBar(
            msg = getString(
                Strings.screen_yap_house_hold_subscription_cancellation_notification_message_text,
                viewModel.state.subscriptionResponseModel.value?.endDate.toString()
            ),
            viewBgColor = R.color.colorSnackBarBackGround,
            colorOfMessage = R.color.colorSecondaryMagenta,
            gravity = Gravity.TOP,
            duration = 10000,
            marginTop = 0
        )
    }
}